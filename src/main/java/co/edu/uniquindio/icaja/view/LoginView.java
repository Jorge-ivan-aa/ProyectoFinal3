package co.edu.uniquindio.icaja.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private TextField txtContraseña;

    @FXML
    private TextField txtIdUsuario;

    @FXML
    void cancelarIniciarSesion(ActionEvent event) {

    }

    @FXML
    void iniciarSesion(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnCancelar != null : "fx:id=\"btnCancelar\" was not injected: check your FXML file 'login.fxml'.";
        assert btnIniciarSesion != null : "fx:id=\"btnIniciarSesion\" was not injected: check your FXML file 'login.fxml'.";
        assert txtContraseña != null : "fx:id=\"txtContraseña\" was not injected: check your FXML file 'login.fxml'.";
        assert txtIdUsuario != null : "fx:id=\"txtIdUsuario\" was not injected: check your FXML file 'login.fxml'.";

    }
}
