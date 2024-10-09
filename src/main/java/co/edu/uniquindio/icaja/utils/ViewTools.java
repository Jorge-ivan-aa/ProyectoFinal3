package co.edu.uniquindio.icaja.utils;

import co.edu.uniquindio.icaja.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class ViewTools {

    public static void mostrarMensaje(String title, String header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void ventanaEmergente(String url, String title, String style) {
        Scene scene = new Scene(new Pane());

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(url));
            scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource(style)).toExternalForm());
        }catch (Exception e){
            mostrarMensaje("Error", "Error al cargar la transacción", e.getMessage(), Alert.AlertType.ERROR);
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    public static LocalDate convertToLocalDate(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static void cerrarVentana(TextField context) {
        Stage stage = (Stage) ((Node) context).getScene().getWindow();
        stage.close();
    }

    public static void limpiarCampos(TextField... campoDeTexto) {
        for (TextField texto : campoDeTexto) {
            texto.setText("");
        }
    }

    public static boolean hayCamposVacios(String... camposDeTexto) {
        for (String texto : camposDeTexto) {
            if (texto.isEmpty() || texto.equals(" ")) {
                return true;
            }
        }
        return false;
    }

}