package co.edu.uniquindio.icaja.view.views.admin;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.utils.ViewTools;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class UsuarioView {
    UsuarioController usuarioController = new UsuarioController();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizarUsuarioAdmin;

    @FXML
    private Button btnAgregarUsuarioAdmin;

    @FXML
    private Button btnEliminarUsuarioAdmin;

    @FXML
    private AnchorPane panelUsuario;

    @FXML
    private TableView<Usuario> tbUsuariosAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcCedulaUsuarioAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcClaveTransaccionalAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcClaveUsuarioAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcCorreoUsuarioAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcNombreUsuarioAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcPresupuestoMensualAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcTelefonoUsuarioAdmin;

    @FXML
    private TextField txtCedulaAdmin;

    @FXML
    private TextField txtClaveAdmin;

    @FXML
    private TextField txtClaveTransaccionalAdmin;

    @FXML
    private TextField txtCorreoAdmin;

    @FXML
    private TextField txtNombreAdmin;

    @FXML
    private TextField txtPresupuestoMensualAdmin;

    @FXML
    private TextField txtTelefonoAdmin;

    @FXML
    void actualizarUsuario(ActionEvent event) {
        String nombre = txtNombreAdmin.getText();
        String cedula = txtCedulaAdmin.getText();
        String correo = txtCorreoAdmin.getText();
        String clave = txtClaveAdmin.getText();
        String claveTransaccional = txtClaveTransaccionalAdmin.getText();
        String presupuestoMensual = txtPresupuestoMensualAdmin.getText();
        String telefono = txtTelefonoAdmin.getText();

        if (!ViewTools.hayCamposVacios(nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional,  presupuestoMensual)) {
            UsuarioDto usuarioDto = new UsuarioDto(nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional, Double.parseDouble(presupuestoMensual));

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
        ViewTools.limpiarCampos(txtCedulaAdmin,
                txtNombreAdmin,
                txtCorreoAdmin,
                txtTelefonoAdmin,
                txtClaveAdmin,
                txtClaveTransaccionalAdmin,
                txtPresupuestoMensualAdmin);

    }

    @FXML
    void crearUsuario(ActionEvent event) {
        String nombre = txtNombreAdmin.getText();
        String cedula = txtCedulaAdmin.getText();
        String correo = txtCorreoAdmin.getText();
        String clave = txtClaveAdmin.getText();
        String claveTransaccional = txtClaveTransaccionalAdmin.getText();
        String presupuestoMensual = txtPresupuestoMensualAdmin.getText();
        String telefono = txtTelefonoAdmin.getText();


        if (!ViewTools.hayCamposVacios(nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional,  presupuestoMensual)) {
            UsuarioDto usuarioDto = new UsuarioDto(nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional, Double.parseDouble(presupuestoMensual));

            try {
                usuarioController.crear(usuarioDto);
                String msj = "Se ha creado el usuario " + nombre + "correctamente";
                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoYaExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }

        ViewTools.limpiarCampos(txtCedulaAdmin,
                txtNombreAdmin,
                txtCorreoAdmin,
                txtTelefonoAdmin,
                txtClaveAdmin,
                txtClaveTransaccionalAdmin,
                txtPresupuestoMensualAdmin);

    }

    @FXML
    void eliminarUsuario(ActionEvent event) {
        String cedula   = txtCedulaAdmin.getText();

        if (!ViewTools.hayCamposVacios(cedula)) {
            try {
                usuarioController.eliminar(cedula);
                String msj = "Se ha eliminado el usuario de cedula" + cedula + "correctamente";
                ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoNoExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }

        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);
        }

        ViewTools.limpiarCampos(txtCedulaAdmin,
                txtNombreAdmin,
                txtCorreoAdmin,
                txtTelefonoAdmin,
                txtClaveAdmin,
                txtClaveTransaccionalAdmin,
                txtPresupuestoMensualAdmin);

    }

    @FXML
    void initialize() {
        initview();
    }
    private void initview() {
        initDataBinging();
        tbUsuariosAdmin.getItems().clear();
        tbUsuariosAdmin.setItems(usuarioController.getListaUsuarioObservable());
        listenerSelectionUsuario();
    }

    private void initDataBinging() {
        tbcNombreUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcCorreoUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        tbcCedulaUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula()));
        tbcClaveTransaccionalAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClaveTransaccional()));
        tbcTelefonoUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        tbcPresupuestoMensualAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPresupuestoMensual())));
        tbcClaveUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClave()));
    }

    private void listenerSelectionUsuario() {
        tbUsuariosAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> this.mostrarInformacion((Usuario) newSelection));
    }

    private void mostrarInformacion(Usuario seleccionado) {
        if (seleccionado != null) {
            txtNombreAdmin.setText(seleccionado.getNombre());
            txtCedulaAdmin.setText(seleccionado.getCedula());
            txtCorreoAdmin.setText(seleccionado.getCorreo());
            txtTelefonoAdmin.setText(seleccionado.getTelefono());
            txtClaveTransaccionalAdmin.setText(seleccionado.getClaveTransaccional());
            txtClaveAdmin.setText(seleccionado.getClave());
            txtPresupuestoMensualAdmin.setText(String.valueOf(seleccionado.getPresupuestoMensual()));
        }
    }
    @FXML
    void limpiarCamposUsuarioAction(ActionEvent event) {
        ViewTools.limpiarCampos(txtCedulaAdmin,
                txtNombreAdmin,
                txtCorreoAdmin,
                txtTelefonoAdmin,
                txtClaveAdmin,
                txtClaveTransaccionalAdmin,
                txtPresupuestoMensualAdmin);
    }

}
