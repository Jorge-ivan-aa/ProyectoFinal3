package co.edu.uniquindio.proyectofinal3.controller;

import co.edu.uniquindio.proyectofinal3.factory.ModelFactory;
import co.edu.uniquindio.proyectofinal3.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Objects;
public class UsuarioController {

    private final ModelFactory factory;

    private final ObservableList<Usuario> listaObservable;

    public UsuarioController(ModelFactory factory, ObservableList<Usuario> listaObservable) {
        this.factory = ModelFactory.getInstance();
        this.listaObservable = FXCollections.observableArrayList();
        this.sincronizarData();

    }

    private void sincronizarData() {
    }
}
