package co.edu.uniquindio.icaja.view.views.admin;

import co.edu.uniquindio.icaja.controller.CategoriaController;
import co.edu.uniquindio.icaja.controller.CuentaController;
import co.edu.uniquindio.icaja.controller.TransaccionController;
import co.edu.uniquindio.icaja.exception.transacciones.MontoInvalidoException;
import co.edu.uniquindio.icaja.exception.transacciones.SaldoInsuficiente;
import co.edu.uniquindio.icaja.mapping.dto.RetiroODepostoDto;
import co.edu.uniquindio.icaja.mapping.dto.TransferenciaDto;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.Cuenta;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.enums.TipoTransaccion;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class TransaccionView {
    TransaccionController transaccionController = TransaccionController.getInstance();
    CategoriaController categoriaController = new CategoriaController();
    CuentaController cuentaController = new CuentaController();

    @FXML
    private MFXFilterComboBox<String> cbxCategoriaTransaccionAdmin;

    @FXML
    private MFXFilterComboBox<String> cbxCuentaDestinoTransaccionAdmin;

    @FXML
    private MFXFilterComboBox<String> cbxCuentaTransaccionAdmin;

    @FXML
    private Label lbCuentaDestino;

    @FXML
    private ComboBox<TipoTransaccion> cbTipoTransaccionAdmin;

    @FXML
    private TableColumn<Transaccion, String> tcCategoriaTransaccionAdmin;

    @FXML
    private TableColumn<Transaccion, String> tcFechaTransaccionAdmin;

    @FXML
    private TableColumn<Transaccion, String> tcIdTransaccionAdmin;

    @FXML
    private TableColumn<Transaccion, String> tcMontoTransaccionAdmin;

    @FXML
    private TableColumn<Transaccion, String> tcMotivoTransaccionAdmin;

    @FXML
    private TableView<Transaccion> tvTablaTransaccionaAdmin;

    @FXML
    private TextField txtIdTransaccionAdmin;

    @FXML
    private TextField txtMontoTransaccionAdmin;

    @FXML
    private TextField txtMotivoTransaccionAdmin;

    @FXML
    void consultarTransaccionAction() {

    }


    @FXML
    void crearTransaccionAction() {
        String monto = txtMontoTransaccionAdmin.getText();
        String motivo = txtMotivoTransaccionAdmin.getText();

        if (ViewTools.NoHayCamposVacios(monto, motivo)) {
            boolean condicion = cbTipoTransaccionAdmin.getValue() != null &&
                    cbxCuentaTransaccionAdmin.getValue() != null &&
                    cbxCategoriaTransaccionAdmin.getValue() != null &&
                    cbxCuentaDestinoTransaccionAdmin.getValue() != null;

            if (condicion) {
                TipoTransaccion tipo = cbTipoTransaccionAdmin.getValue();
                Cuenta cuentaOrigen = cuentaController.consultar(cbxCuentaTransaccionAdmin.getValue());
                Categoria categoria = categoriaController.consultar(cbxCategoriaTransaccionAdmin.getValue());

                switch (tipo) {
                    case TRANSFERENCIA:
                        try {
                            Cuenta cuentaDestino = cuentaController.consultar(cbxCuentaDestinoTransaccionAdmin.getValue());
                            TransferenciaDto transferenciaDto = new TransferenciaDto(null, tipo, monto, motivo, cuentaOrigen, cuentaDestino, categoria);
                            transaccionController.crear(transferenciaDto);
                        } catch (MontoInvalidoException | SaldoInsuficiente e) {
                            ViewTools.mostrarMensaje("¡Error!", null, e.getMessage(), Alert.AlertType.ERROR);
                            Seguimiento.registrarLog(2, e.getMessage());
                        }

                        break;
                    case RETIRO:
                        break;
                    case DEPOSITO:
                        break;
                    default:

                }

            } else {
                ViewTools.mostrarMensaje("¡Cuidado!", null, "No se han seleccionado el tipo, las cuentas o la categoria de la trasacción.", Alert.AlertType.WARNING);
            }

        } else {
            ViewTools.mostrarMensaje("¡Cuidado!", null, "Hay campos vacios", Alert.AlertType.WARNING);
        }
    }


    @FXML
    void LimpiarCamposTransaccionAction() {
        limpiar();
    }

    private void limpiar() {
        cbxCuentaTransaccionAdmin.clearSelection();
        cbxCategoriaTransaccionAdmin.clearSelection();
        cbxCuentaDestinoTransaccionAdmin.clearSelection();
        cbTipoTransaccionAdmin.getSelectionModel().clearSelection();

        ViewTools.limpiarCampos(
                txtIdTransaccionAdmin,
                txtMontoTransaccionAdmin,
                txtMotivoTransaccionAdmin);
    }

    @FXML
    void initialize() {
        initview();
        ViewTools.actualizarComboBox(cbxCategoriaTransaccionAdmin, categoriaController.getListaCategoriasObservable(), Categoria::getNombre);
        ViewTools.actualizarComboBox(cbxCuentaDestinoTransaccionAdmin, cuentaController.getListaCuentaObservable(), Cuenta::getNumeroCuenta);
        ViewTools.actualizarComboBox(cbxCuentaTransaccionAdmin, cuentaController.getListaCuentaObservable(), Cuenta::getNumeroCuenta);
        cbTipoTransaccionAdmin.getItems().addAll(TipoTransaccion.values());
    }

    private void initview() {
        initDataBinging();
        tvTablaTransaccionaAdmin.getItems().clear();
        tvTablaTransaccionaAdmin.setItems(transaccionController.getListaTransaccionObservable());
        listenerSelectionCategorias();
    }

    private void initDataBinging() {
        tcCategoriaTransaccionAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getListacategoriatoString()));
        tcFechaTransaccionAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        tcIdTransaccionAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdTransaccion()));
        tcMontoTransaccionAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMonto()));
        tcMotivoTransaccionAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMotivo()));
    }

    private void listenerSelectionCategorias() {

        tvTablaTransaccionaAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> this.mostrarInformacion(newSelection));
    }

    private void mostrarInformacion(Transaccion seleccionado) {
        if (seleccionado != null) {

        }
    }

}
