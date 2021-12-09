package pl.comp.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.comp.model.DifficultyLevel;

public class MenuViewController {

    public static DifficultyLevel level;
    private RadioButton[] radioButtons;

    @FXML private AnchorPane parentPane;
    @FXML private Label welcomeText;
    @FXML private Button playButton;
    @FXML private RadioButton easyButton;
    @FXML private RadioButton normalButton;
    @FXML private RadioButton hardButton;

    @FXML
    private void initialize() {
        welcomeText.setText("Witaj w grze Sudoku!");

        radioButtons = new RadioButton[] {
                easyButton,
                normalButton,
                hardButton
            };
        onEasyButtonClick();
    }

    @FXML
    private void onPlayButtonClicked(ActionEvent e) {
        Stage stage = MainApp.getCurrentStage();
        SceneController.loadGameScene(stage);
    }

    @FXML
    private void onEasyButtonClick() {
        level = DifficultyLevel.Easy;
        turnDifficultyButtonsOff(easyButton);
    }

    @FXML
    private void onNormalButtonClick() {
        level = DifficultyLevel.Normal;
        turnDifficultyButtonsOff(normalButton);
    }

    @FXML
    private void onHardButtonClick() {
        level = DifficultyLevel.Hard;
        turnDifficultyButtonsOff(hardButton);
    }

    private void turnDifficultyButtonsOff(RadioButton clickedButton) {
        for (var rb : radioButtons) {
            rb.setSelected(rb == clickedButton);
        }
    }
}