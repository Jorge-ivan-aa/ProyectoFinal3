package co.edu.uniquindio.icaja.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class CrearUsuarioView {
    UsuarioController usuarioController= new UsuarioController();

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

        if (!Tools.hayCamposVacios(nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional,  presupuestoMensual)) {
            String resultado = usuarioController.crearUsuario(nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional, Double.parseDouble(presupuestoMensual));
            Tools.mostrarMensaje("Información", null, resultado, Alert.AlertType.INFORMATION);
        } else {
            Tools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }
        Tools.limpiarCampos(txtCedulaUsuario,
                txtNombreUsuario,
                txtCorreoUsuario,
                txtTelefonoUsuario,
                txtClaveUsuario,
                txtClaveTranUsuario,
                txtPresupuestoUsuario);

    }

    @FXML
    void volverAction(ActionEvent event) {
        Tools.ventanaEmergente("login.fxml", "ICaja :)", "styles/main.css");
        Tools.cerrarVentana(txtCedulaUsuario);


    }


    @FXML
    void initialize() {


    }

}
