package co.edu.uniquindio.icaja.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.exception.CredencialesNoCoinciden;
import co.edu.uniquindio.icaja.exception.UsuarioNoExiste;
import co.edu.uniquindio.icaja.model.Sesion;
import co.edu.uniquindio.icaja.model.enums.TipoUsuario;
import co.edu.uniquindio.icaja.utils.ViewTools;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
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
    private MFXPasswordField txtClaveUsuario;

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

        if (!ViewTools.hayCamposVacios(clave, cedula)) {
            Sesion sesion = new Sesion(cedula, clave);

            try {
                TipoUsuario tipoUsuario = sesion.ingresar();
                usuarioController.getFactory().getIcaja().setSesion(sesion);
                seleccionarInterfax(tipoUsuario, sesion.getUsuario().getNombre());
                ViewTools.cerrarVentana(txtCedulaUsuario);

            } catch (UsuarioNoExiste | CredencialesNoCoinciden e) {
                ViewTools.mostrarMensaje("¡Lo sentimos!", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            ViewTools.mostrarMensaje("¡Lo sentimos!", null, "Hay campos vacios.", Alert.AlertType.ERROR);
        }

    }

    void seleccionarInterfax(TipoUsuario tipoUsuario, String usuario) {
        switch(tipoUsuario) {
            case ADMINISTRADOR:
                ViewTools.ventanaEmergente("templates/mainAdmin.fxml", "ICaja - Administrador", "styles/main.css");
                break;
            case NORMAL:
                ViewTools.ventanaEmergente("templates/mainNormal.fxml", "ICaja - " + usuario, "styles/main.css");
                break;
        }
    }

    @FXML
    void registrarUsuario(ActionEvent event) {
        ViewTools.ventanaEmergente("templates/registroUsuario.fxml", "ICaja - Registro de usuario", "styles/main.css");
        ViewTools.cerrarVentana(txtCedulaUsuario);
    }

    @FXML
    void initialize() {

    }
}
