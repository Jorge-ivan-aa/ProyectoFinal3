package co.edu.uniquindio.icaja;

import atlantafx.base.theme.PrimerLight;
import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        UserAgentBuilder.builder()
                .themes(JavaFXThemes.MODENA)
                .themes(MaterialFXStylesheets.forAssemble(false))
                .build()
                .setGlobal();


        Scene scene = new Scene(fxmlLoader.load());
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/main.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/login.css")).toExternalForm());
        stage.setTitle("ICaja Wallet");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}