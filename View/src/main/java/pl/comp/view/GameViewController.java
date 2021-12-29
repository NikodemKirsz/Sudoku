package pl.comp.view;

import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.util.converter.NumberStringConverter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.comp.model.SudokuBoard;
import pl.comp.model.SudokuPlayer;

public class GameViewController implements Initializable {

    private SudokuPlayer player;
    private SudokuBoard sudokuBoard;

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

    public GameViewController() {
        this.activeY = -1;
        this.activeX = -1;

        this.font = new Font("System", 48);
        this.activeFont = new Font("System", 51);

        this.dropShadow = new DropShadow();
        this.dropShadow.setRadius(5.0);
        this.dropShadow.setOffsetX(3.0);
        this.dropShadow.setOffsetY(3.0);
        this.dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
    }

    private void createNewSudoku() {
        player = new SudokuPlayer();
        sudokuBoard = new SudokuBoard(player);

        //Ten poziom trudności trzeba będzie gdzięs indziej zapamiętać
        sudokuBoard.generateSudokuPuzzle(MenuViewController.level);

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
        gridLabels[row][column].setText("");
        gridLabels[row][column].setTextFill(Color.RED);

        // setting onAction for every label
        gridLabels[row][column].onMouseClickedProperty()
                .set((EventHandler<MouseEvent>) (MouseEvent t) -> {
            this.activeX = row;
            this.activeY = column;
            this.clearFocus();
        });

        gridLabels[row][column].textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue,
                                String s, String t1) {
                if (sudokuBoard.isBoardValid()) {
                    winLabel.setVisible(true);
                } else {
                    winLabel.setVisible(false);
                }
            }
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
                    gridLabels[row][column].setStyle("-fx-border-color: grey;\n"
                                                    + "-fx-border-width: 1;\n"
                                                    + "-fx-border-style: solid;\n");
                }
            }
        }
    }

    private void updateSudokuBoard(int number) {
        if (this.activeY != -1 && this.activeX != -1) {
            gridLabels[activeX][activeY].setText(String.valueOf(number));
//            sudokuBoard.printBoard();
        }
    }

    private void setSudokuGrid(SudokuBoard sudokuBoard) {
        NumberStringConverter converter = new NumberStringConverter();
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                try {
                    JavaBeanIntegerPropertyBuilder builder = JavaBeanIntegerPropertyBuilder.create();
                    JavaBeanIntegerProperty
                            integerProperty = builder
                            .bean(sudokuBoard.getField(row, column))
                            .name("fieldValue")
                            .build();
                    gridLabels[row][column].textProperty().bindBidirectional(integerProperty, converter);

                    if (sudokuBoard.get(row, column) == 0) {
                        this.setModifiableLabel(row, column);
                    }

                } catch (NoSuchMethodException e) {
                    Logger.getLogger(GameViewController.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createNewSudoku();
    }
}