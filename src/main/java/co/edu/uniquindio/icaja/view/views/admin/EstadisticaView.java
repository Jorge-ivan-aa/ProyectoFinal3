package co.edu.uniquindio.icaja.view.views.admin;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.controller.TransaccionController;
import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.Transaccion;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class EstadisticaView {
    TransaccionController transaccionController= new TransaccionController();
    UsuarioController usuarioController = new UsuarioController();
    ObservableList<Transaccion> listaTransaccionesUsuario= transaccionController.getListaTransaccionObservable();

    List<Transaccion> listaRetiros= new ArrayList<>();
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
    private Label lbUsuarioMayorSaldoNombre;

    @FXML
    private Label lbUsuarioMayorSaldoSaldo;

    @FXML
    private PieChart pcGraficaDos;

    @FXML
    private PieChart pcGraficaUno;

    @FXML
    private TableColumn<Transaccion, String> tcCantidadTransacciones;

    @FXML
    private TableColumn<Categoria, String> tcGastosPorCategoria;

    @FXML
    private TableColumn<Categoria, String> tcPorcentajePorCategoria;

    @FXML
    private TableColumn<Transaccion, String> tcTransaccionesPorUsuario;

    @FXML
    private TableView<Categoria> tvGastosPorCategoria;

    @FXML
    private TableView<Transaccion> tvTransaccionesPorUsuario;

    @FXML
    void initialize() {
        crearGraficos();

    }
    public void crearGraficos (){
        //al piechart se le pone el nombre y el porcentaje de ocupación
        PieChart.Data Grafico=  new PieChart.Data("Cuenta",39);
        pcGraficaUno.setTitle("Grafica de cosas");
        pcGraficaUno.getData().add(Grafico);

    }



}
