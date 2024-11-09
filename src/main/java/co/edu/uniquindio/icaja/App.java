package co.edu.uniquindio.icaja;

import atlantafx.base.theme.PrimerLight;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        UserAgentBuilder.builder()
                .themes(JavaFXThemes.MODENA)
                .themes(MaterialFXStylesheets.forAssemble(false))
                .build()
                .setGlobal();

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        Scene scene = ViewTools.cargarEscena("login.fxml", "styles/main.css", "styles/login.css");
        stage.setTitle("ICaja Wallet");
        stage.setScene(scene);
        stage.show();

        ViewTools.fadeIn(scene.getRoot(), 0.5);

    }

    public static void main(String[] args) {
        launch();
    }
}