package pl.comp.model;

import org.junit.jupiter.api.Test;
import pl.comp.exceptions.IllegalBoardValueException;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {
    private Repository repo;
    private int size = 9;

    @Test
    void createInstance() throws CloneNotSupportedException, IllegalBoardValueException {
        SudokuBoard sudokuBoard = new SudokuBoard();
        sudokuBoard.solveGame();
        repo = new Repository(sudokuBoard);

        var clonedSudokuBoard = repo.createInstance();
        SudokuField[][] board = sudokuBoard.getBoard();
        SudokuField[][] boardCloned = clonedSudokuBoard.getBoard();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                assertEquals(board[i][j].getFieldValue(), boardCloned[i][j].getFieldValue());
            }
        }
    }
}