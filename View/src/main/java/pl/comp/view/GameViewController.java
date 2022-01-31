package pl.comp.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.exceptions.DatabaseException;
import pl.comp.exceptions.ViewException;
import pl.comp.model.FilesManager;
import pl.comp.model.JdbcSudokuBoardDao;
import pl.comp.model.SudokuBoard;
import pl.comp.model.SudokuBoardDaoFactory;
import pl.comp.model.SudokuPlayer;

public class GameViewController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(GameViewController.class);

    private Font font;
    private Font activeFont;
    private DropShadow dropShadow;
    private ResourceBundle resourceBundle;

    private final JavaBeanIntegerProperty[][] integerProperty = new JavaBeanIntegerProperty[9][9];
    private SudokuPlayer player;
    private SudokuBoard sudokuBoard;
    private SudokuBoard originalSudokuBoard;
    private JdbcSudokuBoardDao origJdbc;
    private JdbcSudokuBoardDao currJdbc;
    private Label[][] gridLabels;

    private int activeX;
    private int activeY;

    @FXML private GridPane sudokuGrid;
    @FXML private Label winLabel;
    @FXML private Button readButton;
    @FXML private Button saveButton;
    @FXML private ChoiceBox<String> saveChoice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.activeY = -1;
        this.activeX = -1;

        this.font = new Font("System", 48);
        this.activeFont = new Font("System", 51);

        this.dropShadow = new DropShadow();
        this.dropShadow.setRadius(5.0);
        this.dropShadow.setOffsetX(3.0);
        this.dropShadow.setOffsetY(3.0);
        this.dropShadow.setColor(Color.color(0.4, 0.5, 0.5));

        try {
            createNewSudoku();
        } catch (CloneNotSupportedException e) {
            logger.error(e.getLocalizedMessage());
        }

        readButton.setDisable(true);
        saveButton.setDisable(true);

        JdbcSudokuBoardDao.initialize();

        origJdbc = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getDatabaseDao(
                JdbcSudokuBoardDao.BoardType.ORIGINAL
        );

        currJdbc = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getDatabaseDao(
                JdbcSudokuBoardDao.BoardType.CURRENT
        );

        this.fillSavedChoiceBox();
    }

    @FXML
    private void handleButtonOne() {
        this.updateSudokuBoard(1);
    }

    @FXML
    private void handleButtonTwo() {
        this.updateSudokuBoard(2);
    }

    @FXML
    private void handleButtonThree() {
        this.updateSudokuBoard(3);
    }

    @FXML
    private void handleButtonFour() {
        this.updateSudokuBoard(4);
    }

    @FXML
    private void handleButtonFive() {
        this.updateSudokuBoard(5);
    }

    @FXML
    private void handleButtonSix() {
        this.updateSudokuBoard(6);
    }

    @FXML
    private void handleButtonSeven() {
        this.updateSudokuBoard(7);
    }

    @FXML
    private void handleButtonEight() {
        this.updateSudokuBoard(8);
    }

    @FXML
    private void handleButtonNine() {
        this.updateSudokuBoard(9);
    }

    @FXML
    private void saveChange() {
        saveButton.setDisable(false);
        readButton.setDisable(false);
    }

    @FXML
    private void saveSudokuBoard() {
        var activeIndex = getIntFromStringStart(saveChoice.getValue()) - 1;
        try {
            origJdbc.write(originalSudokuBoard, activeIndex);
            currJdbc.write(sudokuBoard, activeIndex);
        } catch (DatabaseException exception) {
            var e = new ViewException(
                    resourceBundle.getString("viewException"), exception);
            logger.error(e + resourceBundle.getString("cause"), e.getCause());
        }
        this.fillSavedChoiceBox();
        saveChoice.setValue(String.valueOf(activeIndex + 1));
    }

    @FXML
    private void readSudokuBoard() {
        String activeField = saveChoice.getValue();

        if (activeField.contains("empty")) {
            return;
        }

        var activeIndex = getIntFromStringStart(activeField) - 1;
        try {
            sudokuBoard = currJdbc.read(activeIndex);
            originalSudokuBoard = origJdbc.read(activeIndex);
        } catch (DatabaseException exception) {
            var e = new ViewException(
                    resourceBundle.getString("viewException"), exception);
            logger.error(e + resourceBundle.getString("cause"), e.getCause());
        }
        this.setSudokuGrid(sudokuBoard);

        this.activeY = -1;
        this.activeX = -1;
        this.clearFocus();
    }

    private void fillSavedChoiceBox() {
        ObservableList<String> list = saveChoice.getItems();
        list.clear();
        for (int i = 0; i < 5; i++) {
            int index = i + 1;
            /*if (jdbc.isRecordEmpty(i)) {
                list.add(index + " (empty)");
            } else {
                list.add(String.valueOf(index));
            }*/
            list.add(String.valueOf(index));
        }
        saveChoice.setItems(list);
    }

    private void createNewSudoku() throws CloneNotSupportedException {
        player = new SudokuPlayer();
        sudokuBoard = new SudokuBoard(player);

        sudokuBoard.generateSudokuPuzzle(MenuViewController.level);
        originalSudokuBoard = sudokuBoard.clone();

        initializeGrid();

        setSudokuGrid(sudokuBoard);
    }

    private void initializeGrid() {
        gridLabels = new Label[9][9];
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                var label = new Label();
                gridLabels[row][column] = label;
                label.setFont(font);
                label.setAlignment(Pos.CENTER);
                label.setTextAlignment(TextAlignment.CENTER);
                label.setPrefHeight(80);
                label.setPrefWidth(80);
                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.CENTER);
                sudokuGrid.add(label, column, row);
            }
        }
    }

    private void setModifiableLabel(int row, int column) {
        // setting modifiable labels
        if (sudokuBoard.get(row, column) == 0) {
            gridLabels[row][column].setText("");
        }
        gridLabels[row][column].setTextFill(Color.RED);

        // setting onAction for every label
        gridLabels[row][column].onMouseClickedProperty()
                .set((MouseEvent t) -> {
                    this.activeX = row;
                    this.activeY = column;
                    this.clearFocus();
                });

        gridLabels[row][column].textProperty().addListener(
                (observableValue, s, t1) -> winLabel.setVisible(sudokuBoard.isBoardValid())
        );
    }

    private void clearFocus() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                gridLabels[row][column].setEffect(null);
                gridLabels[row][column].setFont(font);
                gridLabels[row][column].setStyle(null);

                if (row == this.activeX && column == this.activeY) {
                    gridLabels[row][column].setEffect(dropShadow);
                    gridLabels[row][column].setFont(activeFont);
                    gridLabels[row][column].setStyle("""
                            -fx-border-color: grey;
                            -fx-border-width: 1;
                            -fx-border-style: solid;
                            """);
                }
            }
        }
    }

    private void updateSudokuBoard(int number) {
        if (this.activeY != -1 && this.activeX != -1) {
            gridLabels[activeX][activeY].setText(String.valueOf(number));
            logger.info("Label: " + gridLabels[activeX][activeY].textProperty()
                    + " SudokuBoardField: " + sudokuBoard.get(activeX, activeY));
        }
    }

    private void setSudokuGrid(SudokuBoard sudokuBoard) {
        NumberStringConverter converter = new NumberStringConverter();
        var builder = JavaBeanIntegerPropertyBuilder.create();
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                try {
                    integerProperty[row][column] = builder
                            .bean(sudokuBoard.getField(row, column))
                            .name("fieldValue")
                            .build();
                    Bindings.bindBidirectional(
                            gridLabels[row][column].textProperty(),
                            integerProperty[row][column],
                            converter
                    );

                    gridLabels[row][column].setTextFill(Color.BLACK);
                    gridLabels[row][column].onMouseClickedProperty().set(null);

                    // set field modifiable if it was empty in original board
                    if (originalSudokuBoard.get(row, column) == 0) {
                        this.setModifiableLabel(row, column);
                    }

                } catch (Exception e) {
                    var exception = new ViewException(
                            resourceBundle.getString("viewException"), e);
                    logger.error(exception + resourceBundle.getString(
                            "cause"), exception.getCause()
                    );
                }
            }
        }
    }

    private int getIntFromStringStart(String s) {
        if (s == null || s.isEmpty() || s.isBlank()) {
            return -1;
        }

        int endIndex = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                endIndex++;
            }
        }
        return Integer.parseInt(s.substring(0, endIndex));
    }
}