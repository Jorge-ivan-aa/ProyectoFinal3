package co.edu.uniquindio.icaja.factory;


import co.edu.uniquindio.icaja.model.*;
import co.edu.uniquindio.icaja.model.persistencia.*;
import co.edu.uniquindio.icaja.model.services.Persistible;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.respaldo.ICajaRespaldo;
import co.edu.uniquindio.icaja.utils.respaldo.Persistencia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static co.edu.uniquindio.icaja.utils.loggin.Seguimiento.registrarLog;
import static co.edu.uniquindio.icaja.utils.tools.ListTools.sincronizarLista;

@Getter
public class ModelFactory {
    private static ModelFactory instance;
    private final ICaja icaja;

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
        usuarioPersistente = new UsuarioPersistente();
        cuentaPersistente = new CuentaPersistente();
        categoriaPersistente = new CategoriaPersistente();
        presupuestoPersistente = new PresupuestoPersistente();
        transaccionPersistente = new TransaccionPersistente();

        icaja = new ICaja();
        cargarPersistencia();
        cargarConfiguracion();
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

        guardarPersistencia();
        registrarLog(1, "Se sincronizó la base de datos");
    }


    private void cargarPersistencia() {
        List<Usuario> usuarios;
        List<Cuenta> cuentasBancarias;

        try {
            cuentasBancarias = cuentaPersistente.leer("cuenta.txt");
            agregarElementos(cuentasBancarias);
            usuarios = usuarioPersistente.leer("usuario.txt");
            agregarElementos(usuarios);

        } catch (IOException e) {
            Seguimiento.registrarLog(3, "No se han podido cargar los archivos de persistencia: " + e.getMessage());
        }

    }

    private void guardarPersistencia() {

        List<Usuario> usuarios = new ArrayList<>(icaja.getListaUsuarios());
        icaja.excluirAdmin(usuarios);

        guardar("usuario.txt", usuarioPersistente, usuarios);
        guardar("cuenta.txt", cuentaPersistente, icaja.getListaCuentas());
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
        String cedula = Persistencia.cargarConfiguracion("admin");
        String contrasena = Persistencia.cargarConfiguracion("contrasena");

        icaja.excluirAdmin(icaja.getListaUsuarios());

        Usuario admin = new Usuario();
        admin.setNombre("Administrador");
        admin.setCedula(cedula);
        admin.setHashclave(contrasena);
        admin.setAdministrador();

        icaja.getListaUsuarios().add(admin);
        Seguimiento.registrarLog(1, "Se cargó la configuración de las credenciales de administrador");
    }

    private ICaja cargaRespaldo() {
        return ICajaRespaldo.cargarRecursoICajaXML();
    }

    public void guardarRespaldo() {
        icaja.setSesion(null);
        ICajaRespaldo.guardarRecursoICajaXML(icaja);
    }

}
