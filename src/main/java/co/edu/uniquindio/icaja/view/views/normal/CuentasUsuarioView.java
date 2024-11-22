package co.edu.uniquindio.icaja.view.views.normal;

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
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class CuentasUsuarioView {
    CuentaController cuentaController= new CuentaController();
    UsuarioController usuarioController = new UsuarioController();
    Usuario usuarioLogueado = usuarioController.getFactory().getIcaja().getSesion().getUsuario();

    @FXML
    private ComboBox<EntidadBancaria> cbEntidadCuentaUsuario;

    @FXML
    private ComboBox<TipoCuenta> cbTipoCuentaUsuario;

    @FXML
    private ListView<String> lvListaCuentasUsuario;

    @FXML
    private MFXTextField txtNumeroCuentaUsuario;

    @FXML
    private MFXTextField txtSaldoCuentaUsuario;

    @FXML
    void crearCuentaUsuarioAction() {
        String numeroCuenta = txtNumeroCuentaUsuario.getText().replaceAll("-", "");
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
    void eliminarCuentaUsuarioAction() {
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
        // Validar que el usuario está logueado y tiene cuentas asociadas
        if (usuarioLogueado == null || usuarioLogueado.getIdCuentas() == null) {
            System.out.println("El usuario no está logueado o no tiene cuentas.");
            return;
        }

        // Obtener y revertir las cuentas del usuario
        List<String> cuentasUsuario = new ArrayList<>(usuarioLogueado.getIdCuentas());
        Collections.reverse(cuentasUsuario);

        // Filtrar las cuentas que pertenecen al usuario
        List<String> cuentasFormateadas = cuentaController.getListaCuentaObservable().stream()
                .filter(cuenta -> cuentasUsuario.contains(cuenta.getIdCuenta())) // Filtrar cuentas válidas
                .map(cuenta -> String.format("%s:%.2f", cuenta.getNumeroCuenta(), cuenta.getSaldo())) // Formatear como String
                .toList();

        // Convertir la lista formateada a ObservableList y asignarla al MFXListView
        ObservableList<String> cuentasObservable = FXCollections.observableArrayList(cuentasFormateadas);
        lvListaCuentasUsuario.setItems(cuentasObservable);

        // Configurar el CellFactory para mostrar las cadenas directamente (opcional, pero recomendable)
        lvListaCuentasUsuario.setCellFactory(lv -> new ListCell<>());
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
