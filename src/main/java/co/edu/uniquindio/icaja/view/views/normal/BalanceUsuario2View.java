package co.edu.uniquindio.icaja.view.views.normal;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BalanceUsuario2View {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXComboBox<?> cbCategoriasBalance;

    @FXML
    private TableColumn<?, ?> tcCategoriaBalance;

    @FXML
    private TableColumn<?, ?> tcIdBalance;

    @FXML
    private TableColumn<?, ?> tcMontoAsignadoBalance;

    @FXML
    private TableColumn<?, ?> tcMontoGastadoBalance;

    @FXML
    private TableColumn<?, ?> tcNombreBalance;

    @FXML
    private TableView<?> tvListaBalances;

    @FXML
    private MFXTextField txtMontoBalance;

    @FXML
    private MFXTextField txtNombreBalance;

    @FXML
    void actualizarBalanceAction(ActionEvent event) {

    }

    @FXML
    void crearBalanceAction(ActionEvent event) {

    }

    @FXML
    void eliminarBalanceAction(ActionEvent event) {

    }

    @FXML
    void limpiarCamposAction(ActionEvent event) {

    }

    @FXML
    void volverAction(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}



