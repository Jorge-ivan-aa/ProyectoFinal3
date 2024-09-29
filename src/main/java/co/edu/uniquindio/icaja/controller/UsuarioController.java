package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Objects;

public class UsuarioController {

    private final ModelFactory factory;

    private final ObservableList<Usuario> listaUsuarioObservable;

    public UsuarioController(ModelFactory factory, ObservableList<Usuario> listaObservable) {
        this.factory = ModelFactory.getInstance();
        this.listaUsuarioObservable = FXCollections.observableArrayList();
        this.sincronizarData();

    }

    public ObservableList<Usuario> getListaUsuarioObservable() {
        return listaUsuarioObservable;
    }

    private void sincronizarData() {
        // this.listaUsuarioObservable.addAll(this.factory.getIcaja().getListaUsuario());
    }
    public String eliminarUsuario(String nombre) {

        if (this.consultarUsuario(nombre) == null) {
            return "El usuario ingresado no existe";
        } else {
            int index = -1;
            ArrayList<Usuario> Clientes = factory.getIcaja().getListaUsuario();
            for (int i = 0; i < Clientes.size(); i++) {
                if (Objects.equals(Clientes.get(i).getNombre(), nombre)) {
                    index = i;
                }
            }
            if (index != -1) {
                this.listaUsuarioObservable.remove(index);
                Clientes.remove(index);
            }
            return "El usuario fué eliminado correctamente";
        }
    }

    public Usuario consultarCliente(String nombre) {
        ArrayList<Usuario> Usuarios = this.factory.getIcaja().getListaUsuario();
        for (Usuario value : Usuarios) {
            if (value.getNombre().equals(nombre)) {
                return value;
            }
        }
        return null;
    }

    public String crearUsuario(String nombre, String cedula, String correo, String telefono, String clave, String claveTransaccional, double saldoTotal, double presupuestoMensual) {
        ArrayList<Usuario> Usuarios = factory.getIcaja().getListaUsuario();

        if (this.consultarUsuario(nombre) != null) {
            return "El usuario ingresado ya existe";
        } else {
            Usuario nuevoUsuario = new Usuario(nombre, cedula, correo, telefono, clave, claveTransaccional, saldoTotal, presupuestoMensual);
            this.factory.getIcaja().addUsuario(nuevoUsuario);
            this.listaUsuarioObservable.add(nuevoUsuario);
            return "Usuario registrado exitosamente";
        }
    }

    public String actualizarUsuario(String nombre, String cedula, String correo, String telefono, String clave, String claveTransaccional, double saldoTotal, double presupuestoMensual) {
        ArrayList<Usuario> Usuarios = factory.getIcaja().getListaUsuario();

        if (this.consultarUsuario(nombre) == null) {
            return "El usuario ingresado no existe";

        } else {
            int index = -1;
            for (int i = 0; i < Usuarios.size(); i++) {
                if (Objects.equals(Usuarios.get(i).getNombre(), nombre)) {
                    index = i;
                }
            }

            if (index != -1) {
                Usuario nuevoUsuario = new Usuario(nombre,cedula, correo, telefono, clave, claveTransaccional,saldoTotal,presupuestoMensual);
                Usuarios.remove(index);
                Usuarios.add(nuevoUsuario);
                this.listaUsuarioObservable.remove(index);
                this.listaUsuarioObservable.add(nuevoUsuario);
            }

            return "El Usuario fué actualizado correctamente";

        }
    }
}
