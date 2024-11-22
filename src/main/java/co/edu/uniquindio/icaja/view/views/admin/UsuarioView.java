package co.edu.uniquindio.icaja.view.views.admin;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.exception.crud.AtributoUtilizado;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.server.ConsumidorBase;
import co.edu.uniquindio.icaja.server.mapping.MensajeDTO;
import co.edu.uniquindio.icaja.server.services.Consumidor;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import javafx.beans.property.SimpleStringProperty;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UsuarioView implements Consumidor {
    UsuarioController usuarioController = new UsuarioController();
    String idUsuarioConsultado = "";

    @FXML
    private TableView<Usuario> tbUsuariosAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcCedulaUsuarioAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcClaveTransaccionalAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcClaveUsuarioAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcCorreoUsuarioAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcNombreUsuarioAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcSaldoTotalAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcTelefonoUsuarioAdmin;

    @FXML
    private TextField txtCedulaAdmin;

    @FXML
    private TextField txtClaveAdmin;

    @FXML
    private TextField txtClaveTransaccionalAdmin;

    @FXML
    private TextField txtCorreoAdmin;

    @FXML
    private TextField txtNombreAdmin;

    @FXML
    private TextField txtTelefonoAdmin;


    @FXML
    void actualizarUsuario() {
        String nombre = txtNombreAdmin.getText();
        String cedula = txtCedulaAdmin.getText();
        String correo = txtCorreoAdmin.getText();
        String clave = txtClaveAdmin.getText();
        String claveTransaccional = txtClaveTransaccionalAdmin.getText();
        String telefono = txtTelefonoAdmin.getText();

        if (ViewTools.NoHayCamposVacios(nombre, cedula, correo, telefono)) {
            UsuarioDto usuarioDto = new UsuarioDto(idUsuarioConsultado, nombre, cedula, correo, telefono, clave, claveTransaccional);
            try {
                usuarioController.actualizar(usuarioDto);
                String msj = "Se ha actualizado el usuario de cedula" + cedula + "correctamente";
                ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);
                limpiar();
            } catch (ElementoNoExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }

    }


    @FXML
    void crearUsuario() {
        String nombre = txtNombreAdmin.getText();
        String cedula = txtCedulaAdmin.getText();
        String correo = txtCorreoAdmin.getText();
        String clave = txtClaveAdmin.getText();
        String claveTransaccional = txtClaveTransaccionalAdmin.getText();
        String telefono = txtTelefonoAdmin.getText();


        if (ViewTools.NoHayCamposVacios(nombre, cedula, correo, telefono, clave, claveTransaccional)) {
            UsuarioDto usuarioDto = new UsuarioDto(null, nombre, cedula, correo, telefono, clave, claveTransaccional);

            try {
                usuarioController.crear(usuarioDto);
                String msj = "Se ha creado el usuario " + nombre + "correctamente";
                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
                limpiar();
            } catch (ElementoYaExiste | AtributoUtilizado e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);
        }

    }


    @FXML
    void eliminarUsuario() {
        try {
            usuarioController.eliminar(idUsuarioConsultado);
            String msj = "Se ha eliminado el usuario correctamente";
            ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);
            limpiar();
        } catch (ElementoNoExiste e) {
            ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
        }


    }


    @FXML
    void limpiarCamposUsuarioAction() {
        limpiar();
    }

    void limpiar() {
        ViewTools.limpiarCampos(txtCedulaAdmin,
                txtNombreAdmin,
                txtCorreoAdmin,
                txtTelefonoAdmin,
                txtClaveAdmin,
                txtClaveTransaccionalAdmin);
    }


    @FXML
    void initialize() {
        initview();
        consumirMensaje();
    }

    private void initview() {
        initDataBinging();
        tbUsuariosAdmin.getItems().clear();
        tbUsuariosAdmin.setItems(usuarioController.getListaUsuarioObservable());
        listenerSelectionUsuario();

        txtCedulaAdmin.textProperty().addListener((observable, oldValue, newValue) -> {
            txtCedulaAdmin.setText(formatearNumeroEntrada(newValue, 10));
        });

        txtTelefonoAdmin.textProperty().addListener((observable, oldValue, newValue) -> {
            txtTelefonoAdmin.setText(formatearNumeroEntrada(newValue, 10));
        });

        txtClaveTransaccionalAdmin.textProperty().addListener((observable, oldValue, newValue) -> {
            txtClaveTransaccionalAdmin.setText(formatearNumeroEntrada(newValue, 8));
        });
    }


    private void initDataBinging() {
        tbcNombreUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcCorreoUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        tbcCedulaUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula()));
        tbcClaveTransaccionalAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClaveTransaccional()));
        tbcTelefonoUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        tbcSaldoTotalAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSaldoTotal())));
        tbcClaveUsuarioAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClave()));
    }


    private void listenerSelectionUsuario() {
        tbUsuariosAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> this.mostrarInformacion(newSelection));
    }


    private void mostrarInformacion(Usuario seleccionado) {
        if (seleccionado != null) {
            idUsuarioConsultado = seleccionado.getIdUsuario();
            txtNombreAdmin.setText(seleccionado.getNombre());
            txtCedulaAdmin.setText(seleccionado.getCedula());
            txtCorreoAdmin.setText(seleccionado.getCorreo());
            txtTelefonoAdmin.setText(seleccionado.getTelefono());
            txtClaveTransaccionalAdmin.setPromptText(seleccionado.getClaveTransaccional());
            txtClaveAdmin.setPromptText(seleccionado.getClave());
        }
    }


    public String formatearNumeroEntrada(String input, int limite) {
        // Filtrar solo los números de la entrada
        String soloNumeros = input.replaceAll("[^0-9]", "");

        // Limitar la longitud a 10 caracteres (máximo)
        if (soloNumeros.length() > limite) {
            soloNumeros = soloNumeros.substring(0, limite);
        }

        return soloNumeros;
    }


    @Override
    public void consumirMensaje() {
        try {
            ConsumidorBase consumidorBase = ConsumidorBase.obtenerInstancia();
            consumidorBase.consumirMensaje(this);

        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Ocurrio un error en la sincronización con el servidor de parte del consumidor, revisalo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void procesarDTO(MensajeDTO dto) {
        if (!dto.IdInstanciaMensajera().equals(ModelFactory.getIdInstanciaMensajera())) {
            usuarioController.getFactory().sincronizarInstancia();
            limpiar();

        } else {
            Seguimiento.registrarLog(2, "No se va a sincronizar la instancia porque es la misma instancia que envia el mensaje");
        }
    }
}