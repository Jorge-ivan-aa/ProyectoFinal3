package co.edu.uniquindio.icaja.view.views.admin;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.controller.CategoriaController;
import co.edu.uniquindio.icaja.controller.TransaccionController;
import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.Usuario;
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
    ObservableList<Transaccion> listaTransaccionesDeUsuario= FXCollections.observableArrayList();
    ObservableList<Usuario> listaDeUsuarios= FXCollections.observableArrayList();
    double promedioSaldos= 0;
    double sumaSaldos =0 ;


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
        initview();

    }
    @FXML
    void initview() {
        initDataBinding();
        crearGraficos();
        filtrarRetiros();
        filtrarSaldos();
        tvGastosPorCategoria.setItems(filtrarCategorias());
        //tvGastosPorCategoria.setItems(categoriaController.getListaCategoriasObservable());
    }
    @FXML
    void initDataBinding(){
        tcGastosPorCategoria.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNombre()));
        tcPorcentajePorCategoria.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdCategoria()));
    }


    public void crearGraficos (){
        //al piechart se le pone el nombre y el porcentaje de ocupación
        //es una prueba
        PieChart.Data Grafico=  new PieChart.Data("Cuenta",39);
        pcGraficaUno.setTitle("Grafica de cosas");
        pcGraficaUno.getData().add(Grafico);

    }


    public List<Transaccion> filtrarRetiros(){
        listaTransaccionesDeUsuario = transaccionController.getListaTransaccionObservable();
        List<Transaccion> listaRetiros= new ArrayList<>();
        for (int i = 0; i < listaTransaccionesDeUsuario.size(); i++) {
            Transaccion transaccion = listaTransaccionesDeUsuario.get(i);
            if (transaccion.getTipo().equals("RETIRO")){
                listaRetiros.add(transaccion);
                //tcGastosPorCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));
                //poner o hacer que mande la lista de los retiros
            }

        }
        return listaRetiros;

    }

    public ObservableList<Categoria> filtrarCategorias(){
        List<Transaccion> listaRetiros2= filtrarRetiros();
        List<Categoria> listaCategorias = new ArrayList<>();
        for (int i = 0; i < listaRetiros2.size(); i++) {
            //Sacar los Id categorias para hacer la busqueda por el Id y almacenarlos en la lista
            Transaccion tipo = listaTransaccionesDeUsuario.get(i);

            Categoria tipoCategoria= categoriaController.consultar(tipo.getIdCategoria(), TipoConsulta.ID_CATEGORIA);
            listaCategorias.add(tipoCategoria);
        }
        ObservableList<Categoria> observableCategoriaList = FXCollections.observableArrayList(listaCategorias);

        return observableCategoriaList;
    }

    public void filtrarSaldos(){
        listaDeUsuarios = usuarioController.getListaUsuarioObservable();
        List<Double> listaDeSaldos = new ArrayList<>();
        List<Transaccion> listaDetransacciones= new ArrayList<>();
        double saldoMayor = 0;
        String nombreMayor="";
        for (int i = 0; i < listaDeUsuarios.size()-1; i++) {
            Usuario usuario = listaDeUsuarios.get(i);
            //Añadir transacciones de los usuarios a la lista

            Usuario usuario2 = listaDeUsuarios.get(i+1);
            sumaSaldos = usuario.getSaldoTotal().doubleValue();
            promedioSaldos= (sumaSaldos/listaDeUsuarios.size());

            //Validar cual es el usuario con el mayor saldo de la app
            double saldo = usuario.getSaldoTotal().doubleValue();
            if(saldo>usuario2.getSaldoTotal().doubleValue()){
                saldoMayor = saldo;
                nombreMayor=usuario.getNombre();
            }else{
                saldoMayor= usuario2.getSaldoTotal().doubleValue();
                nombreMayor= usuario2.getNombre();
            }
            listaDeSaldos.add(saldo);

        }

        lbSaldoPromedioUsuario.setText(String.valueOf(promedioSaldos));
        lbUsuarioMayorSaldoNombre.setText(nombreMayor);
        lbUsuarioMayorSaldoSaldo.setText(String.valueOf(saldoMayor));


    }




}
