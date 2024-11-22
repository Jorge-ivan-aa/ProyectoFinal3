package co.edu.uniquindio.icaja.view.views.normal;

import co.edu.uniquindio.icaja.controller.CategoriaController;
import co.edu.uniquindio.icaja.controller.PresupuestoController;
import co.edu.uniquindio.icaja.controller.TransaccionController;
import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.mapping.dto.PresupuestoDto;
import co.edu.uniquindio.icaja.model.*;
import co.edu.uniquindio.icaja.model.enums.EntidadBancaria;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.tools.NumTool;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class BalanceUsuarioView {
    PresupuestoController presupuestoController = new PresupuestoController();
    TransaccionController transaccionController = new TransaccionController();
    UsuarioController usuarioController = new UsuarioController();
    CategoriaController categoriaController = new CategoriaController();
    String balanceSeleccionado = "";
    Usuario usuarioLogueado = usuarioController.getFactory().getIcaja().getSesion().getUsuario();

    String csvPath = "reporte.csv";


    @FXML
    private MFXFilterComboBox<String> cbCategoriasBalance;

    @FXML
    private ListView<Presupuesto> lvListaPresupuestosEstadisticas;

    @FXML
    private ListView<Transaccion> lvListaTransaccionesBalance;

    @FXML
    private Pane panelBalanceUsuario1;
    //donde está el crud
    @FXML
    private Pane panelBalanceUsuario2;

    @FXML
    private Pane panelCrearCategoria;

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
    private MFXTextField txtDescripcionParaCategoria;

    @FXML
    private MFXTextField txtNombreParaCategoria;

    @FXML
    void actualizarBalanceAction() {
        String monto = txtMontoBalance.getText();
        String nombre = txtNombreBalance.getText();
        String[] categoria = new String[]{String.valueOf(cbCategoriasBalance.getValue())};

        if (ViewTools.NoHayCamposVacios(monto, nombre)) {
            PresupuestoDto presupuestoDto = new PresupuestoDto(null, nombre, monto, "", categoria);

            try {
                presupuestoController.actualizar(presupuestoDto);
                String msj = "Se ha actualizado el Presupuesto " + nombre + "correctamente";
                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoYaExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }

        ViewTools.limpiarCampos(txtMontoBalance,
                txtNombreBalance
        );
    }

    @FXML
    void ajustarPresupuestoBalanceAction() {
        ViewTools.cambiarPantalla(panelBalanceUsuario2, 0.225, panelBalanceUsuario1);

    }

    @FXML
    void crearBalanceAction() {
        String monto = txtMontoBalance.getText();
        String nombre = txtNombreBalance.getText();
        String[] categoria = new String[]{String.valueOf(cbCategoriasBalance.getValue())};


        if (ViewTools.NoHayCamposVacios(monto, nombre)) {
            PresupuestoDto presupuestoDto = new PresupuestoDto(null, nombre, monto, "", categoria);

            try {
                presupuestoController.crear(presupuestoDto);
                String msj = "Se ha creado el Presupuesto " + nombre + "correctamente";
                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoYaExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }

        ViewTools.limpiarCampos(txtMontoBalance,
                txtNombreBalance
        );
    }

    @FXML
    void eliminarBalanceAction() {
        try {
            presupuestoController.eliminar(balanceSeleccionado);
            String msj = "Se ha eliminado el balance correctamente";
            ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);
            limpiarCamposAction();
        } catch (ElementoNoExiste e) {
            ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
        }

    }


    @FXML
    void generarReporteFinancieroBalanceAction() {
        generateCSV(csvPath);
    }

    @FXML
    void limpiarCamposAction() {
        ViewTools.limpiarCampos(txtMontoBalance,
                txtNombreBalance
        );
    }

    @FXML
    void volverAction() {
        ViewTools.cambiarPantalla(panelBalanceUsuario1, 0.225, panelBalanceUsuario2);

    }

    @FXML
    void initialize() {
        ViewTools.inicializarComboBox(cbCategoriasBalance, obtenerCategoriasPorId(usuarioLogueado.getIdCategorias()), Categoria::getNombre);
        initview();
        llenarListaTransaccionesUsuario();
        llenarListaPresupuestosUsuario();
    }


    private <T> ObservableList<T> obtenerEntidadesPorIds(List<String> ids, Function<String, T> consulta) {
        ObservableList<T> entidades = FXCollections.observableArrayList();
        for (String id : ids) {
            T entidad = consultarPorId(id, consulta);
            if (entidad != null) {
                entidades.add(entidad);
            }
        }
        return entidades;
    }
    private ObservableList<Categoria> obtenerCategoriasPorId(List<String> idCategorias) {
        return obtenerEntidadesPorIds(idCategorias, id -> categoriaController.consultar(id, TipoConsulta.ID_CATEGORIA));
    }


    private void initview() {
        initDataBinding();
        tvListaBalances.getItems().clear();
        tvListaBalances.setItems(presupuestoController.getListaPresupuestoObservable());
        listenerSelectionUsuario();
    }

    private void initDataBinding() {
        tcNombreBalance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcIdBalance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdPresupuesto()));
        tcMontoAsignadoBalance.setCellValueFactory(cellData -> new SimpleStringProperty(NumTool.formatearMonto(cellData.getValue().getMontoAsignado())));
        tcMontoGastadoBalance.setCellValueFactory(cellData -> new SimpleStringProperty(NumTool.formatearMonto(cellData.getValue().getMontoGastado())));
        tcCategoriaBalance.setCellValueFactory(cellData -> new SimpleStringProperty(Arrays.toString(cellData.getValue().getIdCategorias())));

    }


    private void listenerSelectionUsuario() {
        tvListaBalances.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> this.mostrarInformacion(newSelection));

    }

    private void mostrarInformacion(Presupuesto seleccionado) {
        if (seleccionado != null) {
            balanceSeleccionado = seleccionado.getNombre();
            txtNombreBalance.setText(seleccionado.getNombre());
            txtMontoBalance.setText(String.valueOf(seleccionado.getMontoAsignado()));
        }
    }


    private <T> T consultarPorId(String id, Function<String, T> consulta) {
        try {
            return consulta.apply(id);
        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Error al consultar: " + e.getMessage());
            return null;
        }
    }


    public static void generatePDF() {

    }

    public static void generateCSV(String filePath) {
        UsuarioController usuarioController2 = new UsuarioController();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Encabezados
            writer.write("Cuenta,Número de Cuenta,Usuario,Categoría");
            writer.newLine();

            // Datos de ejemplo
            String[][] data = {
                    {"1", "123456789", "Juan Pérez", "Ahorro"},
                    {"2", "987654321", "Ana Gómez", "Corriente"},
                    {"3", usuarioController2.getListaUsuarioObservable().get(1).getIdUsuario(), usuarioController2.getListaUsuarioObservable().get(1).getNombre(), "corriente"},
                    {"4", usuarioController2.getListaUsuarioObservable().get(2).getIdUsuario(), usuarioController2.getListaUsuarioObservable().get(2).getNombre(), "Ahorro"}
            };

            for (String[] row : data) {
                writer.write(String.join(",", row));
                writer.newLine();
            }

            System.out.println("CSV generado en: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void llenarListaTransaccionesUsuario() {

    }


    private void llenarListaPresupuestosUsuario() {

    }


}
