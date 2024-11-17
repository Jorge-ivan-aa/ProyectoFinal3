package co.edu.uniquindio.icaja.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import co.edu.uniquindio.icaja.exception.almacenamiento.ElementoNoEncontrado;
import co.edu.uniquindio.icaja.exception.almacenamiento.TipoNoMapeado;
import co.edu.uniquindio.icaja.model.enums.TipoTransaccion;
import co.edu.uniquindio.icaja.model.enums.TipoUsuario;
import co.edu.uniquindio.icaja.utils.tools.ListTools;
import co.edu.uniquindio.icaja.utils.tools.NumTool;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ICaja implements Serializable {

    // Maps
    private Map<Class<?>, List<?>> listas = new HashMap<>();
    private Map<Class<?>, Function<Object, String>> idGetter =  new HashMap<>();

    // Listas
    private ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    private ArrayList<Cuenta> listaCuentas = new ArrayList<>();
    private ArrayList<Transaccion> listaTransacciones = new ArrayList<>();
    private ArrayList<Categoria> listaCategorias = new ArrayList<>();
    private ArrayList<Presupuesto> listaPresupuestos = new ArrayList<>();

    // Sesion
    private Sesion sesion;

    public ICaja() {
        this.sesion = null;
        inicializarMappers();
    }


    private void inicializarMappers() {
        // listas
        listas.put(Usuario.class, listaUsuarios);
        listas.put(Transaccion.class, listaTransacciones);
        listas.put(Categoria.class, listaCategorias);
        listas.put(Cuenta.class, listaCuentas);
        listas.put(Presupuesto.class, listaPresupuestos);

        // idGetter
        idGetter.put(Usuario.class, usuario -> ((Usuario) usuario).getIdUsuario());
        idGetter.put(Transaccion.class, transaccion -> ((Transaccion) transaccion).getIdTransaccion());
        idGetter.put(Categoria.class, categoria -> ((Categoria) categoria).getIdCategoria());
        idGetter.put(Cuenta.class, cuenta -> ((Cuenta) cuenta).getIdCuenta());
        idGetter.put(Presupuesto.class, presupuesto -> ((Presupuesto) presupuesto).getIdPresupuesto());
    }

    public void add(Cuenta cuenta) {
        Usuario propietario = (Usuario) buscarPorId(Usuario.class, cuenta.getIdpropietario());
        propietario.getIdCuentas().add(cuenta.getIdCuenta());
        propietario.sumarSaldoTotal(cuenta.getSaldo());

        listaCuentas.add(cuenta);
    }

    public void remove(Cuenta cuenta) {
        Usuario propietario = (Usuario) buscarPorId(Usuario.class, cuenta.getIdpropietario());
        propietario.getIdCuentas().remove(cuenta.getIdCuenta());
        propietario.restarSaldoTotal(cuenta.getSaldo());

        listaCuentas.remove(cuenta);
    }

    public void add(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    public void remove(Usuario usuario) {
        for (String id: usuario.getIdCuentas()) listaCuentas.removeIf(cuenta -> cuenta.getIdCuenta().equals(id));
        for (String id: usuario.getIdCategorias()) listaCategorias.removeIf(categoria -> categoria.getIdCategoria().equals(id));
        for (String id: usuario.getIdPresupuestos()) listaPresupuestos.removeIf(presupuesto -> presupuesto.getIdPresupuesto().equals(id));
        for (String id: usuario.getIdTransacciones()) listaTransacciones.removeIf(transaccion -> transaccion.getIdTransaccion().equals(id));

        listaUsuarios.remove(usuario);
    }

    public void add(Transaccion transaccion) {
        switch (transaccion.getTipo()) {
            case TRANSFERENCIA:
                try {
                    Cuenta cuentaOrigen = (Cuenta) buscarPorId(Cuenta.class, transaccion.getIdCuentas()[0]);
                    Usuario propietario1 = (Usuario) buscarPorId(Usuario.class, cuentaOrigen.getIdpropietario());

                    Cuenta cuentaDestino = (Cuenta) buscarPorId(Cuenta.class, transaccion.getIdCuentas()[1]);
                    Usuario propietario2 = (Usuario) buscarPorId(Usuario.class, cuentaDestino.getIdpropietario());

                    transaccion.hacerTransferencia(cuentaOrigen, propietario1, cuentaDestino, propietario2);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case RETIRO:
                try {
                    Cuenta cuentaOrigen = (Cuenta) buscarPorId(Cuenta.class, transaccion.getIdCuentas()[0]);
                    Usuario propietario = (Usuario) buscarPorId(Usuario.class, cuentaOrigen.getIdpropietario());

                    transaccion.hacerRetiro(cuentaOrigen, propietario);
                }  catch (Exception e) {
                    e.printStackTrace();
                }
            case DEPOSITO:
                try {
                    Cuenta cuentaOrigen = (Cuenta) buscarPorId(Cuenta.class, transaccion.getIdCuentas()[0]);
                    Usuario propietario = (Usuario) buscarPorId(Usuario.class, cuentaOrigen.getIdpropietario());

                    transaccion.hacerDeposito(cuentaOrigen, propietario);
                }  catch (Exception e) {
                    e.printStackTrace();
                }
        }


        listaTransacciones.add(transaccion);
    }

    public void add(Categoria categoria) {
        listaCategorias.add(categoria);
    }

    public void remove(Categoria categoria) {
        listaCategorias.remove(categoria);
    }

    public void add(Presupuesto presupuesto) {
        listaPresupuestos.add(presupuesto);
    }

    public void remove(Presupuesto presupuesto) {
        listaPresupuestos.remove(presupuesto);
    }



    /**
     * Elimina todos los usuarios de tipo administrador de una lista.
     * @param usuarios lista de usuario.
     */
    public void excluirAdmin(List<Usuario> usuarios) {
        if (usuarios != null) {
            usuarios.removeIf(usuario -> usuario.getTipoUsuario().equals(TipoUsuario.ADMINISTRADOR));
        }
    }


    /**
     * Busca un objeto por su ID en la lista correspondiente según su tipo.
     *
     * @param tipo El tipo de objeto que se busca.
     * @param id El identificador único del objeto que se busca.
     * @return El objeto encontrado sí se encuentra en la lista correspondiente.
     * @throws TipoNoMapeado Si el tipo no está mapeado en las listas o si no se encuentra el tipo de ID.
     */
    public Object buscarPorId(Class<?> tipo, String id) throws TipoNoMapeado, ElementoNoEncontrado {
        List<?> lista = listas.get(tipo);
        Function<Object, String> idGetter = this.idGetter.get(tipo);

        // Verifica si tanto la lista como el getter de ID están disponibles para el tipo
        if (lista != null && idGetter != null) {
            return ListTools.ConsultaAvanzada(lista, idGetter, id, 0);
        } else {
            // Si no se encuentra el tipo mapeado, lanza la excepción personalizada
            throw new TipoNoMapeado("El tipo de entrada " + tipo.getSimpleName() + " no se encuentra mapeado en Icaja");
        }
    }

}