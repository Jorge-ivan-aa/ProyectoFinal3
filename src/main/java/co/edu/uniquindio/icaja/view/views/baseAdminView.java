package co.edu.uniquindio.icaja.view.views;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class baseAdminView {

    UsuarioController usuarioController = new UsuarioController();

    @FXML
    private Button btnCuentas;

    @FXML
    private Button btnEstadisticas;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnTransacciones;

    @FXML
    private Button btnUsuario;

    @FXML
    private AnchorPane estadisticaBox;

    @FXML
    private AnchorPane cuentaBancariaBox;

    @FXML
    private AnchorPane transaccionBox;

    @FXML
    private AnchorPane usuarioBox;

    @FXML
    void VolverAction() {
        usuarioController.cerrarSesion();
        ViewTools.generarVentana("login.fxml", "ICaja Wallet", "carga.fxml", "styles/main.css", "styles/login.css");
        ViewTools.cambiarColores(btnSalir, "menu_opt_selected", btnCuentas, btnUsuario, btnTransacciones, btnEstadisticas);
        ViewTools.cerrarVentana(estadisticaBox);
    }

    @FXML
    void irEstadisticaAction() {
        ViewTools.cambiarPantalla(estadisticaBox, 0.125, cuentaBancariaBox, transaccionBox, usuarioBox);
        ViewTools.cambiarColores(btnEstadisticas, "menu_opt_selected", btnCuentas, btnUsuario, btnTransacciones, btnSalir);
    }

    @FXML
    void irCuentaAction() {
        ViewTools.cambiarPantalla(cuentaBancariaBox, 0.125, estadisticaBox, transaccionBox, usuarioBox);
        ViewTools.cambiarColores(btnCuentas, "menu_opt_selected", btnEstadisticas, btnUsuario, btnTransacciones, btnSalir);
    }

    @FXML
    void irTransaccionAction() {
        ViewTools.cambiarPantalla(transaccionBox, 0.125, cuentaBancariaBox, estadisticaBox, usuarioBox);
        ViewTools.cambiarColores(btnTransacciones, "menu_opt_selected", btnEstadisticas, btnUsuario, btnCuentas, btnSalir);
    }

    @FXML
    void irUsuarioAction() {
        ViewTools.cambiarPantalla(usuarioBox, 0.125, transaccionBox, cuentaBancariaBox, estadisticaBox);
        ViewTools.cambiarColores(btnUsuario, "menu_opt_selected", btnEstadisticas, btnTransacciones, btnCuentas, btnSalir);
    }

    @FXML
    void initialize() {
        ViewTools.cambiarPantalla(usuarioBox, 0.125, transaccionBox, cuentaBancariaBox, estadisticaBox);
        ViewTools.cambiarColores(btnUsuario, "menu_opt_selected", btnEstadisticas, btnTransacciones, btnCuentas, btnSalir);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> usuarioController.cerrarSesion()));
    }

}