package co.edu.uniquindio.icaja.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.exception.UsuarioNoExiste;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.UsuarioProxy;
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
        UsuarioProxy u = new UsuarioProxy("", "");
        try {
            u.ingresar();
        } catch (UsuarioNoExiste e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void initialize() {
        assert btnCancelar != null : "fx:id=\"btnCancelar\" was not injected: check your FXML file 'login.fxml'.";
        assert btnIniciarSesion != null : "fx:id=\"btnIniciarSesion\" was not injected: check your FXML file 'login.fxml'.";
        assert txtContraseña != null : "fx:id=\"txtContraseña\" was not injected: check your FXML file 'login.fxml'.";
        assert txtIdUsuario != null : "fx:id=\"txtIdUsuario\" was not injected: check your FXML file 'login.fxml'.";

    }
}
