package co.edu.uniquindio.icaja.view.views.normal;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.model.*;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

public class PrincipalUsuarioView {
    UsuarioController usuarioController = new UsuarioController();
    Sesion sesion = usuarioController.getFactory().getIcaja().getSesion();
    Usuario usuarioLogueado = sesion.getUsuario();
    List<String> listaMensajes = new ArrayList<>();
    ChatBot chatBot = new ChatBot();

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
    private MFXListView<AnchorPane> lvListaChatConIA;

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
        //Agregar un mensaje inicial por parte del chatbot
        String mensajeInicial = "¡Hola! Soy tu asistente. ¿En qué puedo ayudarte?";
        AnchorPane userMessage2 = crearMensaje(mensajeInicial, false);
        lvListaChatConIA.getItems().add(userMessage2);
    }
    //con true envia mensaje por parte del usuario, con false envia mensaje por parte del chatBot
    @FXML
    void EnviarMensajeIaAction() {
//        String mensaje = txtMensajeParaIA.getText();
//        listaMensajes.add(mensaje);
//        lvListaChatConIA.setItems((ObservableList<AnchorPane>) listaMensajes);
        String texto = txtMensajeParaIA.getText();
        if (!texto.isEmpty()) {
            AnchorPane userMessage = crearMensaje(texto, true);
            lvListaChatConIA.getItems().add(userMessage); // Agregar mensaje del usuario

            // Simulación de respuesta del "otro usuario"
            String respuestaBot = chatBot.procesarEntrada(texto);
            AnchorPane responseMessage = crearMensaje("IcajaBot: " + respuestaBot, false);
            lvListaChatConIA.getItems().add(responseMessage);

            txtMensajeParaIA.clear(); // Limpiar el campo de entrada
        }
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
    private AnchorPane crearMensaje(String text, boolean isSentByUser) {
        VBox chatBox = new VBox(10); // Espaciado de 10 píxeles entre mensajes
        chatBox.setPadding(new Insets(10));
        Label messageLabel = new Label(text);
        messageLabel.setWrapText(true);
        // Permitir que el texto se ajuste automáticamente

        //messageLabel.setPadding(new Insets());

        // Estilo diferente para mensajes enviados y recibidos
        if (isSentByUser) {
            messageLabel.setStyle("-fx-background-color: lightblue; -fx-background-radius: 10;");
        } else {
            messageLabel.setStyle("-fx-background-color: lightgray; -fx-background-radius: 10;");
        }

        AnchorPane messagePane = new AnchorPane(messageLabel);
        if (isSentByUser) {
            // Alinear a la derecha
            AnchorPane.setLeftAnchor(messageLabel, 370.0);
            messagePane.setPadding(new Insets(5));

            //AnchorPane.setBottomAnchor(messageLabel, 50.0);
        } else {
            //Alinear a la izquierda
            AnchorPane.setRightAnchor(messageLabel, 50.0);
            messagePane.setPadding(new Insets(5));
           // AnchorPane.setTopAnchor(messageLabel, 50.0);
        }


        //messageLabel.setPrefWidth(250);
        //messagePane.setPadding(new Insets(0x5));
        return messagePane;

    }





}
