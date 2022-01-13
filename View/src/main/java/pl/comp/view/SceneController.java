package pl.comp.view;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class SceneController {

    private static final Class<SceneController> thisClass = SceneController.class;

    private SceneController() {
    }

    public static void loadGameScene(Stage stage) {
        URL gameViewUrl = thisClass.getResource("GameView.fxml");
        try {
            loadScene(stage, gameViewUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadMenuScene(Stage stage) {
        URL menuViewUrl = thisClass.getResource("MenuView.fxml");
        try {
            loadScene(stage, menuViewUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadScene(Stage stage, URL viewUrl) throws IOException {
        var resourceBundle = ResourceBundle.getBundle("viewBundle");
        Parent root = FXMLLoader.load(viewUrl, resourceBundle);

        var scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
