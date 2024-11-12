package co.edu.uniquindio.icaja.view.views.normal;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CuentasUsuarioView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXComboBox<?> cbEntidadCuentaUsuario;

    @FXML
    private MFXComboBox<?> cbTipoCuentaUsuario;

    @FXML
    private MFXListView<?> lvListaCuentasUsuario;

    @FXML
    private MFXTextField txtNumeroCuentaUsuario;

    @FXML
    private MFXTextField txtSaldoCuentaUsuario;

    @FXML
    void crearCuentaUsuarioAction(ActionEvent event) {

    }

    @FXML
    void eliminarCuentaUsuarioAction(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
