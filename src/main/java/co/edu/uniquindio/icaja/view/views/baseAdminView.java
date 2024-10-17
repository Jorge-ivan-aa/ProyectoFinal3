package co.edu.uniquindio.icaja.view.views;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.utils.ViewTools;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class baseAdminView {

    UsuarioController usuarioController = new UsuarioController();

    @FXML
    private Button btnActualizarUsuarioAdmin;

    @FXML
    private Button btnAgregarUsuarioAdmin;

    @FXML
    private Button btnEliminarUsuarioAdmin;

    @FXML
    private AnchorPane categorialpanel;

    @FXML
    private MFXComboBox<?> cbPropietarioAdmin;

    @FXML
    private ComboBox<?> cbTipoCuentaAdmin;

    @FXML
    private MFXComboBox<?> cbTipoTransaccionAdmin;

    @FXML
    private AnchorPane panelCuenta;

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
    private TableColumn<?, ?> tcCategoriaTransaccionAdmin;

    @FXML
    private MFXComboBox<?> tcCuentaTransaccionAdmin;

    @FXML
    private TableColumn<?, ?> tcDescripcionCategoriaAdmin;

    @FXML
    private TableColumn<?, ?> tcEntidadAdmin;

    @FXML
    private TableColumn<?, ?> tcFechaTransaccionAdmin;

    @FXML
    private TableColumn<?, ?> tcIdTransaccionAdmin;

    @FXML
    private TableColumn<?, ?> tcLimiteAdmin;

    @FXML
    private TableColumn<?, ?> tcMontoTransaccionAdmin;

    @FXML
    private TableColumn<?, ?> tcMotivoTransaccionAdmin;

    @FXML
    private TableColumn<?, ?> tcNombreCategoriaAdmin;

    @FXML
    private TableColumn<?, ?> tcNumeroCuentaAdmin;

    @FXML
    private TableColumn<?, ?> tcSaldoAdmin;

    @FXML
    private TableColumn<?, ?> tcTipoCategoriaAdmin;

    @FXML
    private TableColumn<?, ?> tcTipoCuentaAdmin;

    @FXML
    private AnchorPane transaccionPanel;

    @FXML
    private TableView<?> tvCategoriaAdmin;

    @FXML
    private TableView<?> tvTablaCuentasAdmin;

    @FXML
    private TableView<?> tvTablaTransaccionaAdmin;

    @FXML
    private TextArea txaDescripcionCategoriaAdmin;

    @FXML
    private TextField txtCedulaAdmin;

    @FXML
    private TextField txtClaveAdmin;

    @FXML
    private TextField txtClaveTransaccionalAdmin;

    @FXML
    private TextField txtCorreoAdmin;

    @FXML
    private TextField txtEntidadAdmin;

    @FXML
    private MFXTextField txtFechaTransaccionAdmin;

    @FXML
    private MFXTextField txtIdTransaccionAdmin;

    @FXML
    private TextField txtLimiteAdmin;

    @FXML
    private MFXTextField txtMontoTransaccionAdmin;

    @FXML
    private MFXTextField txtMotivoTransaccionAdmin;

    @FXML
    private TextField txtNombreAdmin;

    @FXML
    private MFXTextField txtNombreCategoriaAdmin;

    @FXML
    private TextField txtNumeroCuentaAdmin;

    @FXML
    private TextField txtPresupuestoMensualAdmin;

    @FXML
    private TextField txtSaldoAdmin;

    @FXML
    private TextField txtTelefonoAdmin;

    @FXML
    private MFXTextField txtTipoCategoriaAdmin;

    @FXML
    void ActualizarCuentaAction(ActionEvent event) {

    }

    @FXML
    void EliminarCategoriaAction(ActionEvent event) {

    }

    @FXML
    void VolverAction(ActionEvent event) {

    }

    @FXML
    void actualizarUsuario(ActionEvent event) {

    }

    @FXML
    void consultarCategoriaAction(ActionEvent event) {

    }

    @FXML
    void consultarTransaccionAction(ActionEvent event) {

    }

    @FXML
    void crearCategoriaAction(ActionEvent event) {

    }

    @FXML
    void crearCuentaAction(ActionEvent event) {

    }

    @FXML
    void crearTransaccionAction(ActionEvent event) {

    }

    @FXML
    void crearUsuario(ActionEvent event) {

    }

    @FXML
    void eliminarCuentaAction(ActionEvent event) {

    }

    @FXML
    void eliminarTransaccionAction(ActionEvent event) {

    }

    @FXML
    void eliminarUsuario(ActionEvent event) {

    }

    @FXML
    void ponerCategoriaAction(ActionEvent event) {

    }

    @FXML
    void ponerCuentaAction(ActionEvent event) {

    }

    @FXML
    void ponerTransaccionAction(ActionEvent event) {

    }

    @FXML
    void ponerUsuarioAction(ActionEvent event) {

    }

    @FXML
    void seleccionPropietarioAction(ActionEvent event) {

    }

    @FXML
    void seleccionTipoCuentaAction(ActionEvent event) {

    }

//    @FXML
//    void crearUsuario() {
//        String nombre = txtNombreAdmin.getText();
//        String cedula = txtCedulaAdmin.getText();
//        String correo = txtCorreoAdmin.getText();
//        String clave = txtClaveAdmin.getText();
//        String claveTransaccional = txtClaveTransaccionalAdmin.getText();
//        String presupuestoMensual = txtPresupuestoMensualAdmin.getText();
//        String telefono = txtTelefonoAdmin.getText();
//
//
//        if (!ViewTools.hayCamposVacios(nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional,  presupuestoMensual)) {
//            UsuarioDto usuarioDto = new UsuarioDto(nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional, Double.parseDouble(presupuestoMensual));
//
//            try {
//                usuarioController.crear(usuarioDto);
//                String msj = "Se ha creado el usuario " + nombre + "correctamente";
//                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
//            } catch (ElementoYaExiste e) {
//                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
//            }
//        } else {
//            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);
//
//        }
//
//        ViewTools.limpiarCampos(txtCedulaAdmin,
//                txtNombreAdmin,
//                txtCorreoAdmin,
//                txtTelefonoAdmin,
//                txtClaveAdmin,
//                txtClaveTransaccionalAdmin,
//                txtPresupuestoMensualAdmin);
//    }
//
//    @FXML
//    void actualizarUsuario() {
//        String nombre = txtNombreAdmin.getText();
//        String cedula = txtCedulaAdmin.getText();
//        String correo = txtCorreoAdmin.getText();
//        String clave = txtClaveAdmin.getText();
//        String claveTransaccional = txtClaveTransaccionalAdmin.getText();
//        String presupuestoMensual = txtPresupuestoMensualAdmin.getText();
//        String telefono = txtTelefonoAdmin.getText();
//
//        if (!ViewTools.hayCamposVacios(nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional,  presupuestoMensual)) {
//            UsuarioDto usuarioDto = new UsuarioDto(nombre,  cedula,  correo,  telefono,  clave,  claveTransaccional, Double.parseDouble(presupuestoMensual));
//
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
//        ViewTools.limpiarCampos(txtCedulaAdmin,
//                txtNombreAdmin,
//                txtCorreoAdmin,
//                txtTelefonoAdmin,
//                txtClaveAdmin,
//                txtClaveTransaccionalAdmin,
//                txtPresupuestoMensualAdmin);
//    }
//
//    @FXML
//    void eliminarUsuario() {
//        String cedula   = txtCedulaAdmin.getText();
//
//        if (!ViewTools.hayCamposVacios(cedula)) {
//            try {
//                usuarioController.eliminar(cedula);
//                String msj = "Se ha eliminado el usuario de cedula" + cedula + "correctamente";
//                ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);
//            } catch (ElementoNoExiste e) {
//                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
//            }
//
//        } else {
//            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);
//        }
//
//        ViewTools.limpiarCampos(txtCedulaAdmin,
//                txtNombreAdmin,
//                txtCorreoAdmin,
//                txtTelefonoAdmin,
//                txtClaveAdmin,
//                txtClaveTransaccionalAdmin,
//                txtPresupuestoMensualAdmin);
//    }

    @FXML
    void initialize() {
        initview();
    }

    private void initview() {
//        initDataBinging();
//        tbUsuariosAdmin.getItems().clear();
//        tbUsuariosAdmin.setItems(usuarioController.getListaUsuarioObservable());
//        listenerSelectionUsuario();
    }

//    private void initDataBinging() {
//        tbcNombreUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
//        tbcCorreoUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
//        tbcCedulaUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula()));
//        tbcClaveTransaccionalAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClaveTransaccional()));
//        tbcTelefonoUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
//        tbcPresupuestoMensualAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPresupuestoMensual())));
//        tbcClaveUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClave()));
//    }
//
//    private void listenerSelectionUsuario() {
//        tbUsuariosAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
//                -> this.mostrarInformacion((Usuario) newSelection));
//    }

//    private void mostrarInformacion(Usuario seleccionado) {
//        if (seleccionado != null) {
//            txtNombreAdmin.setText(seleccionado.getNombre());
//            txtCedulaAdmin.setText(seleccionado.getCedula());
//            txtCorreoAdmin.setText(seleccionado.getCorreo());
//            txtTelefonoAdmin.setText(seleccionado.getTelefono());
//            txtClaveTransaccionalAdmin.setText(seleccionado.getClaveTransaccional());
//            txtClaveAdmin.setText(seleccionado.getClave());
//            txtPresupuestoMensualAdmin.setText(String.valueOf(seleccionado.getPresupuestoMensual()));
//        }
//    }
}
