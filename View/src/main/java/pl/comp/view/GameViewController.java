package pl.comp.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
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
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import pl.comp.model.SudokuBoard;
import pl.comp.model.SudokuField;
import pl.comp.model.SudokuPlayer;

import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable {

    private SudokuPlayer player;
    private SudokuBoard sb;

    private Label[][] gridLabels;
    private int activeX;
    private int activeY;

    private final Font font = new Font("System", 48);
    private final Font activeFont = new Font("System", 51);

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

    private void createNewSudoku() {
        player = new SudokuPlayer();
        sb = new SudokuBoard(player);

        //Ten poziom trudności trzeba będzie gdzięs indziej zapamiętać
        sb.generateSudokuPuzzle(MenuViewController.level);

        initializeGrid();

        setSudokuGrid(sb.getBoard());
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

                // setting onAction for every label
                int finalRow = row;
                int finalColumn = column;
                label.onMouseClickedProperty().set((EventHandler<MouseEvent>) (MouseEvent t) -> {
                    this.activeX = finalRow;
                    this.activeY = finalColumn;
                    this.clearFocus();
                });

                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.CENTER);
                sudokuGrid.add(label, column, row);
            }
        }
    }

    private void clearFocus() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                gridLabels[row][column].setEffect(null);
                gridLabels[row][column].setFont(font);
                gridLabels[row][column].setStyle(null);
                if (row == this.activeX && column == this.activeY) {

                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setRadius(5.0);
                    dropShadow.setOffsetX(3.0);
                    dropShadow.setOffsetY(3.0);
                    dropShadow.setColor(Color.color(0.4, 0.5, 0.5));

                    gridLabels[row][column].setEffect(dropShadow);
                    gridLabels[row][column].setFont(activeFont);
                    gridLabels[row][column].setStyle("-fx-border-color: grey;\n"
                                                    + "-fx-border-width: 1;\n"
                                                    + "-fx-border-style: solid;\n");
                }
            }
        }
    }

    private void updateSudokuBoard(int number) {
        if (this.activeY != -1 && this.activeX != -1) {
            StringConverter<Number> converter = new NumberStringConverter();
            IntegerProperty simpleIntegerProperty = new SimpleIntegerProperty(number);

            // wiązanie nie jest prawidłowe (coś z Modelem trzeba zrobić)
            sb.set(this.activeX, this.activeY, number);
            Bindings.bindBidirectional(gridLabels[activeX][activeY].textProperty(), simpleIntegerProperty, converter);
//            sb.printBoard();
        }
    }

    private void setSudokuGrid(SudokuField[][] sudokuBoard) {
        StringConverter<Number> converter = new NumberStringConverter();
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                int value = sb.get(row, column);
                if (value != 0) {
                    IntegerProperty simpleIntegerProperty = new SimpleIntegerProperty(sb.get(row, column));
                    Bindings.bindBidirectional(gridLabels[row][column].textProperty(), simpleIntegerProperty, converter);
                }
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.activeY = -1;
        this.activeX = -1;
        createNewSudoku();
    }
}