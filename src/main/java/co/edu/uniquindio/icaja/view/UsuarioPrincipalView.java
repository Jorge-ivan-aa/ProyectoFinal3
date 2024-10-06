package co.edu.uniquindio.icaja.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UsuarioPrincipalView {
    UsuarioController usuarioController= new UsuarioController();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lbPonerNombreUsuario;

    @FXML
    private Label lbPonerSaldoUsuario;

    @FXML
    void DepositoAction(ActionEvent event) {

    }

    @FXML
    void CerrarAction(ActionEvent event) {

    }

    @FXML
    void HistorialAction(ActionEvent event) {

    }

    @FXML
    void PresupuestoAction(ActionEvent event) {

    }

    @FXML
    void RetirarAction(ActionEvent event) {

    }

    @FXML
    void TransferirDineroAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        

    }

}
