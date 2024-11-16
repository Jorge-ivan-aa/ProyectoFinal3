package co.edu.uniquindio.icaja.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import co.edu.uniquindio.icaja.exception.almacenamiento.ElementoNoEncontrado;
import co.edu.uniquindio.icaja.exception.almacenamiento.TipoNoMapeado;
import co.edu.uniquindio.icaja.model.enums.TipoUsuario;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.tools.ListTools;
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
    private ArrayList<Transaccion> listaTransacciones = new ArrayList<>();
    private ArrayList<Categoria> listaCategorias = new ArrayList<>();
    private ArrayList<Cuenta> listaCuentas = new ArrayList<>();
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


    public void construirReferencias() {
        // Crear un mapa para encontrar las cuentas más rápidamente
        Map<String, Cuenta> cuentasMap = new HashMap<>();
        for (Cuenta cuenta : listaCuentas) {
            cuentasMap.put(cuenta.getIdCuenta(), cuenta);
        }

        // Recorremos los usuarios
        for (Usuario usuario : listaUsuarios) {
            // Recorremos los ids de las cuentas asociadas al usuario
            for (String idCuentaUsuario : usuario.getIdCuentas()) {
                Cuenta cuenta = cuentasMap.get(idCuentaUsuario);  // Obtenemos la cuenta correspondiente

                if (cuenta != null) {
                    usuario.getCuentas().add(cuenta);  // Agregamos la cuenta al usuario
                    cuenta.setPropietario(usuario);  // Establecemos el propietario de la cuenta
                } else {
                    // Si no se encuentra la cuenta, tal vez quieras registrar un error o manejarlo
                    System.err.println("No se encontró la cuenta con ID: " + idCuentaUsuario);
                }
            }
        }
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


    /**
     * Método genérico para restaurar listas de objetos en base a un array de datos y un delimitador.
     *
     * @param datosUsuario Array de datos que contiene los ID de los objetos.
     * @param startIdx     El índice donde empieza la búsqueda de elementos a restaurar.
     * @param delimitador  El delimitador que marca el final de una lista (e.g., "<<<").
     * @param tipoClase    La clase de los objetos que estamos restaurando (Cuenta, Presupuesto, etc.).
     * @return Lista de objetos restaurados.
     */
    @SuppressWarnings("unchecked")
    public <T> ArrayList<T> restaurarLista(String[] datosUsuario, int startIdx, String delimitador, Class<T> tipoClase) {
        ArrayList<T> lista = new ArrayList<>();
        int idx = startIdx;

        // Mientras no lleguemos al delimitador, restauramos los objetos
        while (!datosUsuario[idx].equals(delimitador)) {
            try {
                T objeto = (T) buscarPorId(tipoClase, datosUsuario[idx]);
                if (objeto != null) {
                    lista.add(objeto);
                }
                idx++;
            } catch (TipoNoMapeado e) {
                Seguimiento.registrarLog(3, e.getMessage());
            } catch (ElementoNoEncontrado e ) {
                Seguimiento.registrarLog(3, e.getMessage() + "de tipo " + tipoClase.getSimpleName());
            }
        }
        return lista;
    }
}