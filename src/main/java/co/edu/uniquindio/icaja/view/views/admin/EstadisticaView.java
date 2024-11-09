package co.edu.uniquindio.icaja.view.views.admin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class EstadisticaView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane estadisticasPanel;

    @FXML
    private Label lbSaldoPromedioUsuario;

    @FXML
    private Label lbUsuarioMayorSaldo;

    @FXML
    private PieChart pcGraficaDos;

    @FXML
    private PieChart pcGraficaUno;

    @FXML
    private TableColumn<?, ?> tcCantidadTransacciones;

    @FXML
    private TableColumn<?, ?> tcGastosPorCategoria;

    @FXML
    private TableColumn<?, ?> tcPorcentajePorCategoria;

    @FXML
    private TableColumn<?, ?> tcTransaccionesPorUsuario;

    @FXML
    private TableView<?> tvGastosPorCategoria;

    @FXML
    private TableView<?> tvTransaccionesPorUsuario;

    @FXML
    void initialize() {

    }

}
