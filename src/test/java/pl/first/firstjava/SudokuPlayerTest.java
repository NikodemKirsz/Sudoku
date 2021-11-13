package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuPlayerTest {

    @Test
    void onValueChanged() {
        var sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), (IObserver)null);
        sudokuBoard.solveGame();

        sudokuBoard.set(0, 0, 0);
        sudokuBoard.set(0, 0, 4);
        sudokuBoard.set(0, 0, 11);

        var sudokuBoardWithObserver = new SudokuBoard();
        sudokuBoardWithObserver.solveGame();

        sudokuBoardWithObserver.set(0, 0, 0);
        sudokuBoardWithObserver.set(0, 0, 4);
        sudokuBoardWithObserver.set(0, 0, 11);
       }
}