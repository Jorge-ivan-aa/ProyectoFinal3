package co.edu.uniquindio.icaja.view.views.normal;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.model.Sesion;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class PerfilUsuarioView {
    UsuarioController usuarioController= new UsuarioController();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXTextField txtNuevaCedulaUsuario;

    @FXML
    private MFXTextField txtNuevoCorreoUsuario;

    @FXML
    private MFXTextField txtNuevoNombreUsuario;

    @FXML
    private MFXTextField txtNuevoTelefonoUsuario;

    @FXML
    void ActualizarDatosUsuarioAction(ActionEvent event) {
//        String nombre = txtNuevoNombreUsuario.getText();
//        String cedula = txtNuevaCedulaUsuario.getText();
//        String correo = txtNuevoCorreoUsuario.getText();
//        //String clave = txtClaveAdmin.getText();
//        //String claveTransaccional = txtClaveTransaccionalAdmin.getText();
//        String telefono = txtNuevoTelefonoUsuario.getText();
//
//        //boolean cambioClaves =  !clave.isEmpty() || !claveTransaccional.isEmpty();
//
//        if (ViewTools.NoHayCamposVacios(nombre, cedula, correo, telefono)) {
//            UsuarioDto usuarioDto = new UsuarioDto(id, nombre,  cedula,  correo,  telefono, "1222" ,"2111"  );
//            try {
//                usuarioController.actualizar(usuarioDto);
//                String msj = "Se ha actualizado el usuario de cedula" + cedula + "correctamente";
//                ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);
//
//            } catch (ElementoNoExiste e) {
//                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
//            }
//        } else {
//            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);
//
//        }
//        ViewTools.limpiarCampos(txtNuevaCedulaUsuario,
//                txtNuevoNombreUsuario,
//                txtNuevoCorreoUsuario,
//                txtNuevoTelefonoUsuario
//                );

        Sesion sesion = usuarioController.getFactory().getIcaja().getSesion();
        Usuario usuarioLogeado = sesion.getUsuario();

    }

    @FXML
    void cambiarClaveTransaccionalUsuarioAction(ActionEvent event) {

    }

    @FXML
    void cambiarContraseñaIngresoAction(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
