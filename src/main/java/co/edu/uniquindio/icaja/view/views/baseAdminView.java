package co.edu.uniquindio.icaja.view.views;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class baseAdminView {

    UsuarioController usuarioController = new UsuarioController();

    @FXML
    private AnchorPane categoriaBox;

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
        ViewTools.cerrarVentana(categoriaBox);
    }

    @FXML
    void irCategoriaAction() {
        ViewTools.cambiarPantalla(categoriaBox, 0.125, cuentaBancariaBox, transaccionBox, usuarioBox);
    }

    @FXML
    void irCuentaAction() {
        ViewTools.cambiarPantalla(cuentaBancariaBox,0.125, categoriaBox, transaccionBox, usuarioBox);
    }

    @FXML
    void irTransaccionAction() {
        ViewTools.cambiarPantalla(transaccionBox,0.125, cuentaBancariaBox, categoriaBox, usuarioBox);
    }

    @FXML
    void irUsuarioAction() {
        ViewTools.cambiarPantalla(usuarioBox,0.125, transaccionBox, cuentaBancariaBox, categoriaBox);
    }

    @FXML
    void initialize() {
        ViewTools.cambiarPantalla(usuarioBox,0.125, transaccionBox, cuentaBancariaBox, categoriaBox);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            usuarioController.cerrarSesion();
        }));
    }
}