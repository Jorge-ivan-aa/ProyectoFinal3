package co.edu.uniquindio.icaja.view;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.exception.login.CredencialesNoCoinciden;
import co.edu.uniquindio.icaja.exception.login.UsuarioNoExiste;
import co.edu.uniquindio.icaja.model.Sesion;
import co.edu.uniquindio.icaja.model.enums.TipoUsuario;
import co.edu.uniquindio.icaja.utils.ViewTools;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class LoginView {

    UsuarioController usuarioController = new UsuarioController();

    @FXML
    private TextField txtCedulaUsuario;

    @FXML
    private MFXPasswordField txtClaveUsuario;

    @FXML
    void iniciarSesion() {
        String clave = txtClaveUsuario.getText();
        String cedula = txtCedulaUsuario.getText();

        if (ViewTools.NoHayCamposVacios(clave, cedula)) {

            Sesion sesion = new Sesion(cedula);

            try {
                TipoUsuario tipoUsuario = sesion.ingresar(clave);
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
                ViewTools.generarVentana("templates/baseAdmin.fxml", "ICaja - Administrador", "carga.fxml",  "styles/main.css");
                break;
            case NORMAL:
                ViewTools.generarVentana("templates/baseNormal.fxml", "ICaja - " + usuario, "carga.fxml", "styles/main.css");
                break;
        }
    }

    @FXML
    void registrarUsuario() {
        ViewTools.generarVentana("templates/registroUsuario.fxml", "ICaja - Registro de usuario", "carga.fxml", "styles/main.css");
        ViewTools.cerrarVentana(txtCedulaUsuario);
    }

    @FXML
    void initialize() {
    }
}
