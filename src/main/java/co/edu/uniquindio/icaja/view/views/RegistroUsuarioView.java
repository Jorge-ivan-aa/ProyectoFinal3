package co.edu.uniquindio.icaja.view.views;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.utils.ViewTools;
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
    void registrarUsuarioAction() {

        String nombre = txtNombreUsuario.getText();
        String cedula = txtCedulaUsuario.getText();
        String correo = txtCorreoUsuario.getText();
        String clave = txtClaveUsuario.getText();
        String claveTransaccional = txtClaveTranUsuario.getText();
        String presupuestoMensual = txtPresupuestoUsuario.getText();
        String telefono = txtTelefonoUsuario.getText();

        if (!ViewTools.hayCamposVacios(claveTransaccional)) {
            UsuarioDto usuarioDto = new UsuarioDto(nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional, Double.parseDouble(presupuestoMensual));
            try {
                usuarioController.crear(usuarioDto);
                String msj = "El registro ha sido exitoso, ¡Bienvenido " + nombre + "!";
                ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoYaExiste e) {
                ViewTools.mostrarMensaje("¡Lo sentimos!", null, e.getMessage(), Alert.AlertType.ERROR);
            }


        } else {
            ViewTools.mostrarMensaje("¡Cuidado!", null, "No has ingresado una clave transaccional", Alert.AlertType.WARNING);
        }

        ViewTools.limpiarCampos(txtCedulaUsuario,
                txtNombreUsuario,
                txtCorreoUsuario,
                txtTelefonoUsuario,
                txtClaveUsuario,
                txtClaveTranUsuario,
                txtPresupuestoUsuario);

        ViewTools.ventanaEmergente("login.fxml", "ICaja :)", "carga.fxml", "styles/main.css", "styles/login.css");
        ViewTools.cerrarVentana(txtCedulaUsuario);
    }

    @FXML
    void continuar1Action() {
        String nombre = txtNombreUsuario.getText();
        String cedula = txtCedulaUsuario.getText();
        String correo = txtCorreoUsuario.getText();
        String clave = txtClaveUsuario.getText();
        String claveConfirmacion =  txtClaveUsuario1.getText();
        String telefono = txtTelefonoUsuario.getText();

        if(!ViewTools.hayCamposVacios(nombre, cedula, correo, telefono, clave, claveConfirmacion)){
            if (clave.equals(claveConfirmacion)){
                ViewTools.cambiarPantalla(pane2,0.225, pane1, pane3);
            } else {
                ViewTools.mostrarMensaje("¡Cuidado!", null, "Las contraseñas no coinciden", Alert.AlertType.WARNING);
            }
        } else {
            ViewTools.mostrarMensaje("¡Cuidado!", null, "Hay campos vacíos", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void continuar2Action() {
        String presupuestoMensual = txtPresupuestoUsuario.getText();

        if(!ViewTools.hayCamposVacios(presupuestoMensual)){
            try {
                int number = Integer.parseInt(presupuestoMensual);
                if (number > 0) {
                    ViewTools.cambiarPantalla(pane3,0.225, pane1, pane2);
                } else {
                    ViewTools.mostrarMensaje("¡Cuidado!", null, "El presupuesto debe ser mayor a cero.", Alert.AlertType.WARNING);
                }
            } catch (NumberFormatException ignore) {
                ViewTools.mostrarMensaje("¡Cuidado!", null, "El presupuesto debe ser un numero.", Alert.AlertType.WARNING);
            }
            
        } else {
            ViewTools.mostrarMensaje("¡Cuidado!", null, "No has ingresado ningun presupuesto.", Alert.AlertType.WARNING);
        }
    }


    @FXML
    void volverAction() {
        ViewTools.ventanaEmergente("login.fxml", "ICaja :)", "carga.fxml", "styles/main.css", "styles/login.css");
        ViewTools.cerrarVentana(txtCedulaUsuario);
    }


    @FXML
    void initialize() {
        ViewTools.cambiarPantalla(pane1,0.225, pane3, pane2);
    }
}