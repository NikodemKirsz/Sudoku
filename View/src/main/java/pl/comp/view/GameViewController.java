package pl.comp.view;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import pl.comp.model.SudokuBoard;
import pl.comp.model.SudokuField;
import pl.comp.model.SudokuPlayer;

public class GameViewController {

    private SudokuPlayer player;

    private Label[][] gridLabels;
    @FXML private GridPane sudokuGrid;

    @FXML
    private void initialize() {
        createNewSudoku();
    }

    private void createNewSudoku() {
        player = new SudokuPlayer();
        SudokuBoard sb = new SudokuBoard(player);

        //Ten poziom trudności trzeba będzie gdzięs indziej zapamiętać
        sb.generateSudokuPuzzle(MenuViewController.level);

        initializeGrid();

        setSudokuGrid(sb.getBoard());
    }

    private void initializeGrid() {
        var font = new Font("System", 56);
        gridLabels = new Label[9][9];
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                var label = new Label();
                gridLabels[row][column] = label;
                label.setFont(font);
                label.setAlignment(Pos.CENTER);
                label.setTextAlignment(TextAlignment.CENTER);
                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.CENTER);
                sudokuGrid.add(label, column, row);
            }
        }
    }

    private void setSudokuGrid(SudokuField[][] sudokuBoard) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                int value = sudokuBoard[row][column].getFieldValue();
                String text = value == 0 ? "" : String.valueOf(value);
                gridLabels[row][column].setText(text);
            }
        }
    }


}