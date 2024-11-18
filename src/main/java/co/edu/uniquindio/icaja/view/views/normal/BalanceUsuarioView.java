package co.edu.uniquindio.icaja.view.views.normal;

import co.edu.uniquindio.icaja.controller.CategoriaController;
import co.edu.uniquindio.icaja.controller.PresupuestoController;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.mapping.dto.PresupuestoDto;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.Presupuesto;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class BalanceUsuarioView {
    PresupuestoController presupuestoController = new PresupuestoController();
    CategoriaController categoriaController = new CategoriaController();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXComboBox<Categoria> cbCategoriasBalance;

    @FXML
    private MFXListView<Presupuesto> lvListaPresupuestosEstadisticas;

    @FXML
    private MFXListView<Transaccion> lvListaTransaccionesBalance;

    @FXML
    private Pane panelBalanceUsuario1;
    //donde está el crud
    @FXML
    private Pane panelBalanceUsuario2;

    @FXML
    private RadioButton rbGastosBalance;

    @FXML
    private RadioButton rbIngresosBalance;

    @FXML
    private RadioButton rbTodosBalance;

    @FXML
    private TableColumn<Presupuesto, String> tcCategoriaBalance;

    @FXML
    private TableColumn<Presupuesto, String> tcIdBalance;

    @FXML
    private TableColumn<Presupuesto, String> tcMontoAsignadoBalance;

    @FXML
    private TableColumn<Presupuesto, String> tcMontoGastadoBalance;

    @FXML
    private TableColumn<Presupuesto, String> tcNombreBalance;

    @FXML
    private TableView<Presupuesto> tvListaBalances;

    @FXML
    private MFXTextField txtMontoBalance;

    @FXML
    private MFXTextField txtNombreBalance;

    @FXML
    void actualizarBalanceAction(ActionEvent event) {

    }

    @FXML
    void ajustarPresupuestoBalanceAction(ActionEvent event) {
        ViewTools.cambiarPantalla(panelBalanceUsuario2,0.225, panelBalanceUsuario1);

    }

    @FXML
    void crearBalanceAction(ActionEvent event) {
//        String monto = txtMontoBalance.getText();
//        String nombre = txtNombreBalance.getText();
//        String categoria = cbCategoriasBalance.getSelectedText();
////        String clave = txtClaveAdmin.getText();
////        String claveTransaccional = txtClaveTransaccionalAdmin.getText();
////        String telefono = txtTelefonoAdmin.getText();
//
//
//        if (ViewTools.NoHayCamposVacios(monto, nombre, categoria)) {
//            PresupuestoDto presupuestoDto = new PresupuestoDto("10334",nombre,  monto,  23333453.4344,  categoria);
//
//            try {
//                presupuestoController.crear(presupuestoDto);
//                String msj = "Se ha creado el Presupuesto " + nombre + "correctamente";
//                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
//            } catch (ElementoYaExiste e) {
//                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
//            }
//        } else {
//            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);
//
//        }
//
//        ViewTools.limpiarCampos(txtMontoBalance,
//                txtNombreBalance
//                );
    }

    @FXML
    void eliminarBalanceAction(ActionEvent event) {

    }

    @FXML
    void generarReporteFinancieroBalanceAction(ActionEvent event) {

    }

    @FXML
    void limpiarCamposAction(ActionEvent event) {
        ViewTools.limpiarCampos(txtMontoBalance,
                txtNombreBalance
               );
    }

    @FXML
    void volverAction(ActionEvent event) {
        ViewTools.cambiarPantalla(panelBalanceUsuario1,0.225, panelBalanceUsuario2);

    }

    @FXML
    void initialize() {
        initview();
    }

    private void initview(){
        initDataBinding();
        tvListaBalances.getItems().clear();
        //tvListaBalances.setItems(presupuestoController.getListaPresupuestoObservable());
        cbCategoriasBalance.setItems(categoriaController.getListaCategoriasObservable());
        listenerSelectionUsuario();
    }

    private void initDataBinding(){
        tcNombreBalance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcIdBalance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdPresupuesto()));
       // tcMontoAsignadoBalance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMontoAsignado().doubleValue()));
       // tcMontoGastadoBalance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMontoGastado().doubleValue()));
       // tcCategoriaBalance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategorias()));

    }
    private void listenerSelectionUsuario() {
        tvListaBalances.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> this.mostrarInformacion(newSelection));

    }

    private void mostrarInformacion(Presupuesto seleccionado) {
        if (seleccionado != null) {
            txtNombreBalance.setText(seleccionado.getNombre());
            txtMontoBalance.setText(String.valueOf(seleccionado.getMontoAsignado()));

//            txtCorreoAdmin.setText(seleccionado.getCorreo());
//            txtTelefonoAdmin.setText(seleccionado.getTelefono());
//            txtClaveTransaccionalAdmin.setPromptText(seleccionado.getClaveTransaccional());
//            txtClaveAdmin.setPromptText(seleccionado.getClave());
        }
    }



}
