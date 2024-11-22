package co.edu.uniquindio.icaja.view.views.normal;

import co.edu.uniquindio.icaja.controller.CuentaController;
import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.exception.transacciones.MontoInvalido;
import co.edu.uniquindio.icaja.mapping.dto.CuentaDto;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.Cuenta;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.enums.EntidadBancaria;
import co.edu.uniquindio.icaja.model.enums.TipoCuenta;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.tools.NumTool;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class CuentasUsuarioView {
    CuentaController cuentaController= new CuentaController();
    UsuarioController usuarioController = new UsuarioController();
    Usuario usuarioLogueado = usuarioController.getFactory().getIcaja().getSesion().getUsuario();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXFilterComboBox<EntidadBancaria> cbEntidadCuentaUsuario;

    @FXML
    private MFXComboBox<TipoCuenta> cbTipoCuentaUsuario;

    @FXML
    private MFXListView<Cuenta> lvListaCuentasUsuario;

    @FXML
    private MFXTextField txtNumeroCuentaUsuario;

    @FXML
    private MFXTextField txtSaldoCuentaUsuario;

    @FXML
    void crearCuentaUsuarioAction(ActionEvent event) {
        String numeroCuenta = txtNumeroCuentaUsuario.getText().replaceAll("-", "");;
        String saldoCuenta = txtSaldoCuentaUsuario.getText();
        TipoCuenta tipoCuenta = (TipoCuenta.valueOf(String.valueOf(cbTipoCuentaUsuario.getValue())));
        EntidadBancaria entidadCuenta = EntidadBancaria.valueOf(String.valueOf(cbEntidadCuentaUsuario.getValue()));
        if (ViewTools.NoHayCamposVacios(numeroCuenta, saldoCuenta)) {
            if (numeroCuenta.length() <= 10) {
                CuentaDto cuentaDto = new CuentaDto("", entidadCuenta, numeroCuenta, tipoCuenta, saldoCuenta, usuarioLogueado.getIdUsuario());

                try {
                    cuentaController.crear(cuentaDto);
                    String msj = "Se ha creado la cuenta " + numeroCuenta + " con la entidad " + entidadCuenta + " correctamente";
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

        ViewTools.limpiarCampos(txtSaldoCuentaUsuario,
                txtNumeroCuentaUsuario);


    }

    @FXML
    void eliminarCuentaUsuarioAction(ActionEvent event) {
        String numeroCuenta= txtNumeroCuentaUsuario.toString();
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

        ViewTools.limpiarCampos(txtNumeroCuentaUsuario,
                txtSaldoCuentaUsuario);

    }

    @FXML
    void initialize() {
        cbEntidadCuentaUsuario.getItems().addAll(EntidadBancaria.values());
        cbTipoCuentaUsuario.getItems().addAll(TipoCuenta.values());
        llenarTablaCuentasUsuario();

    }

    public void llenarTablaCuentasUsuario() {
        if (usuarioLogueado == null || usuarioLogueado.getIdCuentas() == null) {
            System.out.println("El usuario no está logueado o no tiene cuentas.");
            return;
        }

        // Obtener las cuentas del usuario y revertir el orden
        List<String> cuentasUsuario = new ArrayList<>(usuarioLogueado.getIdCuentas());
        Collections.reverse(cuentasUsuario);

        // Filtrar las cuentas que pertenecen al usuario
        List<Cuenta> cuentasFiltradas = cuentaController.getListaCuentaObservable().stream()
                .filter(cuenta -> cuentasUsuario.contains(cuenta.getIdCuenta()))
                .toList();

        // Convertir la lista a ObservableList y asignarla al MFXListView
        ObservableList<Cuenta> cuentasObservable = FXCollections.observableArrayList(cuentasFiltradas);
        lvListaCuentasUsuario.setItems(cuentasObservable);

        // Configurar cómo se muestran las cuentas en las celdas
        lvListaCuentasUsuario.setCellFactory(lv -> new ListCell<>() {
            protected void updateItem(Cuenta cuenta, boolean empty) {
                super.updateItem(cuenta, empty);

                if (empty || cuenta == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    // Crear los Labels para cada información de la cuenta
                    Label lblEntidad = new Label("Entidad: " + cuenta.getEntidad());
                    Label lblSaldo = new Label("Saldo: " + String.format("$%.2f", cuenta.getSaldo()));
                    Label lblTipo = new Label("Tipo de Cuenta: " + cuenta.getTipo());
                    Label lblNumero = new Label("Número de Cuenta: " + cuenta.getNumeroCuenta());

                    // Ajustar estilos para los Labels
                    lblEntidad.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                    lblSaldo.setStyle("-fx-font-size: 12px; -fx-text-fill: #008000;");
                    lblTipo.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
                    lblNumero.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

                    // Crear un VBox para agrupar la información
                    VBox vbox = new VBox(lblEntidad, lblSaldo, lblTipo, lblNumero);
                    vbox.setSpacing(5);
                    vbox.setPadding(new Insets(10));
                    vbox.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-background-color: #f9f9f9; -fx-background-radius: 5px;");

                    // Alinear y configurar el VBox como gráfico de la celda
                    vbox.setAlignment(Pos.CENTER_LEFT);
                    setGraphic(vbox);
                    setText(null); // Evitar texto adicional
                }
            }
        });
    }






    // Métodos específicos para Cuenta y Categoría
    private ObservableList<Cuenta> obtenerCuentasPorId(List<String> idCuentas) {
        return obtenerEntidadesPorIds(idCuentas, id -> cuentaController.consultar(id, TipoConsulta.ID_CUENTA));
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
    // Métodos generales de consulta
    private <T> T consultarPorId(String id, Function<String, T> consulta) {
        try {
            return consulta.apply(id);
        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Error al consultar: " + e.getMessage());
            return null;
        }
    }
    // Métodos específicos para Cuenta y Categoría
    private ObservableList<Cuenta> obtenerTipoCuentasPorId(List<String> tipos) {
        return obtenerTiposPorIds(tipos, id -> cuentaController.consultar(id, TipoConsulta.ID_CATEGORIA));
    }
    private <T> ObservableList<T> obtenerTiposPorIds(List<String> ids, Function<String, T> consulta) {
        ObservableList<T> entidades = FXCollections.observableArrayList();
        for (String id : ids) {
            T entidad = consultarPorId(id, consulta);
            if (entidad != null) {
                entidades.add(entidad);
            }
        }
        return entidades;
    }




}
