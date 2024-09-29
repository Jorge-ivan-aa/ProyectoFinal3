package co.edu.uniquindio.icaja.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CrearUsuarioView {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizarUsuario;

    @FXML
    private Button btnAgregarUsuario;

    @FXML
    private Button btnEliminarUsuario;

    @FXML
    private TableView<Usuario> tbUsuarios;

    @FXML
    private TableColumn<Usuario, String> tbcCedulaUsuario;

    @FXML
    private TableColumn<Usuario, String> tbcClaveTransaccional;

    @FXML
    private TableColumn<Usuario, String> tbcClaveUsuario;

    @FXML
    private TableColumn<Usuario, String> tbcCorreoUsuario;

    @FXML
    private TableColumn<Usuario, String> tbcNombreUsuario;

    @FXML
    private TableColumn<?, ?> tbcPresupuestoMensual;

    @FXML
    private TableColumn<?, ?> tbcSaldoTotal;

    @FXML
    private TableColumn<Usuario, String> tbcTelefonoUsuario;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtClave;

    @FXML
    private TextField txtClaveTransaccional;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPresupuestoMens;

    @FXML
    private TextField txtSaldoTotal;

    @FXML
    private TextField txtTelefono;

    public CrearUsuarioView(TableColumn<?, ?> tbcCedulaUsuario) {
        this.tbcCedulaUsuario = (TableColumn<Usuario, String>) tbcCedulaUsuario;
    }

    @FXML
    void agregarUsuario(ActionEvent event) {
        String nombre = txtNombre.getText();
        String cedula = txtCedula.getText();
        String correo = txtCorreo.getText();
        String clave = txtClave.getText();
        String claveTransaccional = txtClaveTransaccional.getText();
        String presupuestoMensual = txtPresupuestoMens.getText();
        String telefono = txtTelefono.getText();

        if (!Tools.hayCamposVacios( nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional,  presupuestoMensual)) {
            String resultado = UsuarioController.crearUsuario( nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional,  presupuestoMensual);
            Tools.mostrarMensaje("Información", null, resultado, Alert.AlertType.INFORMATION);
        } else {
            Tools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);


        }
        Tools.limpiarCampos(txtCedula,
                            txtNombre,
                            txtCorreo,
                            txtTelefono);
    }

    @FXML
    void actualizarUsuario(ActionEvent event) {
        String nombre = txtNombre.getText();
        String cedula = txtCedula.getText();
        String correo = txtCorreo.getText();
        String telefono = txtTelefono.getText();

        if (!Tools.hayCamposVacios(nombre, cedula, correo, telefono)) {
            String resultado = UsuarioController.actualizarUsuario(cedula, nombre, correo, telefono);
            Tools.mostrarMensaje("Información", null, resultado, Alert.AlertType.INFORMATION);
        } else {
            Tools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }
        Tools.limpiarCampos(txtCedula,
                            txtNombre,
                            txtCorreo,
                            txtTelefono);
    }

    @FXML
    void eliminarUsuario(ActionEvent event) {
        String nombre = txtNombre.getText();

        if (!Tools.hayCamposVacios(nombre)) {
            String resultado = UsuarioController.eliminarUsuario(nombre);
            Tools.mostrarMensaje("Información", null, resultado, Alert.AlertType.INFORMATION);
        } else {
            Tools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);
        }

        Tools.limpiarCampos(txtCorreo,
                            txtNombre,
                            txtTelefono);
    }

    @FXML
    void initialize() {
        initview();
    }

    private void initview() {
        initDataBinging();
        tbUsuarios.getItems().clear();
        tbUsuarios.setItems(UsuarioController.getListaUsuarioObservable());
        listenerSelectionUsuario();
    }

    private void initDataBinging() {
        tbcNombreUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcCorreoUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        tbcCedulaUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcClaveTransaccional.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClaveTransaccional()));
        tbcTelefonoUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
    }

    private void listenerSelectionUsuario() {
        tbUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            this.mostrarInformacion((Usuario) newSelection);
        });
    }

    private void mostrarInformacion(Usuario seleccionado) {
        if (seleccionado != null) {
            txtNombre.setText(seleccionado.getNombre());
            txtCedula.setText(seleccionado.getCedula());
            txtCorreo.setText(seleccionado.getCorreo());
            txtTelefono.setText(seleccionado.getTelefono());
        }
    }
}
