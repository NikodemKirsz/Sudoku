package pl.comp.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.converter.NumberStringConverter;
import pl.comp.exceptions.DaoException;
import pl.comp.exceptions.IllegalBoardValueException;
import pl.comp.exceptions.SudokuException;
import pl.comp.model.*;

public class GameViewController implements Initializable {

    private SudokuPlayer player;
    private SudokuBoard sudokuBoard;
    private SudokuBoard originalSudokuBoard;
    private FileSudokuBoardDao boardDao;
    private FileSudokuBoardDao originalBoardDao;

    private JavaBeanIntegerProperty[][] integerProperty = new JavaBeanIntegerProperty[9][9];

    private static final Logger logger = LoggerFactory.getLogger(GameViewController.class);

    private Label[][] gridLabels;
    private int activeX;
    private int activeY;

    private final Font font;
    private final Font activeFont;
    private final DropShadow dropShadow;

    @FXML
    private GridPane sudokuGrid;
    @FXML
    private Button oneButton;
    @FXML
    private Button twoButton;
    @FXML
    private Button threeButton;
    @FXML
    private Button fourButton;
    @FXML
    private Button fiveButton;
    @FXML
    private Button sixButton;
    @FXML
    private Button sevenButton;
    @FXML
    private Button eightButton;
    @FXML
    private Button nineButton;
    @FXML
    private Label winLabel;
    @FXML
    private Button readButton;

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
    private void saveSudokuBoard() {
        boardDao.write(sudokuBoard);
        originalBoardDao.write(originalSudokuBoard);
        readButton.setDisable(false);
    }

    @FXML
    private void readSudokuBoard() {
        sudokuBoard = boardDao.read();
        originalSudokuBoard = originalBoardDao.read();
        this.setSudokuGrid(sudokuBoard);

        this.activeY = -1;
        this.activeX = -1;
        this.clearFocus();
    }

    public GameViewController() {
        this.activeY = -1;
        this.activeX = -1;

        this.boardDao = (FileSudokuBoardDao) SudokuBoardDaoFactory
                .getFileDao(FilesManager.SUDOKU_BOARD_PATH);
        this.originalBoardDao = (FileSudokuBoardDao) SudokuBoardDaoFactory
                .getFileDao(FilesManager.SUDOKU_BOARD_ORIGINAL_PATH);

        this.font = new Font("System", 48);
        this.activeFont = new Font("System", 51);

        this.dropShadow = new DropShadow();
        this.dropShadow.setRadius(5.0);
        this.dropShadow.setOffsetX(3.0);
        this.dropShadow.setOffsetY(3.0);
        this.dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
    }

    private void createNewSudoku() throws CloneNotSupportedException {
        player = new SudokuPlayer();
        sudokuBoard = new SudokuBoard(player);

        //Ten poziom trudności trzeba będzie gdzięs indziej zapamiętać
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

        gridLabels[row][column].textProperty().addListener((observableValue, s, t1) -> {
            winLabel.setVisible(sudokuBoard.isBoardValid());
        });
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
                    logger.error(e.getLocalizedMessage());
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            createNewSudoku();
        } catch (CloneNotSupportedException e) {
            logger.error(e.getLocalizedMessage());
        }

        File savedBoard = new File(FilesManager.SUDOKU_BOARD_PATH);
        if (!savedBoard.exists()) {
            readButton.setDisable(true);
        }
    }
}