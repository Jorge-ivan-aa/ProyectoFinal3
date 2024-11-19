package co.edu.uniquindio.icaja.view.views.normal;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

public class PerfilUsuarioView {
    UsuarioController usuarioController= new UsuarioController();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Pane paneActualizarDatos;

    @FXML
    private Pane paneCambiarContrasenaIng;

    @FXML
    private Pane panelCambiarClaveTran;

    @FXML
    private MFXTextField txtConfirmarContrasenaTran;

    @FXML
    private MFXTextField txtNuevaContrasenaTran;

    private MFXTextField txtConfirmarContrasena;

    @FXML
    private MFXTextField txtNuevaContrasena;

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
        String nombre = txtNuevoNombreUsuario.getText();
        String cedula = txtNuevaCedulaUsuario.getText();
        String correo = txtNuevoCorreoUsuario.getText();
        //String clave = txtClaveAdmin.getText();
        //String claveTransaccional = txtClaveTransaccionalAdmin.getText();
        String telefono = txtNuevoTelefonoUsuario.getText();

        //boolean cambioClaves =  !clave.isEmpty() || !claveTransaccional.isEmpty();

        if (ViewTools.NoHayCamposVacios(nombre, cedula, correo, telefono)) {
            UsuarioDto usuarioDto = new UsuarioDto(null,nombre,  cedula,  correo,  telefono, "" ,"" );
            try {
                usuarioController.actualizar(usuarioDto);
                String msj = "Se ha actualizado el usuario de cedula" + cedula + "correctamente";
                ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);

            } catch (ElementoNoExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }
        ViewTools.limpiarCampos(txtNuevaCedulaUsuario,
                txtNuevoNombreUsuario,
                txtNuevoCorreoUsuario,
                txtNuevoTelefonoUsuario
                );


    }

    @FXML
    void cambiarClaveTransaccionalUsuarioAction(ActionEvent event) {
        ViewTools.cambiarPantalla(panelCambiarClaveTran,0.225, paneActualizarDatos, paneCambiarContrasenaIng);
    }

    @FXML
    void cambiarContrasenaIngresoAction(ActionEvent event) {
        ViewTools.cambiarPantalla(paneCambiarContrasenaIng,0.225, paneActualizarDatos, panelCambiarClaveTran);
    }
    @FXML
    void calificarAction(ActionEvent event) {

    }
    @FXML
    void configurarAction(ActionEvent event) {
        //DEJAR LAS CONTRASEÑAS CON ""
        String nuevaContra =txtNuevaContrasena.getText();
        String confirmarContra = txtConfirmarContrasena.getText();
//        if (nuevaContra== confirmarContra){
//            UsuarioDto usuarioDto = new UsuarioDto(null,null,  null,  null,  null, confirmarContra ,""  );
//            try {
//                usuarioController.actualizar(usuarioDto);
//                String msj = "Se ha actualizado el usuario de cedula" + cedula + "correctamente";
//                ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);
//
//            } catch (ElementoNoExiste e) {
//                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
//            }
//            String msj = "Se ha actualizado la contraseña correctamente";
//            ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);
//        }else{
//            String msj = "No se pudo actualizar la contraseña correctamente";
//            ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);
//        }

    }
    @FXML
    void configurarTranAction(ActionEvent event) {
        String nuevaContraTran= txtNuevaContrasenaTran.getText();
        String configurarContraTran = txtConfirmarContrasenaTran.getText();
        if(nuevaContraTran == configurarContraTran){

        }

    }

    @FXML
    void SalirTranAction(ActionEvent event) {
        ViewTools.cambiarPantalla(paneActualizarDatos,0.225, panelCambiarClaveTran, paneCambiarContrasenaIng);
    }

    @FXML
    void salirAction(ActionEvent event) {
        ViewTools.cambiarPantalla(paneActualizarDatos,0.225, paneCambiarContrasenaIng, panelCambiarClaveTran);
    }

    @FXML
    void initialize() {

    }

}
