package co.edu.uniquindio.icaja.view.views.admin;

import co.edu.uniquindio.icaja.controller.CuentaBancariaController;
import co.edu.uniquindio.icaja.controller.TransaccionController;
import co.edu.uniquindio.icaja.mapping.dto.TransaccionDto;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.utils.ViewTools;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class TransaccionView {
    TransaccionController transaccionController = new TransaccionController();
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
        String id = txtIdTransaccionAdmin.getText();
        String monto = txtMontoTransaccionAdmin.getText();
        String tipo = cbTipoTransaccionAdmin.getValue();
        String numCuenta = cbCuentaTransaccionAdmin.getSelectedText();
        String motivo = txtMotivoTransaccionAdmin.getText();

        if (ViewTools.NoHayCamposVacios(monto, motivo)) {


            try {
                CuentaBancaria cuentaBancaria = cuentaBancariaController.consultar(numCuenta);
                double montoREal = Double.parseDouble(monto);
                TransaccionDto transaccionPendiente = new TransaccionDto(montoREal, cuentaBancaria, motivo);
                transaccionController.getFactory().getIcaja().setTransaccionPendiente(transaccionPendiente);

                switch (tipo) {
                    case "transferencia":
                        ViewTools.generarVentana("templates/admin/tooltips/realizarTransferencia.fxml", "Gestion de transferencias", null, "styles/main.css");
                        break;
                    case "deposito":
                        ViewTools.generarVentana("templates/admin/tooltips/realizarDeposito.fxml", "Gestion de depositos", null, "styles/main.css");
                        break;
                    case "retiro":
                        ViewTools.generarVentana("templates/admin/tooltips/realizarRetiro.fxml", "Gestion de retiros", null, "styles/main.css");
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
                cbCuentaTransaccionAdmin,
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

    }

}
