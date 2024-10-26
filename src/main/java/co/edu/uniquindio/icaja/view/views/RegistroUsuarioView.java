package co.edu.uniquindio.icaja.view.views;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.utils.ViewTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class RegistroUsuarioView {
    UsuarioController usuarioController= new UsuarioController();

    @FXML
    private Pane pane1;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane3;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtCedulaUsuario;

    @FXML
    private TextField txtClaveTranUsuario;

    @FXML
    private TextField txtClaveUsuario;

    @FXML
    private TextField txtClaveUsuario1;

    @FXML
    private TextField txtCorreoUsuario;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtPresupuestoUsuario;

    @FXML
    private TextField txtTelefonoUsuario;

    @FXML
    void registrarUsuarioAction(ActionEvent event) {
        String nombre = txtNombreUsuario.getText();
        String cedula = txtCedulaUsuario.getText();
        String correo = txtCorreoUsuario.getText();
        String clave = txtClaveUsuario.getText();
        String claveTransaccional = txtClaveTranUsuario.getText();
        String presupuestoMensual = txtPresupuestoUsuario.getText();
        String telefono = txtTelefonoUsuario.getText();

        if (!ViewTools.hayCamposVacios(nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional,  presupuestoMensual)) {
            UsuarioDto usuarioDto = new UsuarioDto(nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional, Double.parseDouble(presupuestoMensual));
            try {
                usuarioController.crear(usuarioDto);
                String msj = "El registro ha sido exitoso, ¡Bienvenido " + nombre + "!";
                ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoYaExiste e) {
                ViewTools.mostrarMensaje("¡Lo sentimos!", null, e.getMessage(), Alert.AlertType.ERROR);
            }


        } else {
            ViewTools.mostrarMensaje("¡Cuidado!", null, "Hay campos vacíos", Alert.AlertType.WARNING);
        }
        ViewTools.limpiarCampos(txtCedulaUsuario,
                txtNombreUsuario,
                txtCorreoUsuario,
                txtTelefonoUsuario,
                txtClaveUsuario,
                txtClaveTranUsuario,
                txtPresupuestoUsuario);

    }

    @FXML
    void continuar1Action(ActionEvent event) {
        ViewTools.cambiarPantalla(pane2,0.225, pane1, pane3);
    }

    @FXML
    void continuar2Action(ActionEvent event) {
        ViewTools.cambiarPantalla(pane3,0.225, pane1, pane2);

    }


        @FXML
    void volverAction(ActionEvent event) {
        ViewTools.ventanaEmergente("login.fxml", "ICaja :)", "styles/main.css");
        ViewTools.cerrarVentana(txtCedulaUsuario);


    }


    @FXML
    void initialize() {
        ViewTools.cambiarPantalla(pane1,0.225, pane3, pane2);
    }

}
