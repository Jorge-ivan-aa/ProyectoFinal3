package co.edu.uniquindio.icaja.view.views.normal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;

import co.edu.uniquindio.icaja.controller.*;
import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.mapping.dto.RetiroODepostoDto;
import co.edu.uniquindio.icaja.mapping.dto.TransferenciaDto;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.mapping.services.ITransaccionDto;
import co.edu.uniquindio.icaja.model.*;
import co.edu.uniquindio.icaja.model.enums.TipoTransaccion;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.tools.NumTool;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class PrincipalUsuarioView {
    UsuarioController usuarioController = new UsuarioController();
    TransaccionController transaccionController = new TransaccionController();
    CategoriaController categoriaController = new CategoriaController();
    PresupuestoController presupuestoController = new PresupuestoController();
    CuentaController cuentaController = new CuentaController();

    Usuario usuarioLogueado = usuarioController.getFactory().getIcaja().getSesion().getUsuario();

    List<String> listaMensajes = new ArrayList<>();
    ChatBot chatBot = new ChatBot();
    String contexto= "Inicial";
    TipoTransaccion tipoTransaccion = null;


    @FXML
    private MFXFilterComboBox<String> cbxCategoriaTransaccion;


    @FXML
    private MFXFilterComboBox<String> cbxCuentaOrigen;


    @FXML
    private MFXFilterComboBox<String> cbxCuentaDestino;

    @FXML
    private Label lbCuentaOrigen;

    @FXML
    private Label lbCuentaDestino;

    @FXML
    private Label lbTipoTransaccion;

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
    private Pane panelTransaccionUsuario;
    @FXML
    private Pane panelUnoUsuario;


    @FXML
    private TextField txtDescripcionCategoriaTransaccion;

    @FXML
    private MFXTextField txtMensajeParaIA;

    @FXML
    private TextField txtMontoTransaccion;

    @FXML
    private TextField txtMotivoTransaccion;

    @FXML
    private TextField txtNombreCategoriaTransaccion;

    @FXML
    private PasswordField txtclaveTransaccional;


    @FXML
    void CharlarConIaAction() {

        ViewTools.cambiarPantalla(panelCharlarIA,0.225, panelTransaccionUsuario);
        //Agregar un mensaje inicial por parte del chatbot
        String mensajeInicial = "¡Hola! Soy tu asistente virtual digita inicial para información general, digita estrategias para consultas en terminos de fisica  ";
        AnchorPane userMessage2 = crearMensaje(mensajeInicial, false);
        lvListaChatConIA.getItems().add(userMessage2);
    }

    @FXML
    void EnviarMensajeIaAction() {

        String texto = txtMensajeParaIA.getText();

        if (!texto.isEmpty()) {
            AnchorPane userMessage = crearMensaje(texto, true);
            lvListaChatConIA.getItems().add(userMessage); // Agregar mensaje del usuario

            // Simulación de respuesta del "otro usuario"
            String [] respuestaBot = chatBot.procesarEntrada(texto,contexto);
            AnchorPane responseMessage = crearMensaje("IcajaBot: " + respuestaBot[1], false);
            lvListaChatConIA.getItems().add(responseMessage);
            contexto=respuestaBot[0];

            txtMensajeParaIA.clear(); // Limpiar el campo de entrada
        }
    }

    @FXML
    void DepositarUsuarioAction() {
        ViewTools.cambiarPantalla(panelTransaccionUsuario, 0.225, panelUnoUsuario, panelCharlarIA);
        tipoTransaccion = TipoTransaccion.DEPOSITO;
    }

    @FXML
    void RetirarUsuarioAction() {
        ViewTools.cambiarPantalla(panelTransaccionUsuario, 0.225, panelUnoUsuario, panelCharlarIA);
        tipoTransaccion = TipoTransaccion.RETIRO;
    }

    @FXML
    void TransferirUsuarioAction() {
        ViewTools.cambiarPantalla(panelTransaccionUsuario, 0.225, panelUnoUsuario, panelCharlarIA);
        lbCuentaDestino.setVisible(true);
        cbxCuentaDestino.setVisible(true);
        tipoTransaccion = TipoTransaccion.TRANSFERENCIA;

    }
    @FXML
    void salirChatIaAction() {
        ViewTools.cambiarPantalla(panelUnoUsuario,0.225, panelCharlarIA, panelTransaccionUsuario);
        lvListaChatConIA.getItems().clear();
    }

    @FXML
    void volverTransaccionAction() {
        salir();
    }

    void salir() {
        ViewTools.cambiarPantalla(panelUnoUsuario,0.225, panelCharlarIA, panelTransaccionUsuario);
        lbCuentaDestino.setVisible(false);
        cbxCuentaDestino.setVisible(false);
        tipoTransaccion = null;
    }


    @FXML
    void initialize() {
        ViewTools.cambiarPantalla(panelUnoUsuario,0.225, panelTransaccionUsuario);
        ViewTools.inicializarComboBox(cbxCuentaOrigen, obtenerCuentasPorId(usuarioLogueado.getIdCuentas()), Cuenta::getNumeroCuenta);
        ViewTools.inicializarComboBox(cbxCuentaDestino, cuentaController.getListaCuentaObservable(), Cuenta::getNumeroCuenta);
        ViewTools.inicializarComboBox(cbxCategoriaTransaccion, obtenerCategoriasPorId(usuarioLogueado.getIdCategorias()), Categoria::getNombre);

        mostrarInformacion(usuarioLogueado);
        llenarListaTransaccionesUsuario();
        llenarListaPresupuestosUsuario();
    }

    // Métodos generales de consulta
    private <T> T consultarPorId(String id, Function<String, T> consulta) {
        try {
            return consulta.apply(id);
        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Error al consultar: " + e.getMessage());
            return null;
        }
    }

    private <T> ObservableList<T> obtenerEntidadesPorIds(List<String> ids, Function<String, T> consulta) {
        ObservableList<T> entidades = FXCollections.observableArrayList();
        for (String id : ids) {
            T entidad = consultarPorId(id, consulta);
            if (entidad != null) {
                entidades.add(entidad);
            }
        }
        return entidades;
    }

    // Métodos específicos para Cuenta y Categoría
    private ObservableList<Cuenta> obtenerCuentasPorId(List<String> idCuentas) {
        return obtenerEntidadesPorIds(idCuentas, id -> cuentaController.consultar(id, TipoConsulta.ID_CUENTA));
    }

    private ObservableList<Categoria> obtenerCategoriasPorId(List<String> idCategorias) {
        return obtenerEntidadesPorIds(idCategorias, id -> categoriaController.consultar(id, TipoConsulta.ID_CATEGORIA));
    }

    // Método para obtener ID de una categoría por su nombre
    private String obtenerIdCategoriaPorNombre(String nombreCategoria, List<Categoria> categorias) {
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equalsIgnoreCase(nombreCategoria)) {
                return categoria.getIdCategoria();
            }
        }
        return null;
    }

    // Acción principal de realizar transacción
    @FXML
    void realizarTransaccionAction() {
        if (!validarCamposTransaccion()) {
            ViewTools.mostrarMensaje("¡Cuidado!", null, "Hay campos vacíos", Alert.AlertType.WARNING);
            return;
        } else if (txtclaveTransaccional.getText().equals("123e")) {
            ViewTools.mostrarMensaje("¡Cuidado!", null, "La clave transaccional no coincide", Alert.AlertType.WARNING);
            return;
        }

        try {
            Cuenta cuentaOrigenReal = obtenerCuenta(cbxCuentaOrigen.getValue());
            Categoria categoriaReal = obtenerCategoria();

            if (tipoTransaccion == TipoTransaccion.TRANSFERENCIA && cbxCuentaDestino.getValue() != null) {
                realizarTransferencia(cuentaOrigenReal, categoriaReal);
                limpiar();
                salir();

            } else {
                realizarRetiroODeposito(cuentaOrigenReal, categoriaReal);
                limpiar();
                salir();
            }
        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Error al procesar la transacción: " + e.getMessage());
            ViewTools.mostrarMensaje("¡Error!", null, "Ocurrió un error inesperado", Alert.AlertType.ERROR);
        }
    }

    void limpiar() {
        ViewTools.limpiarCampos(txtclaveTransaccional, txtMontoTransaccion, txtMotivoTransaccion, txtNombreCategoriaTransaccion, txtDescripcionCategoriaTransaccion);
        cbxCuentaDestino.clearSelection();
        cbxCuentaDestino.clearSelection();
        cbxCategoriaTransaccion.clearSelection();
    }


    // Métodos auxiliares de transacción
    private boolean validarCamposTransaccion() {
        return ViewTools.NoHayCamposVacios(txtMontoTransaccion.getText(), txtMotivoTransaccion.getText());
    }

    private Cuenta obtenerCuenta(String numeroCuenta) {
        return cuentaController.consultar(numeroCuenta, TipoConsulta.NUMERO_CUENTA);
    }

    private Categoria obtenerCategoria() {
        String categoriaNombre = cbxCategoriaTransaccion.getValue();
        if (categoriaNombre == null || categoriaNombre.isEmpty()) {
            if (ViewTools.NoHayCamposVacios(txtNombreCategoriaTransaccion.getText(), txtDescripcionCategoriaTransaccion.getText())) {
                Categoria nuevaCategoria = new Categoria(txtNombreCategoriaTransaccion.getText(), txtDescripcionCategoriaTransaccion.getText());
                categoriaController.crear(nuevaCategoria);
                usuarioLogueado.getIdCategorias().add(nuevaCategoria.getIdCategoria());
                usuarioController.sincronizarData();
                return nuevaCategoria;

            }
        } else {
            List<Categoria> categorias = obtenerCategoriasPorId(usuarioLogueado.getIdCategorias());
            String idCategoria = obtenerIdCategoriaPorNombre(categoriaNombre, categorias);
            return categoriaController.consultar(idCategoria, TipoConsulta.ID_CATEGORIA);
        }
        return null;
    }

    private void realizarTransferencia(Cuenta cuentaOrigen, Categoria categoria) throws Exception {
        Cuenta cuentaDestino = obtenerCuenta(cbxCuentaDestino.getValue());
        ITransaccionDto transaccionDto = new TransferenciaDto(
                null, tipoTransaccion, txtMontoTransaccion.getText(), txtMotivoTransaccion.getText(),
                cuentaOrigen.getIdCuenta(), cuentaDestino.getIdCuenta(), categoria.getIdCategoria()
        );
        transaccionController.crear(transaccionDto);
    }

    private void realizarRetiroODeposito(Cuenta cuentaOrigen, Categoria categoria) throws Exception {
        ITransaccionDto transaccionDto = new RetiroODepostoDto(
                null, tipoTransaccion, txtMontoTransaccion.getText(), txtMotivoTransaccion.getText(),
                cuentaOrigen.getIdCuenta(), categoria.getIdCategoria()
        );
        transaccionController.crear(transaccionDto);
    }


    private void mostrarInformacion(Usuario usuarioLogueado) {
        if (usuarioLogueado != null) {
            lbSaldoUsuario.setText(NumTool.formatearMonto(usuarioLogueado.getSaldoTotal()));
            lbPonerGastos.setText(NumTool.formatearMonto(usuarioLogueado.getGastos()));
            lbPonerIngresos.setText(NumTool.formatearMonto(usuarioLogueado.getIngresos()));

        }
    }
    private AnchorPane crearMensaje(String text, boolean isSentByUser) {

        // Crear el contenedor del mensaje
        AnchorPane messagePane = new AnchorPane();

        // Crear el Label con el texto del mensaje
        Label messageLabel = new Label(text);
        messageLabel.setWrapText(true); // Permitir que el texto se ajuste automáticamente
        messageLabel.setMaxWidth(250); // Ancho máximo para el texto antes de hacer wrap
        messageLabel.setPadding(new Insets(10)); // Espaciado interno para el mensaje

        // Estilo diferente para mensajes enviados y recibidos
        if (isSentByUser) {
            messageLabel.setStyle("-fx-background-color: lightblue; -fx-background-radius: 10;");
            AnchorPane.setRightAnchor(messageLabel, 10.0); // Alinear a la derecha
        } else {
            messageLabel.setStyle("-fx-background-color: lightgray; -fx-background-radius: 10;");
            AnchorPane.setLeftAnchor(messageLabel, 10.0); // Alinear a la izquierda
        }

        // Asegurar que el mensaje esté correctamente alineado dentro del AnchorPane
        AnchorPane.setTopAnchor(messageLabel, 10.0); // Espaciado superior
        messagePane.getChildren().add(messageLabel); // Añadir el mensaje al AnchorPane

        // dar opciones al usuario numéricamente

        return messagePane;

    }

    private void llenarListaTransaccionesUsuario() {
        // Obtener la lista de transacciones del usuario
        List<String> transaccionesUsuario = new ArrayList<>(usuarioLogueado.getIdTransacciones());
        Collections.reverse(transaccionesUsuario);

        // Convertir la lista a un ObservableList
        ObservableList<Transaccion> transaccionesObservableUsuario = FXCollections.observableArrayList();
        List<Transaccion> transacciones = transaccionController.getListaTransaccionObservable();

        for (String id: transaccionesUsuario) {
            for (Transaccion transaccion : transacciones) {
                if (transaccion.getIdTransaccion().equals(id)) {
                    transaccionesObservableUsuario.add(transaccion);
                }
            }
        }

        // Asignar la lista al ListView
        lvListaTransaccionesUsuario.setItems(transaccionesObservableUsuario);

        // Configurar la forma en que se muestran las transacciones
        lvListaTransaccionesUsuario.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Transaccion transaccion, boolean empty) {
                super.updateItem(transaccion, empty);

                if (empty || transaccion == null) {
                    setGraphic(null); // No mostramos nada si está vacío o es nulo.
                    setText(null);
                } else {
                    // Crear los Labels
                    String tipoYMonto = transaccion.getTipo() + " de " + NumTool.formatearMonto(transaccion.getMonto());
                    Label lblTipoMonto = new Label(tipoYMonto);

                    String categoria;
                    try {
                        Categoria categoriaObj = categoriaController.consultar(transaccion.getIdCategoria(), TipoConsulta.ID_CATEGORIA);
                        categoria = categoriaObj.getNombre();
                    } catch (Exception e) {
                        categoria = "Categoría no encontrada";
                    }
                    Label lblCategoria = new Label(categoria);

                    // Estilo para los Labels
                    lblTipoMonto.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #666");
                    lblCategoria.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

                    // Crear el VBox y configurar estilo
                    VBox vbox = new VBox(5, lblTipoMonto, lblCategoria);
                    vbox.setPadding(new Insets(5));
                    vbox.setAlignment(Pos.CENTER_LEFT);

                    // Asignar el VBox como gráfico de la celda
                    setGraphic(vbox);
                    setText(null); // Eliminar texto por defecto
                }
            }
        });
    }


    private void llenarListaPresupuestosUsuario() {
        // Obtener la lista de presupuestos del usuario
        List<String> presupuestosUsuario = new ArrayList<>(usuarioLogueado.getIdPresupuestos());
        Collections.reverse(presupuestosUsuario);

        // Convertir la lista a un ObservableList
        ObservableList<Presupuesto> presupuestosObservableUsuario = FXCollections.observableArrayList();
        List<Presupuesto> presupuestos = presupuestoController.getListaPresupuestoObservable();

        for (int i = 0; i <= 4; i++) {
            for (Presupuesto presupuesto : presupuestos) {
                if (presupuesto.getIdPresupuesto().equals(presupuestosUsuario.get(i))) {
                    presupuestosObservableUsuario.add(presupuesto);
                }
            }
        }

        Presupuesto presupuesto = new Presupuesto("algo", NumTool.parseToDinero("50000"), "SYSTEM");
        presupuesto.sumarGastos(NumTool.parseToDinero("10000"), "SYSTEM");
        presupuestosObservableUsuario.add(presupuesto);
        System.out.println("Tamaño de la lista de presupuestos: " + presupuestosObservableUsuario.size());
        // Asignar la lista al ListView
        lvListaPresupuestosUsuario.setItems(presupuestosObservableUsuario);

        // Configurar la forma en que se muestran los presupuestos
        lvListaPresupuestosUsuario.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Presupuesto presupuesto, boolean empty) {
                super.updateItem(presupuesto, empty);

                if (empty || presupuesto == null) {
                    setGraphic(null); // No mostramos nada si está vacío o es nulo.
                    setText(null);
                } else {
                    // Obtener los valores de montoAsignado y montoGastado como String
                    String montoAsignadoStr = presupuesto.getMontoAsignado(); // String
                    String montoGastadoStr = presupuesto.getMontoGastado(); // String

                    // Convertir los Strings a BigDecimal para operaciones aritméticas
                    BigDecimal montoAsignado = NumTool.parseToDinero(montoAsignadoStr);
                    BigDecimal montoGastado = NumTool.parseToDinero(montoGastadoStr);

                    // Calcular el porcentaje de gasto
                    BigDecimal porcentajeGastado = montoGastado.divide(montoAsignado, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

                    // Crear los Labels
                    Label lblNombrePresupuesto = new Label(presupuesto.getNombre()); // Nombre del presupuesto
                    Label lblPorcentaje = new Label(String.format("Gastado: %.2f%%", porcentajeGastado.doubleValue())); // Porcentaje gastado

                    // Crear la barra de progreso
                    ProgressBar progressBar = new ProgressBar(porcentajeGastado.doubleValue() / 100);
                    progressBar.setPrefWidth(300); // Asegúrate de que la barra tiene un ancho apropiado
                    progressBar.setMinHeight(25);
                    progressBar.setStyle("-fx-progress-color: #76c7c0;");  // Color verde

                    // Estilo para los Labels
                    lblNombrePresupuesto.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #320116");
                    lblPorcentaje.setStyle("-fx-font-size: 12px; -fx-text-fill: #666");

                    // Crear el VBox y configurar estilo
                    VBox vbox = new VBox(5, lblNombrePresupuesto, progressBar, lblPorcentaje);
                    vbox.setPadding(new Insets(10));
                    vbox.setAlignment(Pos.CENTER_LEFT);
                    vbox.setMinWidth(300);  // Asegúrate de que el VBox tenga suficiente espacio


                    // Asignar el VBox como gráfico de la celda
                    setGraphic(vbox);
                    setText(null); // Eliminar texto por defecto
                }
            }
        });
    }


}
