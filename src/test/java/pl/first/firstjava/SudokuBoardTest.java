package pl.first.firstjava;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SudokuBoardTest {

    public SudokuBoardTest() {
    }

    @Test
    public void printBoardOut() {
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.fillBoard();
        sudoku.getBoard()[0][0] = 0;
        sudoku.getBoard()[1][1] = 0;
        sudoku.printBoard();
        assertEquals(1,1);
    }

    @Test
    public void differentBoardsTest() {
        int N = 9;
        SudokuBoard board1 = new SudokuBoard();
        board1.fillBoard();
        SudokuBoard board2 = new SudokuBoard();
        board2.fillBoard();

        boolean different_boards = false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(!(board1.getBoard()[i][j] == board2.getBoard()[i][j])) {
                    different_boards = true;
                    break;
                }
            }
            if(different_boards) {
                break;
            }
        }
        assertTrue(different_boards);
    }

    @Test
    public void getBoardTest() {
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.fillBoard();
        sudoku.getBoard()[0][0] = 0;
        assertNotEquals(sudoku.getBoard()[0][0],0);
    }

    @Test
    public void isValidTest() {
        final int N = 9;

        int[][] validBoard;
        validBoard = new int[][]
                {
                    {5, 6, 1, 2, 3, 8, 4, 9, 7},
                    {8, 7, 4, 5, 6, 9, 2, 3, 1},
                    {2, 3, 9, 4, 7, 1, 6, 5, 8},
                    {1, 5, 8, 3, 2, 7, 9, 4, 6},
                    {3, 4, 7, 9, 5, 6, 8, 1, 2},
                    {9, 2, 6, 1, 8, 4, 3, 7, 5},
                    {6, 8, 3, 7, 9, 5, 1, 2, 4},
                    {4, 9, 5, 6, 1, 2, 7, 8, 3},
                    {7, 1, 2, 8, 4, 3, 5, 6, 9}
                };
        SudokuBoard validSudoku = new SudokuBoard(validBoard);
        boolean isSudokuValid = true;
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                int validNumber = validBoard[row][column];
                validBoard[row][column] = 0;
                isSudokuValid &= validSudoku.isValid(row, column, validNumber);
                validBoard[row][column] = validNumber;
            }
        }
        assertTrue(isSudokuValid);

        int[][] invalidBoard;
        invalidBoard = new int[][]
                {
                        {5, 6, 1, 2, 3, 8, 4, 9, 7},
                        {8, 7, 4, 5, 6, 9, 2, 3, 1},
                        {2, 3, 9, 4, 7, 1, 6, 5, 8},
                        {1, 5, 8, 3, 2, 7, 9, 4, 6},
                        {3, 4, 7, 9, 7, 6, 8, 1, 2},
                        {9, 2, 6, 1, 8, 4, 3, 7, 5},
                        {6, 8, 3, 7, 9, 5, 1, 2, 4},
                        {4, 9, 5, 6, 1, 2, 7, 8, 3},
                        {7, 1, 2, 8, 4, 3, 5, 6, 9}
                };
        SudokuBoard invalidSudoku = new SudokuBoard(invalidBoard);
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                int validNumber = invalidBoard[row][column];
                invalidBoard[row][column] = 0;
                isSudokuValid &= invalidSudoku.isValid(row, column, validNumber);
                invalidBoard[row][column] = validNumber;
            }
        }
        assertFalse(isSudokuValid);
    }
}
