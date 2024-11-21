package co.edu.uniquindio.icaja.view.views;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class baseNormalView {

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
        lbNombreVentana.setText("Principal");
        ViewTools.cambiarColores(btnHome, "menu_opt_selected", btnCuentas, btnPerfil, btnSalir, btnPresupues);
        ViewTools.cambiarPantalla(principalUsuarioBox, 0.125, cuentasUsuarioBox, estadisticasUsuarioBox, perfilUsuarioBox);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            usuarioController.cerrarSesion();
        }));
    }

}