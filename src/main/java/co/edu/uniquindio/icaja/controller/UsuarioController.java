package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.utils.Seguimiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static co.edu.uniquindio.icaja.utils.Seguimiento.registrarLog;

import java.util.ArrayList;
import java.util.Objects;

public class UsuarioController {

    private final ModelFactory factory;
    private final ObservableList<Usuario> listaUsuarioObservable;

    public UsuarioController() {
        this.factory = ModelFactory.getInstance();
        this.listaUsuarioObservable = FXCollections.observableArrayList();
        this.sincronizarData();

    }

    public ModelFactory getFactory() {
        return factory;
    }

    public ObservableList<Usuario> getListaUsuarioObservable() {
        return listaUsuarioObservable;
    }

    private void sincronizarData() {

        registrarLog(1,"Se sincronizo la base de datos");

        this.listaUsuarioObservable.addAll(this.factory.getIcaja().getListaUsuarios());
        Seguimiento.registrarLog(1,"Se sincronizo la base de datos");
    }
    public String eliminarUsuario(String cedula) {

        if (this.consultarUsuario(cedula) == null) {

            registrarLog(1,"El usuario que no existe");

            return "El usuario ingresado no existe";
        } else {
            int index = -1;
            ArrayList<Usuario> Clientes = factory.getIcaja().getListaUsuarios();
            for (int i = 0; i < Clientes.size(); i++) {
                if (Objects.equals(Clientes.get(i).getCedula(), cedula)) {
                    index = i;
                }
            }

            registrarLog(1,"Se elimino el usuario");

            if (index != -1) {
                this.listaUsuarioObservable.remove(index);
                Clientes.remove(index);
            }
            return "El usuario fué eliminado correctamente";
        }
    }

    public Usuario consultarUsuario(String cedula) {

        registrarLog(1,"Se consultó el usuario");

        ArrayList<Usuario> Usuarios = this.factory.getIcaja().getListaUsuarios();
        for (Usuario value : Usuarios) {
            if (value.getCedula().equals(cedula)) {
                return value;
            }
        }
        return null;
    }

    public String crearUsuario(String nombre, String cedula, String correo, String telefono, String clave, String claveTransaccional, double presupuestoMensual) {
        ArrayList<Usuario> Usuarios = factory.getIcaja().getListaUsuarios();

        if (this.consultarUsuario(cedula) != null) {

            registrarLog(1,"El usuario ya existe");

            return "El usuario ingresado ya existe";
        } else {

            registrarLog(1,"Se ha creado el usuario");

            Usuario nuevoUsuario = new Usuario(nombre, cedula, correo, telefono, clave, claveTransaccional, presupuestoMensual);
            this.factory.getIcaja().addUsuario(nuevoUsuario);
            this.listaUsuarioObservable.add(nuevoUsuario);
            return "Usuario registrado exitosamente";
        }
    }

    public String actualizarUsuario(String nombre, String cedula, String correo, String telefono, String clave, String claveTransaccional, double presupuestoMensual) {
        ArrayList<Usuario> Usuarios = factory.getIcaja().getListaUsuarios();

        if (this.consultarUsuario(cedula) == null) {

            registrarLog(1,"El usuario no existe");

            return "El usuario ingresado no existe";

        } else {
            int index = -1;
            for (int i = 0; i < Usuarios.size(); i++) {
                if (Objects.equals(Usuarios.get(i).getCedula(), cedula)) {
                    index = i;
                }
            }

            if (index != -1) {

                registrarLog(1,"Se actualizo el usuario");

                Usuario nuevoUsuario = new Usuario(nombre,cedula, correo, telefono, clave, claveTransaccional,presupuestoMensual);
                Usuarios.remove(index);
                Usuarios.add(nuevoUsuario);
                this.listaUsuarioObservable.remove(index);
                this.listaUsuarioObservable.add(nuevoUsuario);
            }

            return "El Usuario fué actualizado correctamente";

        }
    }
}
