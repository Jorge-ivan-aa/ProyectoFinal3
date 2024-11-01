package co.edu.uniquindio.icaja.view.views;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.utils.ViewTools;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;


public class baseNormalView {

    UsuarioController usuarioController = new UsuarioController();

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
        ViewTools.generarVentana("login.fxml", "ICaja Wallet", "carga.fxml", "styles/main.css", "styles/login.css");
        ViewTools.cerrarVentana(principalUsuarioBox);
    }

    @FXML
    void irCuentasAction() {
        ViewTools.cambiarPantalla(cuentasUsuarioBox, 0.125, principalUsuarioBox, estadisticasUsuarioBox, perfilUsuarioBox);
    }

    @FXML
    void irEstadisticasAction() {
        ViewTools.cambiarPantalla(estadisticasUsuarioBox, 0.125, principalUsuarioBox, cuentasUsuarioBox, perfilUsuarioBox);
    }

    @FXML
    void irPerfilAction() {
        ViewTools.cambiarPantalla(perfilUsuarioBox, 0.125, principalUsuarioBox, cuentasUsuarioBox, estadisticasUsuarioBox);
    }

    @FXML
    void irPrincipalAction() {
        ViewTools.cambiarPantalla(principalUsuarioBox, 0.125, cuentasUsuarioBox, estadisticasUsuarioBox, perfilUsuarioBox);
    }

    @FXML
    void initialize() {

        ViewTools.cambiarPantalla(principalUsuarioBox, 0.125, cuentasUsuarioBox, estadisticasUsuarioBox, perfilUsuarioBox);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            usuarioController.cerrarSesion();
        }));
    }

}