package co.edu.uniquindio.icaja.view.views.normal;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.model.Presupuesto;
import co.edu.uniquindio.icaja.model.Sesion;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class PrincipalUsuarioView {
    UsuarioController usuarioController = new UsuarioController();
    Sesion sesion = usuarioController.getFactory().getIcaja().getSesion();
    Usuario usuarioLogueado = sesion.getUsuario();
    List<String> listaMensajes = new ArrayList<>();

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
    private MFXListView<String> lvListaChatConIA;

    @FXML
    private ListView<Presupuesto> lvListaPresupuestosUsuario;

    @FXML
    private ListView<Transaccion> lvListaTransaccionesUsuario;
    @FXML
    private Pane panelCharlarIA;
    @FXML
    private Pane panelDepositarUsuario;

    @FXML
    private Pane panelRetirarUsuario;

    @FXML
    private Pane panelTransferirUsuario;
    @FXML
    private Pane panelUnoUsuario;

    @FXML
    private MFXTextField txtMensajeParaIA;

    @FXML
    void CharlarConIaAction() {
        ViewTools.cambiarPantalla(panelCharlarIA,0.225, panelDepositarUsuario, panelTransferirUsuario );

    }
    @FXML
    void EnviarMensajeIaAction() {
        String mensaje = txtMensajeParaIA.getText();
        listaMensajes.add(mensaje);
        lvListaChatConIA.setItems((ObservableList<String>) listaMensajes);

    }

    @FXML
    void DepositarUsuarioAction() {
        ViewTools.cambiarPantalla(panelDepositarUsuario, 0.225, panelUnoUsuario);

    }

    @FXML
    void RetirarUsuarioAction() {
        ViewTools.cambiarPantalla(panelRetirarUsuario, 0.225, panelUnoUsuario, panelDepositarUsuario, panelTransferirUsuario);

    }

    @FXML
    void TransferirUsuarioAction() {
        ViewTools.cambiarPantalla(panelTransferirUsuario, 0.225, panelUnoUsuario,panelDepositarUsuario,panelRetirarUsuario);

    }
    @FXML
    void salirChatIaAction() {
        ViewTools.cambiarPantalla(panelUnoUsuario,0.225, panelCharlarIA,panelDepositarUsuario,panelRetirarUsuario,panelTransferirUsuario);

    }
    @FXML
    void volverDepositoAction() {
        ViewTools.cambiarPantalla(panelUnoUsuario,0.225, panelDepositarUsuario,panelRetirarUsuario,panelTransferirUsuario);
    }

    @FXML
    void volverRetiroAction() {
        ViewTools.cambiarPantalla(panelUnoUsuario,0.225, panelDepositarUsuario,panelRetirarUsuario,panelTransferirUsuario);
        System.out.println("pasó por acá");

    }

    @FXML
    void volverTransferenciaAction() {
        ViewTools.cambiarPantalla(panelUnoUsuario,0.225, panelDepositarUsuario,panelRetirarUsuario,panelTransferirUsuario);
    }


    @FXML
    void initialize() {
        ViewTools.cambiarPantalla(panelUnoUsuario,0.225, panelDepositarUsuario,panelRetirarUsuario,panelTransferirUsuario);
        mostrarInformacion(usuarioLogueado);
    }

    private void mostrarInformacion(Usuario usuarioLogueado) {
        if (usuarioLogueado != null) {
            lbSaldoUsuario.setText(String.valueOf(usuarioLogueado.getSaldoTotal()));
            lbPonerGastos.setText(String.valueOf(usuarioLogueado.getGastos()));
            lbPonerIngresos.setText(String.valueOf(usuarioLogueado.getIngresos()));
            //lbPonerPresupuesto.setText(String.valueOf(usuarioLogueado).getPresupuestos());


//            txtCorreoAdmin.setText(seleccionado.getCorreo());
//            txtTelefonoAdmin.setText(seleccionado.getTelefono());
//            txtClaveTransaccionalAdmin.setPromptText(seleccionado.getClaveTransaccional());
//            txtClaveAdmin.setPromptText(seleccionado.getClave());
        }
    }

}
