package co.edu.uniquindio.icaja.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private TableView<?> tbUsuarios;

    @FXML
    private TableColumn<?, ?> tbcCedulaUsuario;

    @FXML
    private TableColumn<?, ?> tbcClaveTransaccional;

    @FXML
    private TableColumn<?, ?> tbcClaveUsuario;

    @FXML
    private TableColumn<?, ?> tbcCorreoUsuario;

    @FXML
    private TableColumn<?, ?> tbcNombreUsuario;

    @FXML
    private TableColumn<?, ?> tbcPresupuestoMensual;

    @FXML
    private TableColumn<?, ?> tbcSaldoTotal;

    @FXML
    private TableColumn<?, ?> tbcTelefonoUsuario;

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

    @FXML
    void actualizarUsuario(ActionEvent event) {

    }

    @FXML
    void agregarUsuario(ActionEvent event) {

    }

    @FXML
    void eliminarUsuario(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnActualizarUsuario != null : "fx:id=\"btnActualizarUsuario\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert btnAgregarUsuario != null : "fx:id=\"btnAgregarUsuario\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert btnEliminarUsuario != null : "fx:id=\"btnEliminarUsuario\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert tbUsuarios != null : "fx:id=\"tbUsuarios\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert tbcCedulaUsuario != null : "fx:id=\"tbcCedulaUsuario\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert tbcClaveTransaccional != null : "fx:id=\"tbcClaveTransaccional\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert tbcClaveUsuario != null : "fx:id=\"tbcClaveUsuario\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert tbcCorreoUsuario != null : "fx:id=\"tbcCorreoUsuario\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert tbcNombreUsuario != null : "fx:id=\"tbcNombreUsuario\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert tbcPresupuestoMensual != null : "fx:id=\"tbcPresupuestoMensual\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert tbcSaldoTotal != null : "fx:id=\"tbcSaldoTotal\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert tbcTelefonoUsuario != null : "fx:id=\"tbcTelefonoUsuario\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert txtCedula != null : "fx:id=\"txtCedula\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert txtClave != null : "fx:id=\"txtClave\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert txtClaveTransaccional != null : "fx:id=\"txtClaveTransaccional\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert txtCorreo != null : "fx:id=\"txtCorreo\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert txtNombre != null : "fx:id=\"txtNombre\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert txtPresupuestoMens != null : "fx:id=\"txtPresupuestoMens\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert txtSaldoTotal != null : "fx:id=\"txtSaldoTotal\" was not injected: check your FXML file 'crearUsuario.fxml'.";
        assert txtTelefono != null : "fx:id=\"txtTelefono\" was not injected: check your FXML file 'crearUsuario.fxml'.";

    }
}
