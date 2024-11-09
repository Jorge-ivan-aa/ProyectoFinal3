package co.edu.uniquindio.icaja.view.views.admin;

import co.edu.uniquindio.icaja.controller.CuentaBancariaController;
import co.edu.uniquindio.icaja.controller.TransaccionController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.mapping.dto.TransaccionDto;
import co.edu.uniquindio.icaja.model.Cuenta;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
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
//        String monto = txtMontoTransaccionAdmin.getText();
//        String tipo = cbTipoTransaccionAdmin.getValue();
//        String motivo = txtMotivoTransaccionAdmin.getText();
//
//        if (ViewTools.NoHayCamposVacios(monto, motivo)) {
//            try {
//                String numCuenta = cbCuentaTransaccionAdmin.getValue();
//                Cuenta cuenta = cuentaBancariaController.consultar(numCuenta);
//                double montoREal = Double.parseDouble(monto);
//                transaccionController.setTransaccionPendiente(new TransaccionDto(montoREal, cuenta, motivo));
//
//                switch (tipo) {
//                    case "Transferencia":
//                        ViewTools.generarVentana("templates/tooltips/realizarTransferenciaAdmin.fxml", "Gestion de transferencias", null, "styles/main.css");
//                        Seguimiento.registrarLog(1, "Se quiere realizar una transferencia");
//                        limpiar();
//                        break;
//                    case "Deposito":
//                        ViewTools.mostrarMensaje("¡Cuidado!", "No se puede realizar el movimiento", "Los administradores no pueden realizar depositos", Alert.AlertType.WARNING);
//                        Seguimiento.registrarLog(2, "Los administradores no pueden realizar depositos");
//                        break;
//                    case "Retiro":
//                        ViewTools.mostrarMensaje("¡Cuidado!", "No se puede realizar el movimiento", "Los administradores no pueden realizar retiros", Alert.AlertType.WARNING);
//                        Seguimiento.registrarLog(2, "Los administradores no pueden realizar retiros");
//                        break;
//                    default:
//                        ViewTools.mostrarMensaje("¡Cuidado!", null,"No se seleccionó el tipo de transacción", Alert.AlertType.WARNING);
//                }
//
//            } catch (NumberFormatException e) {
//                ViewTools.mostrarMensaje("Error", null, "El campo monto debe ser un valo númerico", Alert.AlertType.ERROR);
//                Seguimiento.registrarLog(2, "No se ingresó un valor númerico en el monto: " + e.getMessage());
//
//            } catch (Exception e) {
//                ViewTools.mostrarMensaje("Error", null, "Ocurrió un error inesperado, comuniquese con atención tecnica.", Alert.AlertType.ERROR);
//                Seguimiento.registrarLog(3, "Ocurrió un error inesperado: " + e.getMessage());
//            }
//
//        } else {
//            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);
//        }

    }


    @FXML
    void eliminarTransaccionAction() {
//
//        String id = txtIdTransaccionAdmin.getText();
//
//        if (ViewTools.NoHayCamposVacios(id)) {
//            try {
//                transaccionController.eliminar(id);
//                String msj = "Se ha eliminado la transacción " + id + " con exito.";
//                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
//                limpiar();
//            } catch (ElementoNoExiste e) {
//                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
//            }
//
//        } else {
//            ViewTools.mostrarMensaje("Error", null, "Hay campos vacios", Alert.AlertType.ERROR);
//        }
//        limpiar();
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
        List<Cuenta> cuentas = cuentaBancariaController.getListaCuentaObservable();
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
