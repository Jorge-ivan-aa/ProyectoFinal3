package co.edu.uniquindio.icaja.factory;


import co.edu.uniquindio.icaja.model.*;
import co.edu.uniquindio.icaja.model.persistencia.*;
import co.edu.uniquindio.icaja.model.services.Persistible;
import co.edu.uniquindio.icaja.utils.almacenamiento.Config;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.model.respaldo.ICajaRespaldo;
import co.edu.uniquindio.icaja.utils.almacenamiento.Persistencia;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import static co.edu.uniquindio.icaja.utils.tools.ListTools.sincronizarLista;

@Setter
@Getter
public class ModelFactory {
    private static ModelFactory instance;
    @Getter
    private static String IdInstanciaMensajera = java.util.UUID.randomUUID().toString();
    private ICaja icaja;

    // SINCRONIZACION
    private final ObservableList<Cuenta> listaCuentaObservable = FXCollections.observableArrayList();
    private final ObservableList<Usuario> listaUsuarioObservable = FXCollections.observableArrayList();
    private final ObservableList<Presupuesto> listaPresupuestoObservable = FXCollections.observableArrayList();
    private final ObservableList<Transaccion> listaTransaccionObservable = FXCollections.observableArrayList();
    private final ObservableList<Categoria> listaCategoriasObservable = FXCollections.observableArrayList();
    private final ObservableList<String> notificaciones = FXCollections.observableArrayList();

    // PERSISTENCIA
    private final UsuarioPersistente usuarioPersistente;
    private final CuentaPersistente cuentaPersistente;
    private final CategoriaPersistente categoriaPersistente;
    private final PresupuestoPersistente presupuestoPersistente;
    private final TransaccionPersistente transaccionPersistente;


    private ModelFactory() {
        Persistencia.setRutaArchivos(Config.RUTA_PERSISTENCIA.getValor());

        usuarioPersistente = new UsuarioPersistente();
        cuentaPersistente = new CuentaPersistente();
        categoriaPersistente = new CategoriaPersistente();
        presupuestoPersistente = new PresupuestoPersistente();
        transaccionPersistente = new TransaccionPersistente();

        icaja = new ICaja();

        if (!cargarPersistencia()) icaja = cargaRespaldo();
        if (icaja == null) icaja = new ICaja();
        cargarConfiguracion();
    }

    public static ModelFactory getInstance() {
        if (instance == null) {
            instance = new  ModelFactory();
        }
        return instance;
    }

    public void sincronizarInstancia() {
        icaja.clear();
        cargarConfiguracion();
        cargarPersistencia();
        sincronizarData();
        Seguimiento.registrarLog(1, "Sincronizando instancia");
    }


    public void sincronizarData() {
        Platform.runLater(() -> {
            sincronizarLista(listaCuentaObservable, icaja.getListaCuentas());
            sincronizarLista(listaUsuarioObservable, icaja.getListaUsuarios());
            sincronizarLista(listaPresupuestoObservable, icaja.getListaPresupuestos());
            sincronizarLista(listaTransaccionObservable, icaja.getListaTransacciones());
            sincronizarLista(listaCategoriasObservable, icaja.getListaCategorias());
            icaja.excluirAdmin(listaUsuarioObservable, listaCategoriasObservable);

            guardarPersistencia();
        });
    }


    private boolean cargarPersistencia() {
        List<Usuario> usuarios;
        List<Cuenta> cuentasBancarias;
        List<Transaccion> transacciones;
        List<Categoria> categorias;
        List<Presupuesto> presupuestos;

        try {
            cuentasBancarias = cuentaPersistente.leer("cuenta.txt");
            usuarios = usuarioPersistente.leer("usuario.txt");
            transacciones = transaccionPersistente.leer("transaccion.txt");
            categorias = categoriaPersistente.leer("categoria.txt");
            presupuestos = presupuestoPersistente.leer("presupuesto.txt");

            if (!usuarios.isEmpty()) {
                agregarElementos(cuentasBancarias);
                agregarElementos(usuarios);
                agregarElementos(transacciones);
                agregarElementos(categorias);
                agregarElementos(presupuestos);
                Seguimiento.registrarLog(1, "Se ha cargado la persistencia correctamente.");
                return  true;

            } else {
                Seguimiento.registrarLog(2, "No se han podido cargar los archivos de persistencia, la persistencia de usuario está vacia.");
                return false;

            }
        } catch (Exception e) {
            Seguimiento.registrarLog(3, "No se han podido cargar los archivos de persistencia: " + e.getMessage());
            return false;
        }

    }

    private void guardarPersistencia() {

        List<Usuario> usuarios = new ArrayList<>(icaja.getListaUsuarios());
        List<Categoria> categorias = new ArrayList<>(icaja.getListaCategorias());
        icaja.excluirAdmin(usuarios, categorias);

        guardar("usuario.txt", usuarioPersistente, usuarios);
        guardar("cuenta.txt", cuentaPersistente, icaja.getListaCuentas());
        guardar("transaccion.txt",transaccionPersistente,icaja.getListaTransacciones());
        guardar("categoria.txt", categoriaPersistente, categorias);
        guardar("presupuesto.txt",presupuestoPersistente, icaja.getListaPresupuestos());
    }

    private <E> void guardar(String file, Persistible<E> elementoPersistible, List<E> lista) {
        try {
            elementoPersistible.guardar(lista);

        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Ocurrio un error al guardar la informacion en el fichero "+ file +", error: " + e);

        }
    }



    private <T> void agregarElementos(List<T> listaElementos) {
        if (listaElementos != null) {
            for (T elemento : listaElementos) {
                if (elemento instanceof Usuario) {
                    icaja.getListaUsuarios().add((Usuario) elemento);
                } else if (elemento instanceof Cuenta) {
                    icaja.getListaCuentas().add((Cuenta) elemento);
                } else if (elemento instanceof Categoria) {
                    icaja.getListaCategorias().add((Categoria) elemento);
                } else if (elemento instanceof Transaccion) {
                    icaja.getListaTransacciones().add((Transaccion) elemento);
                }
            }
        }
    }


    private void cargarConfiguracion() {

        try {
            String cedula_admin= Config.CEDULA_ADMIN.getValor();
            String clave_admin = Config.CLAVE_ADMIN.getValor();
            String categoria_admin = Config.CATEGORIA_ADMIN.getValor();

            icaja.excluirAdmin(icaja.getListaUsuarios(), icaja.getListaCategorias());

            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setCedula(cedula_admin);
            admin.setHashclave(clave_admin);
            admin.setAdministrador();
            icaja.add(admin);

            Categoria categoriaSistema = new Categoria(categoria_admin, categoria_admin);
            categoriaSistema.setIdCategoria("SYSTEM");
            icaja.add(categoriaSistema);

            Seguimiento.registrarLog(1, "Se cargó la configuración de las credenciales de administrador");

        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Ocurrio un error al cargar la informacion del archivo config.properties" + e);

        }

    }

    private ICaja cargaRespaldo() {
        return ICajaRespaldo.cargarRecursoICajaXML();
    }

    public void guardarRespaldo() {
        icaja.setSesion(null);
        ICajaRespaldo.guardarRecursoICajaXML(icaja);
    }

}