package co.edu.uniquindio.icaja.view.views.admin;

import co.edu.uniquindio.icaja.controller.CuentaBancariaController;
import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.mapping.dto.CuentaBancariaDto;
import co.edu.uniquindio.icaja.model.Cuenta;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.enums.EntidadBancaria;
import co.edu.uniquindio.icaja.model.enums.TipoCuenta;
import co.edu.uniquindio.icaja.utils.tools.NumTool;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private TableColumn<Cuenta, EntidadBancaria> tcEntidadAdmin;

    @FXML
    private TableColumn<Cuenta, String> tcLimiteAdmin;

    @FXML
    private TableColumn<Cuenta, String> tcNumeroCuentaAdmin;

    @FXML
    private TableColumn<Cuenta, String> tcSaldoAdmin;

    @FXML
    private TableColumn<Cuenta, TipoCuenta> tcTipoCuentaAdmin;

    @FXML
    private TableView<Cuenta> tvTablaCuentasAdmin;
    @FXML
    private TableColumn<Cuenta, String> tcPropietarioAdmin;

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
            CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto(entidad, numeroCuenta, tipo, saldo, propietario);

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
            CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto(entidad, numeroCuenta, tipo, saldo, propietario);

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
        tvTablaCuentasAdmin.setItems(cuentaBancariaController.getListaCuentaObservable());
        listenerSelectionCuenta();
    }

    private void initDataBinging() {
        tcEntidadAdmin.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEntidad()));
        tcNumeroCuentaAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroCuenta()));
        tcSaldoAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSaldo().toString()));
        tcTipoCuentaAdmin.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTipo()));
        tcPropietarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPropietario().getCedula())));
    }

    private void listenerSelectionCuenta() {
        tvTablaCuentasAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> this.mostrarInformacion((Cuenta) newSelection));
    }

    private void mostrarInformacion(Cuenta seleccionado) {
        if (seleccionado != null) {
            cbxEntidadAdmin.setValue(seleccionado.getEntidad());
            txtNumeroCuentaAdmin.setText(seleccionado.getNumeroCuenta());
            txtSaldoAdmin.setText(String.valueOf(seleccionado.getSaldo()));
            cbxTipoCuentaAdmin.setValue(seleccionado.getTipo());
            cbxPropietarioCuentaAdmin.setValue(seleccionado.getPropietario().getCedula());
        }
    }

}
