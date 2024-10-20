package co.edu.uniquindio.icaja.utils;

import co.edu.uniquindio.icaja.App;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class ViewTools {

    // Metodo para mostrar un mensaje en pantalla
    public static void mostrarMensaje(String title, String header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Metodo para abrir ventana
    public static void ventanaEmergente(String url, String title, String style) {
        Scene scene = new Scene(new Pane());

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(url));
            scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource(style)).toExternalForm());
        }catch (Exception e){
            mostrarMensaje("Error", "Error al cargar la interfaz grafica", e.getMessage(), Alert.AlertType.ERROR);
            Seguimiento.registrarLog(3, "No se pudó cargar la interfaz" + e.getMessage());
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }


    // Metodo para cerrar una venta segun un nodo dado
    public static void cerrarVentana(TextField context) {
        Stage stage = (Stage) ((Node) context).getScene().getWindow();
        stage.close();
    }

    // Metodo para limpiar campos de texto
    public static void limpiarCampos(TextField... campoDeTexto) {
        for (TextField texto : campoDeTexto) {
            texto.setText("");
        }
    }

    // Metodo para verificar si hay campos de texto vacios
    public static boolean hayCamposVacios(String... camposDeTexto) {
        for (String texto : camposDeTexto) {
            if (texto.isEmpty() || texto.equals(" ")) {
                return true;
            }
        }
        return false;
    }

    // Metodo para cambiar entre varias paneles
    public static void cambiarPantalla(Pane primario, Pane... secundarios) {
        // Verificación simple para evitar errores
        if (primario == null || secundarios == null) {
            Seguimiento.registrarLog(3, "No se pudo cambiar de pantalla, algun panel es nulo.");
        } else {
            for (Pane secundario : secundarios) {
                secundario.setVisible(false);
                fadeOut(secundario);
            }
            primario.setVisible(true);
            fadeIn(primario);
        }

    }

    // Animaciones
    public static void fadeOut(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), node);
        fadeTransition.setFromValue(1.0); // Opacidad inicial
        fadeTransition.setToValue(0.0);    // Opacidad final
        fadeTransition.play();
    }

    public static void fadeIn(Node node) {
        node.setOpacity(0.0); // Asegúrate de que el nodo esté completamente invisible antes de iniciar
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

}