package co.edu.uniquindio.icaja.factory;

import co.edu.uniquindio.icaja.exception.almacenamiento.ElementoNoEncontrado;
import co.edu.uniquindio.icaja.exception.almacenamiento.SinPersistencia;
import co.edu.uniquindio.icaja.exception.almacenamiento.TipoNoMapeado;
import co.edu.uniquindio.icaja.model.*;
import co.edu.uniquindio.icaja.model.persistencia.*;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.respaldo.ICajaRespaldo;
import co.edu.uniquindio.icaja.utils.respaldo.Persistencia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import static co.edu.uniquindio.icaja.utils.loggin.Seguimiento.registrarLog;

@Getter
public class ModelFactory {
    private static ModelFactory instance;
    private ICaja icaja;

    // SINCRONIZACION
    private final ObservableList<Cuenta> listaCuentaObservable = FXCollections.observableArrayList();
    private final ObservableList<Usuario> listaUsuarioObservable = FXCollections.observableArrayList();
    private final ObservableList<Presupuesto> listaPresupuestoObservable = FXCollections.observableArrayList();
    private final ObservableList<Transaccion> listaTransaccionObservable = FXCollections.observableArrayList();
    private final ObservableList<Categoria> listaCategoriasObservable = FXCollections.observableArrayList();

    // PERSISTENCIA
    private final UsuarioPersistente usuarioPersistente;
    private final CuentaPersistente cuentaPersistente;
    private final CategoriaPersistente categoriaPersistente;
    private final PresupuestoPersistente presupuestoPersistente;
    private final TransaccionPersistente transaccionPersistente;


    private ModelFactory() {
        usuarioPersistente = new UsuarioPersistente(this);
        cuentaPersistente = new CuentaPersistente(this);
        categoriaPersistente = new CategoriaPersistente();
        presupuestoPersistente = new PresupuestoPersistente();
        transaccionPersistente = new TransaccionPersistente();

        icaja = new ICaja();
        loadData();
        loadConfig();
    }


    public static ModelFactory getInstance() {
        if (instance == null) {
            instance = new ModelFactory();
        }
        return instance;
    }


    public void sincronizarData() {
        sincronizarLista(listaCuentaObservable, icaja.getListaCuentas());
        sincronizarLista(listaUsuarioObservable, icaja.getListaUsuarios());
        sincronizarLista(listaPresupuestoObservable, icaja.getListaPresupuestos());
        sincronizarLista(listaTransaccionObservable, icaja.getListaTransacciones());
        sincronizarLista(listaCategoriasObservable, icaja.getListaCategorias());
        icaja.excluirAdmin(listaUsuarioObservable);

        registrarLog(1,"Se sincronizó la base de datos");
    }

    public <T> void sincronizarLista(ObservableList<T> listaObservable, List<T> listaFuente) {
        listaObservable.clear();
        listaObservable.addAll(listaFuente);
    }

    public void loadData() {
        List<Usuario> usuarios = null;
        List<Cuenta> cuentasBancarias  = null;
//        List<Categoria> categorias  = null;
//        List<Transaccion> transacciones  = null;

        try {
            cuentasBancarias = cuentaPersistente.leer("cuenta.txt");
            agregarElementos(cuentasBancarias);
//
//            categorias = categoriaPersistente.leer("categoria.txt");
//            agregarElementos(categorias);
//
//            transacciones = transaccionPersistente.leer("transaccion");
//            agregarElementos(transacciones);

            usuarios = usuarioPersistente.leer("usuario.txt");
            agregarElementos(usuarios);

            icaja.construirReferencias();

        } catch (IOException e) {
            Seguimiento.registrarLog(3, "No se han podido cargar los archivos de persistencia: " + e.getMessage());
        }
    }

    private <T> void agregarElementos(List<T> listaElementos) {
        if (listaElementos != null) {
            for (T elemento :listaElementos) {
                if (elemento instanceof Usuario) {
                    icaja.getListaUsuarios().add((Usuario) elemento);
                } else if (elemento instanceof Cuenta) {
                    icaja.getListaCuentas().add((Cuenta) elemento);
                } else if (elemento instanceof  Categoria) {
                    icaja.getListaCategorias().add((Categoria) elemento);
                } else if (elemento instanceof  Transaccion) {
                    icaja.getListaTransacciones().add((Transaccion) elemento);
                }
            }
        }
    }


    public void loadConfig() {
        String cedula = Persistencia.cargarConfiguracion("admin");
        String contrasena = Persistencia.cargarConfiguracion("contrasena");

        icaja.excluirAdmin(icaja.getListaUsuarios());

        Usuario admin = new Usuario();
        admin.setNombre("Administrador");
        admin.setCedula(cedula);
        admin.setHashclave(contrasena);
        admin.setAdministrador();

        icaja.getListaUsuarios().add(admin);
        Seguimiento.registrarLog(1,"Se cargó la configuración de las credenciales de administrador");
    }

    public ICaja cargaRespaldo() {
        return ICajaRespaldo.cargarRecursoICajaXML();
    }

    public void guardarRespaldo() {
        icaja.setSesion(null);
        ICajaRespaldo.guardarRecursoICajaBinario(icaja);
        ICajaRespaldo.guardarRecursoICajaXML(icaja);
    }

    public <T> List<T> restaurarLista(String[] datosUsuario, int startIdx, String delimitador, Class<T> tipoClase) {
        return icaja.restaurarLista(datosUsuario, startIdx, delimitador, tipoClase);
    }

    public Object buscarPorId(Class<?> tipo, String id) throws TipoNoMapeado, ElementoNoEncontrado {
        return icaja.buscarPorId(tipo, id);
    }

}
