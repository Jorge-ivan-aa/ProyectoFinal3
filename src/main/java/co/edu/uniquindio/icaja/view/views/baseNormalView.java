package co.edu.uniquindio.icaja.view.views;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.server.ConsumidorBase;
import co.edu.uniquindio.icaja.server.mapping.MensajeDTO;
import co.edu.uniquindio.icaja.server.services.Consumidor;
import co.edu.uniquindio.icaja.server.services.Productor;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;


public class baseNormalView implements Consumidor {

    UsuarioController usuarioController = new UsuarioController();

    @FXML
    private Button btnCuentas;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnPresupues;

    @FXML
    private Button btnSalir;

    @FXML
    private Label lbNombreVentana;

    @FXML
    private AnchorPane cuentasUsuarioBox;

    @FXML
    private AnchorPane estadisticasUsuarioBox;

    @FXML
    private AnchorPane perfilUsuarioBox;

    @FXML
    private AnchorPane principalUsuarioBox;

    @FXML
    private MenuButton menuNotificaciones;

    @FXML
    void VolverAction() {
        usuarioController.cerrarSesion();
        ViewTools.cambiarColores(btnSalir, "menu_opt_selected", btnCuentas, btnHome, btnPresupues, btnPerfil);
        ViewTools.generarVentana("login.fxml", "ICaja Wallet", "carga.fxml", "styles/main.css", "styles/login.css");
        ViewTools.cerrarVentana(principalUsuarioBox);
    }

    @FXML
    void irCuentasAction() {
        lbNombreVentana.setText("Gestion de cuentas");
        ViewTools.cambiarColores(btnCuentas, "menu_opt_selected", btnPresupues, btnHome, btnSalir, btnPerfil);
        ViewTools.cambiarPantalla(cuentasUsuarioBox, 0.125, principalUsuarioBox, estadisticasUsuarioBox, perfilUsuarioBox);
    }

    @FXML
    void irPresupuestoAction() {
        lbNombreVentana.setText("Gestion de presupuestos");
        ViewTools.cambiarColores(btnPresupues, "menu_opt_selected", btnCuentas, btnHome, btnSalir, btnPerfil);
        ViewTools.cambiarPantalla(estadisticasUsuarioBox, 0.125, principalUsuarioBox, cuentasUsuarioBox, perfilUsuarioBox);
    }

    @FXML
    void irPerfilAction() {
        lbNombreVentana.setText("Configurar perfil");
        ViewTools.cambiarColores(btnPerfil, "menu_opt_selected", btnCuentas, btnHome, btnSalir, btnPresupues);
        ViewTools.cambiarPantalla(perfilUsuarioBox, 0.125, principalUsuarioBox, cuentasUsuarioBox, estadisticasUsuarioBox);
    }

    @FXML
    void irPrincipalAction() {
        lbNombreVentana.setText("Principal");
        ViewTools.cambiarColores(btnHome, "menu_opt_selected", btnCuentas, btnPerfil, btnSalir, btnPresupues);
        ViewTools.cambiarPantalla(principalUsuarioBox, 0.125, cuentasUsuarioBox, estadisticasUsuarioBox, perfilUsuarioBox);
    }

    @FXML
    void initialize() {
        consumirMensaje();
        lbNombreVentana.setText("Principal");
        ViewTools.cambiarColores(btnHome, "menu_opt_selected", btnCuentas, btnPerfil, btnSalir, btnPresupues);
        ViewTools.cambiarPantalla(principalUsuarioBox, 0.125, cuentasUsuarioBox, estadisticasUsuarioBox, perfilUsuarioBox);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            usuarioController.cerrarSesion();
        }));

    }

    public void sincronizarMenuConDTO(MensajeDTO dto, MenuButton menuButton) {
        Platform.runLater(() -> {
            // Crea un nuevo ítem con el contenido del mensaje
            String nuevoMensaje = dto.contenido(); // Obtiene el contenido del DTO
            if (!nuevoMensaje.isEmpty()) {
                MenuItem menuItem = new MenuItem(nuevoMensaje); // Agrega con índice
                menuButton.getItems().add(menuItem); // Añade al menú
                menuItem.setText(Integer.toString(menuButton.getItems().size()));
            }
        });
    }

    @Override
    public void consumirMensaje() {
        try {
            ConsumidorBase consumidorBase = ConsumidorBase.obtenerInstancia();
            consumidorBase.consumirMensaje(this);

        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Ocurrió un error en la sincronización con el servidor de parte del consumidor, revísalo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void procesarDTO(MensajeDTO dto) {
        // Verifica que la instancia no sea la misma
        if (!dto.IdInstanciaMensajera().equals(ModelFactory.getIdInstanciaMensajera())) {
            usuarioController.getFactory().sincronizarInstancia();


        } else {
            Seguimiento.registrarLog(2, "No se va a sincronizar la instancia porque es la misma instancia que envía el mensaje");
        }

        // Añade el nuevo mensaje al menú
        sincronizarMenuConDTO(dto, menuNotificaciones);

    }



}