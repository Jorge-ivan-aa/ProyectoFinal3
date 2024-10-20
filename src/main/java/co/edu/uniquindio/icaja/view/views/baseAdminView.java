package co.edu.uniquindio.icaja.view.views;

import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.utils.ViewTools;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class baseAdminView {


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

    }

    @FXML
    void irCategoriaAction(ActionEvent event) {
        ViewTools.cambiarPantalla(categoriaBox, cuentaBancariaBox, transaccionBox, usuarioBox);
    }

    @FXML
    void irCuentaAction(ActionEvent event) {
        ViewTools.cambiarPantalla(cuentaBancariaBox, categoriaBox, transaccionBox, usuarioBox);
    }

    @FXML
    void irTransaccionAction(ActionEvent event) {
        ViewTools.cambiarPantalla(transaccionBox, cuentaBancariaBox, categoriaBox, usuarioBox);
    }

    @FXML
    void irUsuarioAction(ActionEvent event) {
        ViewTools.cambiarPantalla(usuarioBox, transaccionBox, cuentaBancariaBox, categoriaBox);
    }

    @FXML
    void initialize() {
        ViewTools.cambiarPantalla(usuarioBox, transaccionBox, cuentaBancariaBox, categoriaBox);
    }
}