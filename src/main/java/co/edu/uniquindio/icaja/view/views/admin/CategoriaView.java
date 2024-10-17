package co.edu.uniquindio.icaja.view.views.admin;

import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class CategoriaView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane categorialpanel;

    @FXML
    private TableColumn<?, ?> tcDescripcionCategoriaAdmin;

    @FXML
    private TableColumn<?, ?> tcNombreCategoriaAdmin;

    @FXML
    private TableColumn<?, ?> tcTipoCategoriaAdmin;

    @FXML
    private TableView<?> tvCategoriaAdmin;

    @FXML
    private TextArea txaDescripcionCategoriaAdmin;

    @FXML
    private MFXTextField txtNombreCategoriaAdmin;

    @FXML
    private MFXTextField txtTipoCategoriaAdmin;

    @FXML
    void EliminarCategoriaAction(ActionEvent event) {

    }

    @FXML
    void consultarCategoriaAction(ActionEvent event) {

    }

    @FXML
    void crearCategoriaAction(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
