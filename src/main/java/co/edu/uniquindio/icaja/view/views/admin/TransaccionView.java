package co.edu.uniquindio.icaja.view.views.admin;

import co.edu.uniquindio.icaja.controller.TransaccionController;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.mapping.dto.TransaccionDto;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.TransaccionFactory;
import co.edu.uniquindio.icaja.model.enums.TipoCuenta;
import co.edu.uniquindio.icaja.utils.ViewTools;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class TransaccionView {
    TransaccionController transaccionController = new TransaccionController();
    TransaccionFactory transaccionFactory = new TransaccionFactory();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXComboBox<TransaccionFactory> cbTipoTransaccionAdmin;

    @FXML
    private TableColumn<Transaccion, String> tcCategoriaTransaccionAdmin;

    @FXML
    private MFXComboBox<Transaccion> cbCuentaTransaccionAdmin;

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
    private MFXTextField txtFechaTransaccionAdmin;

    @FXML
    private MFXTextField txtIdTransaccionAdmin;

    @FXML
    private MFXTextField txtMontoTransaccionAdmin;

    @FXML
    private MFXTextField txtMotivoTransaccionAdmin;

    @FXML
    void consultarTransaccionAction(ActionEvent event) {

    }

    @FXML
    void crearTransaccionAction(ActionEvent event) {
        String id = txtIdTransaccionAdmin.getText();
        String fecha = txtFechaTransaccionAdmin.getText();
        String monto = txtMontoTransaccionAdmin.getText();
        String tipo = cbTipoTransaccionAdmin.getSelectedText();
        String cuenta = String.valueOf(TipoCuenta.valueOf(cbCuentaTransaccionAdmin.getSelectedText()));
        String motivo = txtMotivoTransaccionAdmin.getText();
        if (!ViewTools.hayCamposVacios(id,fecha,monto,motivo)) {
           // TransaccionDto transaccionDto = new TransaccionDto(Integer.parseInt(id),fecha,Double.parseDouble(monto),tipo, TipoCuenta.valueOf(cuenta),motivo);

            try {
              //  TransaccionFactory.crearTransaccion(transaccionDto);
                String msj = "Se ha creado la Transacción " + id + "correctamente";
                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoYaExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }

        ViewTools.limpiarCampos(txtIdTransaccionAdmin,
                txtFechaTransaccionAdmin,
                txtMontoTransaccionAdmin,
                cbTipoTransaccionAdmin,
                cbCuentaTransaccionAdmin,
                txtMotivoTransaccionAdmin);


    }

    @FXML
    void eliminarTransaccionAction(ActionEvent event) {

    }
    @FXML
    void LimpiarCamposTransaccionAction(ActionEvent event) {
        ViewTools.limpiarCampos(txtIdTransaccionAdmin,
                txtFechaTransaccionAdmin,
                txtMontoTransaccionAdmin,
                cbTipoTransaccionAdmin,
                cbCuentaTransaccionAdmin,
                txtMotivoTransaccionAdmin);
    }

    @FXML
    void initialize() {

    }

}
