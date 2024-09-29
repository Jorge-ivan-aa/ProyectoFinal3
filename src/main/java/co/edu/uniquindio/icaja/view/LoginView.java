package co.edu.uniquindio.icaja.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.controller.UsuarioController;
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

    UsuarioController usuarioController = new UsuarioController();

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
        String clave = txtClaveUsuario.getText();
        String cedula = txtCedulaUsuario.getText();

        if (!Tools.hayCamposVacios(clave, cedula)) {
            UsuarioProxy sesion = new UsuarioProxy(cedula, clave);

            try {
                TipoUsuario tipoUsuario = sesion.ingresar();
                usuarioController.getFactory().getIcaja().setSesion(sesion);
                seleccionarInterfax(tipoUsuario, sesion.getUsuario().getNombre());
                Tools.cerrarVentana(txtCedulaUsuario);

            } catch (UsuarioNoExiste | CredencialesNoCoinciden e) {
                Tools.mostrarMensaje("¡Lo sentimos!", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            Tools.mostrarMensaje("¡Lo sentimos!", null, "Hay campos vacios.", Alert.AlertType.ERROR);
        }

    }

    void seleccionarInterfax(TipoUsuario tipoUsuario, String usuario) {
        switch(tipoUsuario) {
            case ADMINISTRADOR:
                Tools.ventanaEmergente("templates/mainAdministrador.fxml", "ICaja - Administrador", "styles/main.css");
                break;
            case NORMAL:
                Tools.ventanaEmergente("templates/mainNormal.fxml", "ICaja - " + usuario, "styles/main.css");
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
