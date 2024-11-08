package co.edu.uniquindio.icaja.view.views.admin;

import co.edu.uniquindio.icaja.controller.CuentaBancariaController;
import co.edu.uniquindio.icaja.controller.TransaccionController;
import co.edu.uniquindio.icaja.mapping.dto.TransaccionDto;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.utils.ViewTools;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.util.List;

public class TransaccionView {
    TransaccionController transaccionController = TransaccionController.getInstance();
    CuentaBancariaController cuentaBancariaController = new CuentaBancariaController();

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
    private AnchorPane transaccionPanel;

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
                CuentaBancaria cuentaBancaria = cuentaBancariaController.consultar(numCuenta);
                double montoREal = Double.parseDouble(monto);
                transaccionController.setTransaccionPendiente(new TransaccionDto(montoREal, cuentaBancaria, motivo));

                switch (tipo) {
                    case "Transferencia":
                        Seguimiento.registrarLog(1, "Se quiere realizar una transferencia");
                        ViewTools.generarVentana("templates/tooltips/realizarTransferenciaAdmin.fxml", "Gestion de transferencias", null, "styles/main.css");
                        break;
                    case "Deposito":
                        ViewTools.generarVentana("templates/admin/tooltips/realizarDepositoAdmin.fxml", "Gestion de depositos", null, "styles/main.css");
                        break;
                    case "Retiro":
                        ViewTools.generarVentana("templates/admin/tooltips/realizarRetiroAdmin.fxml", "Gestion de retiros", null, "styles/main.css");
                        break;
                    default:
                        ViewTools.mostrarMensaje("¡Cuidado!", null,"No se seleccionó el tipo de transacción", Alert.AlertType.WARNING);
                }

            } catch (NumberFormatException e) {
                Seguimiento.registrarLog(2, "No se ingresó un valor númerico en el monto: " + e.getMessage());
                ViewTools.mostrarMensaje("Error", null, "El campo monto debe ser un valo númerico", Alert.AlertType.ERROR);

            } catch (Exception e) {
                Seguimiento.registrarLog(3, "Ocurrió un error inesperado: " + e.getMessage());
                ViewTools.mostrarMensaje("Error", null, "Ocurrió un error inesperado, comuniquese con atención tecnica.", Alert.AlertType.ERROR);
            }

        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }

        ViewTools.limpiarCampos(txtIdTransaccionAdmin,
                txtMontoTransaccionAdmin,
                txtMotivoTransaccionAdmin);
    }

    @FXML
    void eliminarTransaccionAction() {

    }

    @FXML
    void LimpiarCamposTransaccionAction() {
        ViewTools.limpiarCampos(txtIdTransaccionAdmin,
                txtMontoTransaccionAdmin,
                cbCuentaTransaccionAdmin,
                txtMotivoTransaccionAdmin);
    }

    @FXML
    void initialize() {
        initview();
        List<CuentaBancaria> cuentas = cuentaBancariaController.getListaCuentaBancariaObservable();
        String[] numeroCuentas = new String[cuentas.size()];
        for (CuentaBancaria cuenta : cuentas) {
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
        tcIdTransaccionAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        tcMontoTransaccionAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getMonto())));
        tcMotivoTransaccionAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMotivo()));
    }

    private void listenerSelectionCategorias() {

        tvTablaTransaccionaAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> this.mostrarInformacion((Transaccion) newSelection));
    }

    private void mostrarInformacion(Transaccion seleccionado) {
        if (seleccionado != null) {
            txtIdTransaccionAdmin.setText(seleccionado.getId());
            txtMontoTransaccionAdmin.setText(Double.toString(seleccionado.getMonto()));
            txtMotivoTransaccionAdmin.setText(seleccionado.getMotivo());
            cbTipoTransaccionAdmin.setValue(seleccionado.getClass().getName());
            cbCuentaTransaccionAdmin.setValue(seleccionado.getCuenta().getNumeroCuenta());
        }
    }

}
