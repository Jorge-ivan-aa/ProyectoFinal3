package co.edu.uniquindio.icaja.view.views.normal;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;

public class BalanceUsuarioView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXListView<?> lvListaPresupuestosEstadisticas;

    @FXML
    private MFXListView<?> lvListaTransaccionesBalance;

    @FXML
    private RadioButton rbGastosBalance;

    @FXML
    private RadioButton rbIngresosBalance;

    @FXML
    private RadioButton rbTodosBalance;

    @FXML
    void ajustarPresupuestoBalanceAction(ActionEvent event) {

    }

    @FXML
    void generarReporteFinancieroBalanceAction(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
