package pl.comp.view;

import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.comp.model.DifficultyLevel;
import pl.comp.model.FilesManager;

public class MenuViewController implements Initializable, LocaleChangeListener {

    public static DifficultyLevel level;
    private RadioButton[] radioButtons;

    ObservableList<String> languages;

    @FXML private AnchorPane parentPane;
    @FXML private Label welcomeText;
    @FXML private Button playButton;
    @FXML private RadioButton easyButton;
    @FXML private RadioButton normalButton;
    @FXML private RadioButton hardButton;
    @FXML private ChoiceBox<String> languageChoice;
    @FXML private Label choiceLabel;

    @FXML private Label authorsLabel;
    @FXML private Label firstAuthor;
    @FXML private Label secondAuthor;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioButtons = new RadioButton[] {
                easyButton,
                normalButton,
                hardButton
        };
        onEasyButtonClick();

        var authorsResource = ResourceBundle.getBundle("pl.comp.view.AuthorsBundle");
        authorsLabel.setText(authorsResource.getString("title"));
        firstAuthor.setText(authorsResource.getString("first_author"));
        secondAuthor.setText(authorsResource.getString("second_author"));

        languages = FXCollections.observableArrayList(
                resourceBundle.getString("english"),
                resourceBundle.getString("polish")
        );
        languageChoice.setItems(languages);
        languageChoice.setValue(Objects.equals(String.valueOf(Locale.getDefault()), "pl_PL")
                ? resourceBundle.getString("polish")
                : resourceBundle.getString("english"));
        languageChoice
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {

            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number number,
                    Number number2) {
                // nie wiem czemu to działa na odwrót
                if (Objects.equals(
                        languageChoice.getValue(),
                        resourceBundle.getString("english"))) {
                    Locale.setDefault(new Locale("pl", "PL"));
                } else {
                    Locale.setDefault(new Locale("en", "US"));
                }
                onLocaleChange();
            }
        });

        FilesManager.ensureFilesDirExists();
    }

    @Override
    public void onLocaleChange() {
        ResourceBundle resourceBundle = ResourceBundleManager.changeLanguage(Locale.getDefault());
        welcomeText.setText(resourceBundle.getString("welcomeText"));
        playButton.setText(resourceBundle.getString("playButton"));
        easyButton.setText(resourceBundle.getString("easy"));
        normalButton.setText(resourceBundle.getString("medium"));
        hardButton.setText(resourceBundle.getString("hard"));
        choiceLabel.setText(resourceBundle.getString("choiceLabel"));
        var authorsResource = ResourceBundle.getBundle("pl.comp.view.AuthorsBundle");
        authorsLabel.setText(authorsResource.getString("title"));
        firstAuthor.setText(authorsResource.getString("first_author"));
        secondAuthor.setText(authorsResource.getString("second_author"));
        // TODO: make choiceBox change it's language too
    }
}