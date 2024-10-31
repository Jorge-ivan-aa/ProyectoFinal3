package co.edu.uniquindio.icaja.utils;

import co.edu.uniquindio.icaja.App;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeLineCap;
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
//    public static void ventanaEmergente(String url, String title, String... styles) {
//        Scene scene = new Scene(new Pane());
//
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(url));
//            scene = new Scene(fxmlLoader.load());
//
//            for (String style: styles) {
//                scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource(style)).toExternalForm());
//            }
//
//        }catch (Exception e){
//            mostrarMensaje("Error", "Error al cargar la interfaz grafica", e.getMessage(), Alert.AlertType.ERROR);
//            Seguimiento.registrarLog(3, "No se pudó cargar la interfaz" + e.getMessage());
//        }
//
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.setTitle(title);
//        stage.show();
//    }

    public static void ventanaEmergente(String url, String title, String urlCarga, String... styles) {

        Scene escenaCarga;

        if (urlCarga != null) {
            escenaCarga = crearEscenaCarga(urlCarga);
        } else {
            escenaCarga = new Scene(new Pane());
        }

        Stage stage = new Stage();

        // Mostrar la pantalla de carga mientras se carga el contenido principal
        stage.setScene(escenaCarga);
        stage.setTitle("Cargando...");
        stage.show();

        fadeIn(escenaCarga.getRoot(), 0.5); // Hacer fadeIn en la pantalla de carga

        Task<Scene> cargarEscenaTask = new Task<>() {
            @Override
            protected Scene call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(url));
                Scene scene = new Scene(fxmlLoader.load());
                for (String style : styles) {
                    scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource(style)).toExternalForm());
                }
                return scene;
            }

            @Override
            protected void succeeded() {
                fadeOut(escenaCarga.getRoot(), 0.25);

                // Usar un listener para cambiar a la nueva escena solo después de fadeOut
                escenaCarga.getRoot().opacityProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.doubleValue() == 0.0) { // Cuando el fadeOut termine
                        stage.setScene(getValue());  // Cambiar a la escena principal
                        stage.setTitle(title);
                        fadeIn(getValue().getRoot(), 0.25); // Aplicar fadeIn en la nueva escena
                    }
                });
            }

            @Override
            protected void failed() {
                mostrarMensaje("Error", "Error al cargar la interfaz gráfica", getException().getMessage(), Alert.AlertType.ERROR);
                Seguimiento.registrarLog(3, "No se pudo cargar la interfaz: " + getException().getMessage());
                stage.close();
            }
        };

        new Thread(cargarEscenaTask).start();
    }


    // metodo para crear escena de carga
    public static Scene crearEscenaCarga(String rutaFXML) {
        Pane rootCarga;

        try {
            // Cargar el diseño de la pantalla de carga desde el archivo FXML
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(rutaFXML));
            rootCarga = fxmlLoader.load();
        } catch (Exception e) {
            mostrarMensaje("Error", "Error al cargar la pantalla de carga", e.getMessage(), Alert.AlertType.ERROR);
            Seguimiento.registrarLog(3, "No se pudo cargar la pantalla de carga: " + e.getMessage());
            rootCarga = new StackPane();  // Fallback si el FXML falla
        }


        // Crear una rueda de carga
        Arc rueda = new Arc(0, 0, 40, 40, 0, 270); // Un arco de 270 grados
        rueda.setType(ArcType.OPEN);
        rueda.setStrokeWidth(8);
        rueda.setStroke(Color.LIGHTGRAY);
        rueda.setFill(null); // Sin relleno, para hacerla hueca
        rueda.setStrokeLineCap(StrokeLineCap.ROUND); // Para los bordes redondeados

        // Configurar la animación de rotación en la rueda
        RotateTransition animacionCargando = new RotateTransition(Duration.seconds(1), rueda);
        animacionCargando.setByAngle(360);
        animacionCargando.setCycleCount(RotateTransition.INDEFINITE);
        animacionCargando.play();

        // Asegurar que el círculo se centre
        StackPane.setAlignment(rueda, Pos.CENTER);

        // Agregar el círculo al diseño FXML dentro de un StackPane
        StackPane rootCompleto = new StackPane(rootCarga, rueda);
        return new Scene(rootCompleto);  // Ajusta el tamaño según necesites
    }


    // Metodo para cerrar una venta segun un nodo dado
    public static void cerrarVentana(Node context) {
        Stage stage = (Stage) ((Node) context).getScene().getWindow();
        stage.close();
    }

    // Metodo para limpiar campos de texto
    public static void limpiarCampos(TextField... campoDeTexto) {
        for (TextField texto : campoDeTexto) {
            texto.setText("");
            texto.setPromptText("");
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
    public static void cambiarPantalla(Pane primario, double duracion, Pane... secundarios) {
        // Verificación simple para evitar errores
        if (primario == null || secundarios == null) {
            Seguimiento.registrarLog(3, "No se pudo cambiar de pantalla, algun panel es nulo.");
        } else {
            for (Pane secundario : secundarios) {
                secundario.setVisible(false);
                fadeOut(secundario, duracion);
            }
            primario.setVisible(true);
            fadeIn(primario, duracion);
        }

    }

    // Animaciones
    public static void fadeOut(Node node, double duracion) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duracion), node);
        fadeTransition.setFromValue(1.0); // Opacidad inicial
        fadeTransition.setToValue(0.0);    // Opacidad final
        fadeTransition.play();
    }

    public static void fadeIn(Node node, double duracion) {
        node.setOpacity(0.0); // Asegúrate de que el nodo esté completamente invisible antes de iniciar
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duracion), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

}