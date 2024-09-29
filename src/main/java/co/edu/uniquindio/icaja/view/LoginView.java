package co.edu.uniquindio.icaja.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.exception.CredencialesNoCoinciden;
import co.edu.uniquindio.icaja.exception.UsuarioNoExiste;
import co.edu.uniquindio.icaja.model.UsuarioProxy;
import co.edu.uniquindio.icaja.model.enums.TipoUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
public class LoginView {

    @FXML
    private TextField txtCedulaUsuario;

    @FXML
    private PasswordField txtClaveUsuario;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void cancelarIniciarSesion(ActionEvent event) {

    }

    @FXML
    void iniciarSesion(ActionEvent event) {
        UsuarioProxy usuario = new UsuarioProxy("", "");
        String clave = txtClaveUsuario.getText();
        String cedula = txtCedulaUsuario.getText();

        if (!Tools.hayCamposVacios(clave, cedula)) {
            try {
                TipoUsuario tipoUsuario = usuario.ingresar();
                seleccionarInterfax(tipoUsuario);

            } catch (UsuarioNoExiste | CredencialesNoCoinciden e) {
                Tools.mostrarMensaje("¡Lo sentimos!", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            Tools.mostrarMensaje("¡Lo sentimos!", null, "Hay campos vacios.", Alert.AlertType.ERROR);
        }

    }

    void seleccionarInterfax(TipoUsuario tipoUsuario) {
        switch(tipoUsuario) {
            case ADMINISTRADOR:
                Tools.ventanaEmergente("templates/mainAdministrador.fxml", "ICaja - Administrador", "styles/main.css");
                break;
            case NORMAL:
                Tools.ventanaEmergente("templates/mainAdministrador.fxml", "ICaja - Administrador", "styles/main.css");
                break;
        }
    }

    @FXML
    void registrarUsuario(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }
}
