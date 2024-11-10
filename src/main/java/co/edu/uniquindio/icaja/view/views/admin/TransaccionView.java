package co.edu.uniquindio.icaja.view.views.admin;

import co.edu.uniquindio.icaja.controller.CuentaController;
import co.edu.uniquindio.icaja.controller.TransaccionController;
import co.edu.uniquindio.icaja.mapping.dto.RetiroODepostoDto;
import co.edu.uniquindio.icaja.mapping.dto.TransferenciaDto;
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
    CuentaController cuentaController = new CuentaController();

    @FXML
    private MFXFilterComboBox<String> cbCuentaTransaccionAdmin;

    @FXML
    private ComboBox<String> cbTipoTransaccionAdmin;

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
        String tipo = cbTipoTransaccionAdmin.getValue();
        String motivo = txtMotivoTransaccionAdmin.getText();

        if (ViewTools.NoHayCamposVacios(monto, motivo)) {
            try {
                String numCuenta = cbCuentaTransaccionAdmin.getValue();
                Cuenta cuenta = cuentaController.consultar(numCuenta);

                switch (tipo) {
                    case "Transferencia":
                        transaccionController.setTransaccionPendiente(new TransferenciaDto(null, TipoTransaccion.TRANSFERENCIA, monto, motivo, cuenta, null));
                        ViewTools.generarVentana("templates/tooltips/realizarTransferenciaAdmin.fxml", "Gestion de transferencias", null, "styles/main.css");
                        Seguimiento.registrarLog(1, "Se quiere realizar una transferencia");
                        limpiar();
                        break;
                    case "Deposito":
                        transaccionController.setTransaccionPendiente(new RetiroODepostoDto(null, TipoTransaccion.DEPOSITO, monto, motivo, cuenta));
                        Seguimiento.registrarLog(1, "Se quiere realizar una transferencia");
                        break;
                    case "Retiro":
                        transaccionController.setTransaccionPendiente(new RetiroODepostoDto(null, TipoTransaccion.RETIRO, monto, motivo, cuenta));
                        Seguimiento.registrarLog(1, "Se quiere realizar una transferencia");
                        break;
                    default:
                        ViewTools.mostrarMensaje("¡Cuidado!", null,"No se seleccionó el tipo de transacción", Alert.AlertType.WARNING);
                }

            } catch (NumberFormatException e) {
                ViewTools.mostrarMensaje("Error", null, "El campo monto debe ser un valo númerico", Alert.AlertType.ERROR);
                Seguimiento.registrarLog(2, "No se ingresó un valor númerico en el monto: " + e.getMessage());

            } catch (Exception e) {
                ViewTools.mostrarMensaje("Error", null, "Ocurrió un error inesperado, comuniquese con atención tecnica.", Alert.AlertType.ERROR);
                Seguimiento.registrarLog(3, "Ocurrió un error inesperado: " + e.getMessage());
            }

        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);
        }
    }


    @FXML
    void LimpiarCamposTransaccionAction() {
        limpiar();
    }

    private void limpiar() {
        cbCuentaTransaccionAdmin.clearSelection();
        cbTipoTransaccionAdmin.getSelectionModel().clearSelection();

        ViewTools.limpiarCampos(txtIdTransaccionAdmin,
                txtMontoTransaccionAdmin,
                txtMotivoTransaccionAdmin);
    }

    @FXML
    void initialize() {
        initview();
        List<Cuenta> cuentas = cuentaController.getListaCuentaObservable();
        String[] numeroCuentas = new String[cuentas.size()];
        for (Cuenta cuenta : cuentas) {
            numeroCuentas[cuentas.indexOf(cuenta)] = cuenta.getNumeroCuenta();
        }

        cbCuentaTransaccionAdmin.getItems().addAll(numeroCuentas);
        cbTipoTransaccionAdmin.getItems().addAll("Transferencia", "Deposito", "Retiro");
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
            txtMontoTransaccionAdmin.setText(seleccionado.getMonto());
            txtMotivoTransaccionAdmin.setText(seleccionado.getMotivo());
            cbTipoTransaccionAdmin.setValue(seleccionado.getClass().getSimpleName());
            cbCuentaTransaccionAdmin.setValue(seleccionado.getCuentas()[0].getNumeroCuenta());
            txtIdTransaccionAdmin.setText(seleccionado.getIdTransaccion());
        }
    }

}
