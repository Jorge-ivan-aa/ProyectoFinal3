package co.edu.uniquindio.icaja.view.views.admin;

import co.edu.uniquindio.icaja.controller.CuentaBancariaController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.mapping.dto.CuentaBancariaDto;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.enums.TipoCuenta;
import co.edu.uniquindio.icaja.utils.ViewTools;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class CuentaView {
    CuentaBancariaController cuentaBancariaController = new CuentaBancariaController();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXComboBox<CuentaBancaria> cbPropietarioAdmin;

    @FXML
    private MFXComboBox<CuentaBancaria> cbTipoCuentaAdmin;

    @FXML
    private AnchorPane panelCuenta;

    @FXML
    private TableColumn<CuentaBancaria, String> tcEntidadAdmin;

    @FXML
    private TableColumn<CuentaBancaria, String> tcLimiteAdmin;

    @FXML
    private TableColumn<CuentaBancaria, String> tcNumeroCuentaAdmin;

    @FXML
    private TableColumn<CuentaBancaria, String> tcSaldoAdmin;

    @FXML
    private TableColumn<CuentaBancaria, String> tcTipoCuentaAdmin;

    @FXML
    private TableView<CuentaBancaria> tvTablaCuentasAdmin;
    @FXML
    private TableColumn<CuentaBancaria, String> tcPropietarioAdmin;

    @FXML
    private TextField txtEntidadAdmin;

    @FXML
    private TextField txtLimiteAdmin;

    @FXML
    private TextField txtNumeroCuentaAdmin;

    @FXML
    private TextField txtSaldoAdmin;

    @FXML
    void ActualizarCuentaAction(ActionEvent event) {
        String entidad = txtEntidadAdmin.getText();
        String numeroCuenta = txtNumeroCuentaAdmin.getText();
        String saldo = txtSaldoAdmin.getText();
        String tipoCuenta = String.valueOf(TipoCuenta.valueOf(cbTipoCuentaAdmin.getAccessibleText()));
        String limite = txtLimiteAdmin.getText();
        String propietario = cbPropietarioAdmin.getSelectedText();



        if (!ViewTools.hayCamposVacios(entidad,numeroCuenta,saldo,tipoCuenta,limite,propietario)) {
       //     CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto(entidad,numeroCuenta, TipoCuenta.valueOf(tipoCuenta),Double.parseDouble(saldo),Double.parseDouble(limite), propietario);

            try {
            //cuentaBancariaController.crearCuentaBancaria(cuentaBancariaDto);
                String msj = "Se ha creado la cuenta " + numeroCuenta + " con la entidad "+entidad+ " correctamente";
                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoYaExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }

        ViewTools.limpiarCampos(txtEntidadAdmin,
                txtNumeroCuentaAdmin,
                txtSaldoAdmin,
                txtLimiteAdmin
        );

    }

    @FXML
    void crearCuentaAction(ActionEvent event) {
        String entidad = txtEntidadAdmin.getText();
        String numeroCuenta = txtNumeroCuentaAdmin.getText();
        String saldo = txtSaldoAdmin.getText();
        String tipoCuenta = String.valueOf(TipoCuenta.valueOf(cbTipoCuentaAdmin.getAccessibleText()));
        String limite = txtLimiteAdmin.getText();
        String propietario = cbPropietarioAdmin.getSelectedText();



        if (!ViewTools.hayCamposVacios(entidad,numeroCuenta,saldo,tipoCuenta,limite,propietario)) {
            System.out.println("error aqui (cuentaview linea 118)");
           // CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto(entidad,numeroCuenta, TipoCuenta.valueOf(tipoCuenta),Double.parseDouble(saldo),Double.parseDouble(limite), propietario);

            try {
             //   cuentaBancariaController.crearCuentaBancaria(cuentaBancariaDto);
                String msj = "Se ha creado la cuenta " + numeroCuenta + " con la entidad "+entidad+ " correctamente";
                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoYaExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }

        ViewTools.limpiarCampos(txtEntidadAdmin,
                txtNumeroCuentaAdmin,
                txtSaldoAdmin,
                txtLimiteAdmin
                );

    }

    @FXML
    void eliminarCuentaAction(ActionEvent event) {
        String numeroCuenta   = txtNumeroCuentaAdmin.getText();

        if (!ViewTools.hayCamposVacios(numeroCuenta)) {
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

        ViewTools.limpiarCampos(txtEntidadAdmin,
                txtNumeroCuentaAdmin,
                txtSaldoAdmin,
                txtLimiteAdmin);

    }
    @FXML
    void LimpiarCamposCuentaAction(ActionEvent event) {
        ViewTools.limpiarCampos(txtEntidadAdmin,
                txtNumeroCuentaAdmin,
                txtSaldoAdmin,
                txtLimiteAdmin);
    }

    @FXML
    void seleccionPropietarioAction(ActionEvent event) {

    }

    @FXML
    void seleccionTipoCuentaAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        initview();
    }
    private void initview() {
        initDataBinging();
       tvTablaCuentasAdmin.getItems().clear();
        //tvTablaCuentasAdmin.setItems(CuentaBancariaController.getListaCuentaBancariaObservable());
        listenerSelectionCuenta();
    }

    private void initDataBinging() {
        tcEntidadAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntidad()));
        tcNumeroCuentaAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroCuenta()));
        //tcSaldoAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSaldo()));
        //tcTipoCuentaAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoCuenta()));
        //tcLimiteAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLimite()));
        tcPropietarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPropietario())));

    }

    private void listenerSelectionCuenta() {
        tvTablaCuentasAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> this.mostrarInformacion((CuentaBancaria) newSelection));
    }

    private void mostrarInformacion(CuentaBancaria seleccionado) {
        if (seleccionado != null) {
            txtEntidadAdmin.setText(seleccionado.getEntidad());
            txtNumeroCuentaAdmin.setText(seleccionado.getNumeroCuenta());
            txtSaldoAdmin.setText(String.valueOf(seleccionado.getSaldo()));
            //cbTipoCuentaAdmin.setItems(seleccionado.getTipoCuenta());
            txtLimiteAdmin.setText(String.valueOf(seleccionado.getLimite()));
            cbPropietarioAdmin.setText(String.valueOf(seleccionado.getPropietario()));

        }
    }

}
