package co.edu.uniquindio.icaja.view.views.admin;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CuentaView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXComboBox<?> cbPropietarioAdmin;

    @FXML
    private ComboBox<?> cbTipoCuentaAdmin;

    @FXML
    private AnchorPane panelCuenta;

    @FXML
    private TableColumn<?, ?> tcEntidadAdmin;

    @FXML
    private TableColumn<?, ?> tcLimiteAdmin;

    @FXML
    private TableColumn<?, ?> tcNumeroCuentaAdmin;

    @FXML
    private TableColumn<?, ?> tcSaldoAdmin;

    @FXML
    private TableColumn<?, ?> tcTipoCuentaAdmin;

    @FXML
    private TableView<?> tvTablaCuentasAdmin;

    @FXML
    private TextField txtEntidadAdmin;

    @FXML
    private TextField txtLimiteAdmin;

    @FXML
    private TextField txtNumeroCuentaAdmin;

    @FXML
    private TextField txtSaldoAdmin;

    @FXML
    void ActualizarCuentaAction(ActionEvent event) {

    }

    @FXML
    void crearCuentaAction(ActionEvent event) {

    }

    @FXML
    void eliminarCuentaAction(ActionEvent event) {

    }

    @FXML
    void seleccionPropietarioAction(ActionEvent event) {

    }

    @FXML
    void seleccionTipoCuentaAction(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
