package pl.comp.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.exceptions.ViewException;

public final class SceneController {

    private static final Class<SceneController> thisClass = SceneController.class;
    private static final Logger logger = LoggerFactory.getLogger(SceneController.class);
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("viewBundle");

    private SceneController() {
    }

    public static void loadGameScene(Stage stage) {
        URL gameViewUrl = thisClass.getResource("GameView.fxml");
        try {
            loadScene(stage, gameViewUrl);
        } catch (Exception e) {
            var exception = new ViewException(
                    resourceBundle.getString("viewException"), e);
            logger.error(exception + resourceBundle.getString("cause"), exception.getCause());
        }
    }

    public static void loadMenuScene(Stage stage) {
        URL menuViewUrl = thisClass.getResource("MenuView.fxml");
        try {
            loadScene(stage, menuViewUrl);
        } catch (Exception e) {
            var exception = new ViewException(
                    resourceBundle.getString("viewException"), e);
            logger.error(exception + resourceBundle.getString("cause"), exception.getCause());
        }
    }

    private static void loadScene(Stage stage, URL viewUrl) throws IOException {
        Parent root = FXMLLoader.load(viewUrl, resourceBundle);
        var scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
