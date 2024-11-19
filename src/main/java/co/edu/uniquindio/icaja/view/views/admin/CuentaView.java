package co.edu.uniquindio.icaja.view.views.admin;

import co.edu.uniquindio.icaja.controller.CuentaController;
import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.exception.transacciones.MontoInvalido;
import co.edu.uniquindio.icaja.mapping.dto.CuentaDto;
import co.edu.uniquindio.icaja.model.Cuenta;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.enums.EntidadBancaria;
import co.edu.uniquindio.icaja.model.enums.TipoCuenta;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.tools.NumTool;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;

import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CuentaView {
    CuentaController cuentaController = new CuentaController();
    UsuarioController usuarioController = new UsuarioController();
    String idCuenta = "";

    @FXML
    private ComboBox<EntidadBancaria> cbxEntidadAdmin;

    @FXML
    private MFXFilterComboBox<String> cbxPropietarioCuentaAdmin;

    @FXML
    private ComboBox<TipoCuenta> cbxTipoCuentaAdmin;

    @FXML
    private TableColumn<Cuenta, EntidadBancaria> tcEntidadAdmin;

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
    private TextField txtNumeroCuentaAdmin;

    @FXML
    private TextField txtSaldoAdmin;

    @FXML
    void actualizarCuentaAction() {
        EntidadBancaria entidad = cbxEntidadAdmin.getValue();
        String numeroCuenta = txtNumeroCuentaAdmin.getText().replaceAll("-", "");
        TipoCuenta tipo = cbxTipoCuentaAdmin.getValue();
        String saldo = txtSaldoAdmin.getText().replaceAll("[^0-9]", "");
        String cedulaPropietario = cbxPropietarioCuentaAdmin.getValue();


        if (ViewTools.NoHayCamposVacios(numeroCuenta, saldo)) {
            Usuario propietario = usuarioController.consultar(cedulaPropietario, TipoConsulta.CEDULA);
            CuentaDto cuentaDto = new CuentaDto(idCuenta, entidad, numeroCuenta, tipo, saldo, propietario.getIdUsuario());

            try {
                cuentaController.actualizar(cuentaDto);
                String msj = "Se ha actualizado la cuenta " + numeroCuenta + " con la entidad " + entidad + " correctamente";
                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoYaExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }

        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }

        ViewTools.limpiarCampos(txtNumeroCuentaAdmin,
                txtSaldoAdmin);

    }

    @FXML
    void crearCuentaAction() {
        EntidadBancaria entidad = cbxEntidadAdmin.getValue();
        String numeroCuenta = txtNumeroCuentaAdmin.getText().replaceAll("-", "");
        TipoCuenta tipo = cbxTipoCuentaAdmin.getValue();
        String saldo = NumTool.formatearNumero(txtSaldoAdmin.getText());
        String cedulaPropietario = cbxPropietarioCuentaAdmin.getValue();


        if (ViewTools.NoHayCamposVacios(numeroCuenta, saldo)) {
            if (numeroCuenta.length() <= 10) {
                Usuario propietario = usuarioController.consultar(cedulaPropietario, TipoConsulta.CEDULA);
                CuentaDto cuentaDto = new CuentaDto(idCuenta, entidad, numeroCuenta, tipo, saldo, propietario.getIdUsuario());

                try {
                    cuentaController.crear(cuentaDto);
                    String msj = "Se ha creado la cuenta " + numeroCuenta + " con la entidad " + entidad + " correctamente";
                    ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);

                } catch (ElementoYaExiste | MontoInvalido e) {
                    ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);

                }
            } else {
                ViewTools.mostrarMensaje("Error", null, "Se ingreso un numero de cuenta invalido", Alert.AlertType.ERROR);
            }
        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);
        }

        ViewTools.limpiarCampos(txtNumeroCuentaAdmin,
                txtSaldoAdmin);

    }

    @FXML
    void eliminarCuentaAction() {

        String numeroCuenta = txtNumeroCuentaAdmin.getText().replaceAll("-", "");

        if (ViewTools.NoHayCamposVacios(numeroCuenta)) {
            try {
                cuentaController.eliminar(numeroCuenta);
                String msj = "Se ha eliminado la cuenta de numero " + numeroCuenta + " correctamente";
                ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoNoExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }

        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);
        }

        ViewTools.limpiarCampos(txtNumeroCuentaAdmin,
                txtSaldoAdmin);

    }

    @FXML
    void limpiarCamposCuentaAction() {
        ViewTools.limpiarCampos(txtNumeroCuentaAdmin, txtSaldoAdmin);
        cbxPropietarioCuentaAdmin.getSelectionModel().clearSelection();
        cbxTipoCuentaAdmin.getSelectionModel().clearSelection();
        cbxEntidadAdmin.getSelectionModel().clearSelection();
    }

    @FXML
    void initialize() {
        ViewTools.inicializarComboBox(cbxPropietarioCuentaAdmin, usuarioController.getListaUsuarioObservable(), Usuario::getCedula);
        cbxTipoCuentaAdmin.getItems().addAll(TipoCuenta.values());
        cbxEntidadAdmin.getItems().addAll(EntidadBancaria.values());
        initview();
        entradaNumeroCuenta();

        txtSaldoAdmin.textProperty().addListener((observable, oldValue, newValue) -> {
            txtSaldoAdmin.setText(NumTool.formatearMonto(newValue));
        });


    }

    private void initview() {
        initDataBinging();
        tvTablaCuentasAdmin.getItems().clear();
        tvTablaCuentasAdmin.setItems(cuentaController.getListaCuentaObservable());
        listenerSelectionCuenta();
    }

    private void initDataBinging() {
        tcEntidadAdmin.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEntidad()));
        tcNumeroCuentaAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroCuenta()));
        tcSaldoAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(NumTool.formatearMonto(cellData.getValue().getSaldo().toString())));
        tcTipoCuentaAdmin.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTipo()));
        tcPropietarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(getCedulaPropietario(cellData.getValue().getIdpropietario()))));
    }

    private String getCedulaPropietario(String idPropietario) {
        try {
            Seguimiento.registrarLog(1, "Consultando propietarios de cuenta bancaria");
            return usuarioController.consultar(idPropietario, TipoConsulta.ID_USUARIO).getCedula();

        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Ocurrio un error en la consulta de propietarios: " + e.getMessage());
        }
        return "Propietario No encontrado";
    }

    private void listenerSelectionCuenta() {
        tvTablaCuentasAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> this.mostrarInformacion(newSelection));
    }

    private void mostrarInformacion(Cuenta seleccionado) {
        if (seleccionado != null) {
            idCuenta = seleccionado.getIdCuenta();
            cbxEntidadAdmin.setValue(seleccionado.getEntidad());
            txtNumeroCuentaAdmin.setText(seleccionado.getNumeroCuenta());
            txtSaldoAdmin.setText(String.valueOf(seleccionado.getSaldo()));
            cbxTipoCuentaAdmin.setValue(seleccionado.getTipo());
            cbxPropietarioCuentaAdmin.setValue(getCedulaPropietario(seleccionado.getIdpropietario()));
        }
    }

    void entradaNumeroCuenta() {
        txtNumeroCuentaAdmin.textProperty().addListener((observable, oldValue, newValue) -> {
            // Elimina cualquier carácter no numérico
            String sinGuiones = newValue.replaceAll("\\D", "");

            // Limita el número máximo de caracteres a 10
            if (sinGuiones.length() > 10) {
                txtNumeroCuentaAdmin.setText(oldValue); // Revertimos al valor anterior
                return;
            }

            // Formatea según el patrón 4-3-3
            StringBuilder formateado = new StringBuilder();
            for (int i = 0; i < sinGuiones.length(); i++) {
                if (i == 4 || i == 7) {
                    formateado.append('-');
                }
                formateado.append(sinGuiones.charAt(i));
            }

            // Actualiza el campo con el valor formateado
            txtNumeroCuentaAdmin.setText(formateado.toString());
        });
    }

}
