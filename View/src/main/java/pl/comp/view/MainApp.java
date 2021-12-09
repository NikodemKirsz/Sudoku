package pl.comp.view;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage currentStage;

    @Override
    public void start(Stage stage) throws IOException {
        currentStage = stage;

        URL sceneUrl = MainApp.class.getResource("MenuView.fxml");
        assert sceneUrl != null;
        Parent root = FXMLLoader.load(sceneUrl);
        Scene scene = new Scene(root);
        currentStage.setScene(scene);

        URL iconUrl = MainApp.class.getResource("icon.png");
        Image icon = new Image(String.valueOf(iconUrl));
        currentStage.getIcons().add(icon);

        currentStage.setResizable(false);
        currentStage.setTitle("Sudoku!");

        currentStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getCurrentStage() {
        return currentStage;
    }
}