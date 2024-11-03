package co.edu.uniquindio.icaja.view.views.normal;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class PrincipalUsuarioView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lbPonerGastos;

    @FXML
    private Label lbPonerIngresos;

    @FXML
    private Label lbPonerPresupuesto;

    @FXML
    private Label lbSaldoUsuario;

    @FXML
    private Label lbSaltoLinea;

    @FXML
    private TableView<Transaccion> tvUltimasTransaccionesUsuario;

    @FXML
    void CharlarConIaAction(ActionEvent event) {

    }

    @FXML
    void DepositarUsuarioAction(ActionEvent event) {

    }

    @FXML
    void RetirarUsuarioAction(ActionEvent event) {

    }

    @FXML
    void TransferirUsuarioAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        lbSaltoLinea.setText("¿No sabes como plantear \n tu estrategia de ahorro?");

    }

    private void initDataBinging() {

    }
    private void mostrarInformacion(Transaccion seleccionado) {
        if (seleccionado != null) {

        }
    }

}
