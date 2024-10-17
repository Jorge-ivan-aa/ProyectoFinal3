package co.edu.uniquindio.icaja.view.views.admin;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class TransaccionView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXComboBox<?> cbTipoTransaccionAdmin;

    @FXML
    private TableColumn<?, ?> tcCategoriaTransaccionAdmin;

    @FXML
    private MFXComboBox<?> tcCuentaTransaccionAdmin;

    @FXML
    private TableColumn<?, ?> tcFechaTransaccionAdmin;

    @FXML
    private TableColumn<?, ?> tcIdTransaccionAdmin;

    @FXML
    private TableColumn<?, ?> tcMontoTransaccionAdmin;

    @FXML
    private TableColumn<?, ?> tcMotivoTransaccionAdmin;

    @FXML
    private AnchorPane transaccionPanel;

    @FXML
    private TableView<?> tvTablaTransaccionaAdmin;

    @FXML
    private MFXTextField txtFechaTransaccionAdmin;

    @FXML
    private MFXTextField txtIdTransaccionAdmin;

    @FXML
    private MFXTextField txtMontoTransaccionAdmin;

    @FXML
    private MFXTextField txtMotivoTransaccionAdmin;

    @FXML
    void consultarTransaccionAction(ActionEvent event) {

    }

    @FXML
    void crearTransaccionAction(ActionEvent event) {

    }

    @FXML
    void eliminarTransaccionAction(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
