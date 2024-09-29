package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
