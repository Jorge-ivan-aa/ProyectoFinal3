package co.edu.uniquindio.icaja.view.views.admin;

import co.edu.uniquindio.icaja.controller.CuentaController;
import co.edu.uniquindio.icaja.controller.TransaccionController;
import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.exception.transacciones.CuentaDuplicada;
import co.edu.uniquindio.icaja.exception.transacciones.MontoInvalido;
import co.edu.uniquindio.icaja.exception.transacciones.SaldoInsuficiente;
import co.edu.uniquindio.icaja.mapping.dto.RetiroODepostoDto;
import co.edu.uniquindio.icaja.mapping.dto.TransferenciaDto;
import co.edu.uniquindio.icaja.mapping.services.ITransaccionDto;
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

import java.util.Objects;


public class TransaccionView {
    TransaccionController transaccionController = new TransaccionController();
    CuentaController cuentaController = new CuentaController();

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
            boolean condicion = cbTipoTransaccionAdmin.getValue() != null && cbxCuentaTransaccionAdmin.getValue() != null;
            if (condicion) {
                TipoTransaccion tipo = cbTipoTransaccionAdmin.getValue();
                Cuenta cuentaOrigen = cuentaController.consultar(cbxCuentaTransaccionAdmin.getValue(), TipoConsulta.NUMERO_CUENTA);
                Categoria categoria = new Categoria("Movimiento realizado por el sistema", "Transferencia realizada por el administrador");
                ITransaccionDto transaccionDto;

                if (Objects.requireNonNull(tipo) == TipoTransaccion.TRANSFERENCIA) {
                    if (cbxCuentaDestinoTransaccionAdmin.getValue() != null) {
                        Cuenta cuentaDestino = cuentaController.consultar(cbxCuentaDestinoTransaccionAdmin.getValue(), TipoConsulta.NUMERO_CUENTA);
                        transaccionDto = new TransferenciaDto(null, tipo, monto, motivo, cuentaOrigen.getIdCuenta(), cuentaDestino.getIdCuenta(), categoria.getIdCategoria());
                    } else {
                        transaccionDto = null;
                    }
                } else {
                    transaccionDto = new RetiroODepostoDto(null, tipo, monto, motivo, cuentaOrigen.getIdCuenta(), categoria.getIdCategoria());
                }

                try {
                    assert transaccionDto != null;
                    transaccionController.crear(transaccionDto);

                } catch (MontoInvalido | SaldoInsuficiente | ElementoYaExiste | CuentaDuplicada e) {
                    ViewTools.mostrarMensaje("¡Error!", null, e.getMessage(), Alert.AlertType.ERROR);
                    Seguimiento.registrarLog(2, e.getMessage());

                } catch (NullPointerException e) {
                    ViewTools.mostrarMensaje("¡Error!", null, "No se ha selecciona la cuenta de destino de la transacción.", Alert.AlertType.ERROR);
                }

            } else {
                ViewTools.mostrarMensaje("¡Cuidado!", null, "No se han seleccionado el tipo o la cuenta en la transacción.", Alert.AlertType.WARNING);
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
        cbxCuentaDestinoTransaccionAdmin.clearSelection();
        cbTipoTransaccionAdmin.getSelectionModel().clearSelection();

        ViewTools.limpiarCampos(
                txtIdTransaccionAdmin,
                txtMontoTransaccionAdmin,
                txtMotivoTransaccionAdmin);
    }

    @FXML
    void initialize() {

        cbTipoTransaccionAdmin.valueProperty().addListener((observable, oldValue, seleccionado) -> {
            if (seleccionado.equals(TipoTransaccion.TRANSFERENCIA)) {
                lbCuentaDestino.setVisible(true);
                cbxCuentaDestinoTransaccionAdmin.setVisible(true);
            } else {
                cbxCuentaDestinoTransaccionAdmin.setVisible(false);
                lbCuentaDestino.setVisible(false);
            }

        });

        initview();
        ViewTools.inicializarComboBox(cbxCuentaDestinoTransaccionAdmin, cuentaController.getListaCuentaObservable(), Cuenta::getNumeroCuenta);
        ViewTools.inicializarComboBox(cbxCuentaTransaccionAdmin, cuentaController.getListaCuentaObservable(), Cuenta::getNumeroCuenta);
        cbTipoTransaccionAdmin.getItems().addAll(TipoTransaccion.values());
    }

    private void initview() {
        initDataBinging();
        tvTablaTransaccionaAdmin.getItems().clear();
        tvTablaTransaccionaAdmin.setItems(transaccionController.getListaTransaccionObservable());
        listenerSelectionCategorias();
    }

    private void initDataBinging() {
        tcIdTransaccionAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdTransaccion()));
        tcCategoriaTransaccionAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdCategoria()));
        tcFechaTransaccionAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        tcMontoTransaccionAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMonto()));
        tcMotivoTransaccionAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMotivo()));
    }

    private void listenerSelectionCategorias() {

        tvTablaTransaccionaAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> this.mostrarInformacion(newSelection));
    }

    private void mostrarInformacion(Transaccion seleccionado) {
        if (seleccionado != null) {
            txtIdTransaccionAdmin.setText(seleccionado.getIdTransaccion());
            txtMotivoTransaccionAdmin.setText(seleccionado.getMotivo());
            txtMontoTransaccionAdmin.setText(seleccionado.getMonto());
            cbTipoTransaccionAdmin.setValue(seleccionado.getTipo());
            cbxCuentaTransaccionAdmin.setValue(consultarNumeroCuenta(seleccionado.getIdCuentas()[0]));
            if (seleccionado.getTipo().equals(TipoTransaccion.TRANSFERENCIA)) {
                cbxCuentaDestinoTransaccionAdmin.setValue(consultarNumeroCuenta(seleccionado.getIdCuentas()[1]));
            }


        }
    }

    private String consultarNumeroCuenta(String idCuenta) {
        try {
            Seguimiento.registrarLog(1, "Consultando Numero de cuenta bancaria");
            System.out.println(cuentaController.consultar(idCuenta, TipoConsulta.ID_CUENTA));

            return cuentaController.consultar(idCuenta, TipoConsulta.ID_CUENTA).getNumeroCuenta();
        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Ocurrio un error en la consulta de propietarios: " + e.getMessage());
        }


        return "Propietario No encontrado";
    }

}
