package co.edu.uniquindio.icaja.utils.tools;

import co.edu.uniquindio.icaja.App;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ViewTools {

    /**
     * Muestra un mensaje para dar información del usuario
     *
     * @param title   El título de la ventana.
     * @param header  Subtitulo de la ventana.
     * @param message Mensaje que describe la información a dar
     * @param type    El tipo de mensaje: Alert.Alertype.<Enumeracion>, donde Enumeracion puede ser:
     *                NONE,
     *                INFORMATION,
     *                WARNING,
     *                CONFIRMATION,
     *                ERROR;
     */
    public static void mostrarMensaje(String title, String header, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static Scene cargarEscena(String url, String... styles) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(url));
            Scene scene = new Scene(fxmlLoader.load());
            for (String style : styles) {
                scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource(style)).toExternalForm());
            }
            return scene;
        } catch (IOException e) {
            //Seguimiento.registrarLog(3, "No se pudo cargar la escena, No se encontró el recurso '" + url + "', error: " + e.getMessage());
            e.printStackTrace();
           // mostrarMensaje("Lo sentimos", "¡Ha ocurrido un error!", "La ventana no pudo cargar de forma adecuada, comunicate con atención tecnica", Alert.AlertType.ERROR);
            return new Scene(new Pane(), 600, 400);
        } catch (Exception e) {
            Seguimiento.registrarLog(3, "No se pudo cargar la escena, error: " + e.getMessage());
            return new Scene(new Pane(), 600, 400);
        }
    }

    /**
     * Genera una ventana con una escena de carga y luego carga la escena principal en segundo plano.
     * Muestra una animación de desvanecimiento (fade) durante la transición entre la escena de carga y la escena principal.
     *
     * @param url      Ruta del archivo FXML para la escena principal.
     * @param title    Título de la ventana para la escena principal.
     * @param urlCarga Ruta del archivo FXML de la escena de carga.
     * @param styles   Opcional. Las rutas a los archivos CSS que se deben aplicar a ambas escenas.
     */
    public static void generarVentana(String url, String title, String urlCarga, String... styles) {
        Stage stage = new Stage();

        if (urlCarga != null && !urlCarga.isEmpty()) {
            Scene escenaCarga = generarEscenaCarga(urlCarga, styles);
            stage.setScene(escenaCarga);
            stage.setTitle("Cargando...");
            stage.show();
            fadeIn(escenaCarga.getRoot(), 0.5);
        }

        Task<Scene> cargarEscenaTask = new Task<>() {
            @Override
            protected Scene call() {
                return cargarEscena(url, styles);
            }

            @Override
            protected void succeeded() {
                if (urlCarga != null && !urlCarga.isEmpty()) {
                    fadeOut(stage.getScene().getRoot(), 0.25);

                    stage.getScene().getRoot().opacityProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue.doubleValue() == 0.0) {
                            stage.setScene(getValue());
                            stage.setTitle(title);
                            fadeIn(getValue().getRoot(), 0.25);
                        }
                    });
                } else {
                    stage.setScene(getValue());
                    stage.setTitle(title);
                    fadeIn(getValue().getRoot(), 0.5);
                    stage.show();
                }
            }
        };

        new Thread(cargarEscenaTask).start();
    }


    /**
     * Cierra la ventana actual a partir de un nodo de contexto.
     *
     * @param context Nodo dentro de la ventana que se desea cerrar.
     */
    public static void cerrarVentana(Node context) {
        Stage stage = (Stage) (context).getScene().getWindow();
        stage.close();
    }


    /**
     * Genera una escena de carga con una animación de rueda giratoria.
     *
     * @param rutaFXML La ruta del archivo FXML que define la escena de carga.
     * @param styles   Opcional. Las rutas a los archivos de estilo CSS para aplicar a la escena.
     * @return Una escena que muestra un indicador de carga.
     */
    public static Scene generarEscenaCarga(String rutaFXML, String... styles) {
        Scene escenaCarga = cargarEscena(rutaFXML, styles);

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

        // Revisar si el root es un StackPane
        Pane root = (Pane) escenaCarga.getRoot();
        StackPane rootCompleto = new StackPane(root, rueda);
        return new Scene(rootCompleto, root.getPrefWidth(), root.getPrefHeight());
    }


    /**
     * Limpia el texto y el texto de sugerencia (prompt) de uno o más campos de texto.
     *
     * @param campoDeTexto Los campos de texto que se desean limpiar.
     */
    public static void limpiarCampos(TextInputControl... campoDeTexto) {
        for (TextInputControl texto : campoDeTexto) {
            if (texto != null) {
                texto.setText("");
                texto.setPromptText("");
            }
        }
    }


    /**
     * Verifica si alguno de los campos de texto proporcionados está vacío.
     *
     * @param camposDeTexto Texto de los campos a verificar.
     * @return true si todos los campos contienen texto; false si alguno está vacío.
     */
    public static boolean NoHayCamposVacios(String... camposDeTexto) {
        for (String texto : camposDeTexto) {
            if (texto.isEmpty() || texto.equals(" ")) {
                return false;
            }
        }
        return true;
    }


    /**
     * Cambia la visibilidad entre paneles en una misma ventana.
     *
     * @param primario    El panel que se debe hacer visible.
     * @param duracion    Duración en segundos de la transición de desvanecimiento.
     * @param secundarios Los paneles que se deben ocultar.
     */
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

    /**
     * Cambia los colores de los botones primario y secundarios con un efecto de desvanecimiento.
     *
     * @param primario El botón principal al que se le aplica el color destacado.
     * @param claseCSS La clase CSS que define el color del botón primario.
     * @param secundarios Los botones secundarios a los que se les quita la clase CSS y se les aplica el efecto de desvanecimiento.
     */
    public static void cambiarColores(Button primario, String claseCSS, Button... secundarios) {
        if (primario == null || secundarios == null) {
            Seguimiento.registrarLog(3, "No se pudo cambiar de pantalla, algun panel es nulo.");
        } else {
            for (Button secundario : secundarios) {
                secundario.getStyleClass().remove(claseCSS);
            }
            primario.getStyleClass().add(claseCSS);

        }
    }



    /**
     * Aplica una animación de desvanecimiento gradual a un nodo, haciéndolo desaparecer.
     *
     * @param node     Nodo al cual aplicar la animación.
     * @param duracion Duración en segundos de la animación.
     */
    public static void fadeOut(Node node, double duracion) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duracion), node);
        fadeTransition.setFromValue(1.0); // Opacidad inicial
        fadeTransition.setToValue(0.0);    // Opacidad final
        fadeTransition.play();
    }


    /**
     * Aplica una animación de desvanecimiento gradual a un nodo, haciéndolo aparecer.
     *
     * @param node     Nodo al cual aplicar la animación.
     * @param duracion Duración en segundos de la animación.
     */
    public static void fadeIn(Node node, double duracion) {
        node.setOpacity(0.0); // Asegúrate de que el nodo esté completamente invisible antes de iniciar
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duracion), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }


    /**
     * Actualiza los elementos de un ComboBox con los elementos de una lista observable.
     *
     * @param <T> Tipo de los elementos de la lista observable.
     * @param comboBox El ComboBox a actualizar.
     * @param listaObservable Lista observable con los elementos a agregar.
     * @param mapper Función para mapear cada elemento a un String.
     */
    private static <T> void actualizarComboBox(MFXFilterComboBox<String> comboBox, ObservableList<T> listaObservable, Function<T, String> mapper) {
        Platform.runLater(() -> {
            comboBox.getItems().clear();

            // Convierte la lista observable en una lista de strings
            ObservableList<String> items = FXCollections.observableArrayList();
            for (T item : listaObservable) {
                items.add(mapper.apply(item));  // Convierte el elemento a String y lo agrega
            }

            comboBox.setItems(items);  // Establece la lista de items en el ComboBox
        });
    }



    /**
     * Inicializa el ComboBox conectándolo a una lista observable y actualizándolo automáticamente.
     *
     * @param <T> Tipo de los elementos de la lista observable.
     * @param comboBox El ComboBox a inicializar.
     * @param listaObservable La lista observable con los elementos que se agregarán al ComboBox.
     * @param mapper Función para mapear los elementos de la lista a un String para el ComboBox.
     */
    public static <T> void inicializarComboBox(MFXFilterComboBox<String> comboBox, ObservableList<T> listaObservable, Function<T, String> mapper) {
        // Listener para actualizar el ComboBox cuando la lista cambie
        listaObservable.addListener((ListChangeListener<? super T>) change -> {
            Platform.runLater(() -> actualizarComboBox(comboBox, listaObservable, mapper));
        });

        // Actualizar el ComboBox al inicio en el hilo de JavaFX
        Platform.runLater(() -> actualizarComboBox(comboBox, listaObservable, mapper));
    }

}