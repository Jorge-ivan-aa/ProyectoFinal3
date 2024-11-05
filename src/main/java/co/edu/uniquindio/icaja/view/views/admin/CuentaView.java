package co.edu.uniquindio.icaja.view.views.admin;

import co.edu.uniquindio.icaja.controller.CuentaBancariaController;
import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.mapping.dto.CuentaBancariaDto;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.enums.EntidadBancaria;
import co.edu.uniquindio.icaja.model.enums.TipoCuenta;
import co.edu.uniquindio.icaja.utils.ViewTools;
import io.github.palexdev.materialfx.controls.MFXComboBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class CuentaView {
    CuentaBancariaController cuentaBancariaController = new CuentaBancariaController();

    UsuarioController usuarioController = new UsuarioController();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<EntidadBancaria> cbxEntidadAdmin;

    @FXML
    private ComboBox<String> cbxPropietarioCuentaAdmin;

    @FXML
    private ComboBox<TipoCuenta> cbxTipoCuentaAdmin;

    @FXML
    private AnchorPane panelCuenta;

    @FXML
    private TableColumn<CuentaBancaria, EntidadBancaria> tcEntidadAdmin;

    @FXML
    private TableColumn<CuentaBancaria, String> tcLimiteAdmin;

    @FXML
    private TableColumn<CuentaBancaria, String> tcNumeroCuentaAdmin;

    @FXML
    private TableColumn<CuentaBancaria, String> tcSaldoAdmin;

    @FXML
    private TableColumn<CuentaBancaria, TipoCuenta> tcTipoCuentaAdmin;

    @FXML
    private TableView<CuentaBancaria> tvTablaCuentasAdmin;
    @FXML
    private TableColumn<CuentaBancaria, String> tcPropietarioAdmin;

    @FXML
    private TextField txtLimiteAdmin;

    @FXML
    private TextField txtNumeroCuentaAdmin;

    @FXML
    private TextField txtSaldoAdmin;

    @FXML
    void actualizarCuentaAction() {
        EntidadBancaria entidad = cbxEntidadAdmin.getValue();
        String numeroCuenta = txtNumeroCuentaAdmin.getText();
        TipoCuenta tipo = cbxTipoCuentaAdmin.getValue();
        String saldo = txtSaldoAdmin.getText();
        String limite = txtLimiteAdmin.getText();
        String cedulaPropietario = cbxPropietarioCuentaAdmin.getValue();


        if (ViewTools.NoHayCamposVacios(numeroCuenta, saldo, limite)) {
            Usuario propietario = usuarioController.consultar(cedulaPropietario);
            CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto(entidad, numeroCuenta, tipo, Double.parseDouble(saldo), Double.parseDouble(limite), propietario);

            try {
                cuentaBancariaController.actualizar(cuentaBancariaDto);
                String msj = "Se ha actualizado la cuenta " + numeroCuenta + " con la entidad " + entidad + " correctamente";
                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoYaExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }

        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }

        ViewTools.limpiarCampos(txtNumeroCuentaAdmin,
                txtSaldoAdmin,
                txtLimiteAdmin);

    }

    @FXML
    void crearCuentaAction() {
        EntidadBancaria entidad = cbxEntidadAdmin.getValue();
        String numeroCuenta = txtNumeroCuentaAdmin.getText();
        TipoCuenta tipo = cbxTipoCuentaAdmin.getValue();
        String saldo = txtSaldoAdmin.getText();
        String limite = txtLimiteAdmin.getText();
        String cedulaPropietario = cbxPropietarioCuentaAdmin.getValue();


        if (ViewTools.NoHayCamposVacios(numeroCuenta, saldo, limite)) {
            Usuario propietario = usuarioController.consultar(cedulaPropietario);
            CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto(entidad, numeroCuenta, tipo, Double.parseDouble(saldo), Double.parseDouble(limite), propietario);

            try {
                cuentaBancariaController.crear(cuentaBancariaDto);
                String msj = "Se ha creado la cuenta " + numeroCuenta + " con la entidad " + entidad + " correctamente";
                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoYaExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }

        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }

        ViewTools.limpiarCampos(txtNumeroCuentaAdmin,
                txtSaldoAdmin,
                txtLimiteAdmin);

    }

    @FXML
    void eliminarCuentaAction() {

        String numeroCuenta = txtNumeroCuentaAdmin.getText();

        if (ViewTools.NoHayCamposVacios(numeroCuenta)) {
            try {
                cuentaBancariaController.eliminar(numeroCuenta);
                String msj = "Se ha eliminado la cuenta de numero " + numeroCuenta + " correctamente";
                ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoNoExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }

        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);
        }

        ViewTools.limpiarCampos(txtNumeroCuentaAdmin,
                txtSaldoAdmin,
                txtLimiteAdmin);

    }

    @FXML
    void limpiarCamposCuentaAction() {
        ViewTools.limpiarCampos(txtNumeroCuentaAdmin,
                txtSaldoAdmin,
                txtLimiteAdmin);

        cbxPropietarioCuentaAdmin.getSelectionModel().clearSelection();
        cbxTipoCuentaAdmin.getSelectionModel().clearSelection();
        cbxEntidadAdmin.getSelectionModel().clearSelection();
    }

    @FXML
    void initialize() {
        List<Usuario> usuarios = usuarioController.getListaUsuarioObservable();
        String[] cedulas = new String[usuarios.size()];
        for (Usuario usuario : usuarios) {
            cedulas[usuarios.indexOf(usuario)] = usuario.getCedula();
        }
        cbxPropietarioCuentaAdmin.getItems().addAll(cedulas);
        cbxTipoCuentaAdmin.getItems().addAll(TipoCuenta.values());
        cbxEntidadAdmin.getItems().addAll(EntidadBancaria.values());
        initview();
    }

    private void initview() {
        initDataBinging();
        tvTablaCuentasAdmin.getItems().clear();
        tvTablaCuentasAdmin.setItems(cuentaBancariaController.getListaCuentaBancariaObservable());
        listenerSelectionCuenta();
    }

    private void initDataBinging() {
        tcEntidadAdmin.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEntidad()));
        tcNumeroCuentaAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroCuenta()));
        tcSaldoAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getSaldo())));
        tcTipoCuentaAdmin.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTipoCuenta()));
        tcLimiteAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getLimite())));
        tcPropietarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPropietario().getCedula())));

    }

    private void listenerSelectionCuenta() {
        tvTablaCuentasAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> this.mostrarInformacion((CuentaBancaria) newSelection));
    }

    private void mostrarInformacion(CuentaBancaria seleccionado) {
        if (seleccionado != null) {
            cbxEntidadAdmin.setValue(seleccionado.getEntidad());
            txtNumeroCuentaAdmin.setText(seleccionado.getNumeroCuenta());
            txtSaldoAdmin.setText(String.valueOf(seleccionado.getSaldo()));
            cbxTipoCuentaAdmin.setValue(seleccionado.getTipoCuenta());
            txtLimiteAdmin.setText(String.valueOf(seleccionado.getLimite()));
            cbxPropietarioCuentaAdmin.setValue(seleccionado.getPropietario().getCedula());
        }
    }

}
