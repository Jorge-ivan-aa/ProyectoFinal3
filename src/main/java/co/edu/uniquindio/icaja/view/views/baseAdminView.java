package co.edu.uniquindio.icaja.view.views;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.utils.ViewTools;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class baseAdminView {

    UsuarioController usuarioController = new UsuarioController();

    public FontAwesomeIconView icono;
    @FXML
    private AnchorPane categoriaBox;

    @FXML
    private AnchorPane cuentaBancariaBox;

    @FXML
    private AnchorPane transaccionBox;

    @FXML
    private AnchorPane usuarioBox;

    @FXML
    void VolverAction(ActionEvent event) {

        usuarioController.cerrarSesion();
        ViewTools.ventanaEmergente("login.fxml", "ICaja Wallet", "styles/main.css", "styles/login.css");
        ViewTools.cerrarVentana(categoriaBox);
    }

    @FXML
    void irCategoriaAction(ActionEvent event) {
        ViewTools.cambiarPantalla(categoriaBox, 0.125, cuentaBancariaBox, transaccionBox, usuarioBox);
    }

    @FXML
    void irCuentaAction(ActionEvent event) {
        ViewTools.cambiarPantalla(cuentaBancariaBox,0.125, categoriaBox, transaccionBox, usuarioBox);
    }

    @FXML
    void irTransaccionAction(ActionEvent event) {
        ViewTools.cambiarPantalla(transaccionBox,0.125, cuentaBancariaBox, categoriaBox, usuarioBox);
    }

    @FXML
    void irUsuarioAction(ActionEvent event) {
        ViewTools.cambiarPantalla(usuarioBox,0.125, transaccionBox, cuentaBancariaBox, categoriaBox);
    }

    @FXML
    void initialize() {
        ViewTools.cambiarPantalla(usuarioBox,0.125, transaccionBox, cuentaBancariaBox, categoriaBox);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("El programa está a punto de cerrarse...");
            // Aquí puedes poner el código que quieras ejecutar antes de salir
        }));
    }
}