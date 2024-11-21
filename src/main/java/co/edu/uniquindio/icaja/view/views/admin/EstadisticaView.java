package co.edu.uniquindio.icaja.view.views.admin;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.controller.CategoriaController;
import co.edu.uniquindio.icaja.controller.CuentaController;
import co.edu.uniquindio.icaja.controller.TransaccionController;
import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.Cuenta;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.enums.CategoriasComunes;
import co.edu.uniquindio.icaja.model.enums.TipoTransaccion;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class EstadisticaView {
    TransaccionController transaccionController= new TransaccionController();
    CategoriaController categoriaController = new CategoriaController();
    UsuarioController usuarioController = new UsuarioController();
    CuentaController cuentaController = new CuentaController();

    ObservableList<Transaccion> listaTransaccionesDeUsuario= FXCollections.observableArrayList();
    ObservableList<String> listaEstadisticasGastosCategoria= FXCollections.observableArrayList();



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
    private TableColumn<String, String> tcGastosPorCategoria;

    @FXML
    private TableColumn<String, String> tcPorcentajePorCategoria;

    @FXML
    private TableColumn<Transaccion, String> tcTransaccionesPorUsuario;

    @FXML
    private TableView<String> tvGastosPorCategoria;

    @FXML
    private TableView<Transaccion> tvTransaccionesPorUsuario;

    @FXML
    void initialize() {
        initview();

    }
    @FXML
    void initview() {
//        initDataBinding();
//        crearGraficos();
//        List<String> resultadoCategoriasGasto = estadisticasGastosCategoria(filtrarCategorias());
//        listaEstadisticasGastosCategoria.clear();
//        listaEstadisticasGastosCategoria.setAll(resultadoCategoriasGasto);
//        tvGastosPorCategoria.setItems(listaEstadisticasGastosCategoria);

    }
    @FXML
    void initDataBinding(){
//        tcGastosPorCategoria.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
//        tcPorcentajePorCategoria.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
    }

//
//    public void crearGraficos (){
//        //al piechart se le pone el nombre y el porcentaje de ocupación
//        //es una prueba
//        PieChart.Data Grafico=  new PieChart.Data("Cuenta",39 );
//        pcGraficaUno.setTitle("Grafica de cosas");
//        pcGraficaUno.getData().add(Grafico);
//
//    }
//
//    public  List<String> estadisticasGastosCategoria(List<String> categoriasUsuarios) {
//        return CategoriasComunes.calcularEstadisticas(categoriasUsuarios);
//    }
//
//
//    public List<Transaccion> filtrarGastos(){
//        listaTransaccionesDeUsuario = transaccionController.getListaTransaccionObservable();
//        List<Transaccion> listaRetiros= new ArrayList<>();
//        for(Transaccion transaccion: listaTransaccionesDeUsuario){
//            if(transaccion.getTipo().equals(TipoTransaccion.RETIRO)){
//                listaRetiros.add(transaccion);
//            } else if (transaccion.getTipo().equals(TipoTransaccion.TRANSFERENCIA)) {
//                Cuenta cuentaO = cuentaController.consultar(transaccion.getIdCuentas()[0], TipoConsulta.ID_CUENTA);
//                Cuenta cuentaD = cuentaController.consultar(transaccion.getIdCuentas()[1], TipoConsulta.ID_CUENTA);
//
//                if (!cuentaO.getIdpropietario().equals(cuentaD.getIdpropietario())) {
//                    listaRetiros.add(transaccion);
//                }
//            }
//        }
//
//        return listaRetiros;
//    }
//
//    public List<String> filtrarCategorias(){
//        List<Transaccion> listaTransacciones = filtrarGastos();
//
//        String[] listaCategorias = new String[listaTransacciones.size()];
//        for (int i = 0; i < listaTransacciones.size(); i++) {
//            Transaccion transaccion = listaTransaccionesDeUsuario.get(i);
//            try {
//                Categoria categoriaTransaccion = categoriaController.consultar(transaccion.getIdCategoria(), TipoConsulta.ID_CATEGORIA);
//                listaCategorias[i] = categoriaTransaccion.getNombre();
//
//            }catch (Exception e) {
//                Seguimiento.registrarLog(3, "Ocurrio un error en la filtración de categoria: " + e.getMessage());
//            }
//        }
//
//        return List.of(listaCategorias);
//    }

}
