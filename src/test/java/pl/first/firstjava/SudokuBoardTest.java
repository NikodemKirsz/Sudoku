package pl.first.firstjava;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SudokuBoardTest {

    @Test
    void printBoardOut() {
        var sudoku = new SudokuBoard();
        sudoku.solveGame();
        sudoku.getBoard()[0][0].setFieldValue(0);
        sudoku.getBoard()[1][1].setFieldValue(0);
        sudoku.printBoard();
        assertEquals(1,1);
    }

    @Test
    void differentBoardsTest() {
        int N = 9;
        var board1 = new SudokuBoard();
        board1.solveGame();
        var board2 = new SudokuBoard();
        board2.solveGame();

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
    void getBoardTest() {
        var sudoku = new SudokuBoard();
        sudoku.getBoard()[0][0].setFieldValue(0);
        assertNotEquals(sudoku.getBoard()[0][0],0);
    }

    @Test
    void isValidTest() {
        int[][] validBoard;

        // valid board test
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
        var validSudoku = new SudokuBoard(validBoard);
        boolean isSudokuValid = validSudoku.isBoardValid();
        assertTrue(isSudokuValid);

        // invalid arrays test
        int[][] invalidBoardRow = new int[][]
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
        int[][] invalidBoardColumn = new int[][]
                {
                        {5, 1, 1, 2, 3, 8, 4, 9, 7},
                        {8, 7, 4, 5, 6, 9, 2, 3, 1},
                        {2, 3, 9, 4, 7, 1, 6, 5, 8},
                        {1, 5, 8, 3, 2, 7, 9, 4, 6},
                        {3, 4, 7, 9, 7, 6, 8, 1, 2},
                        {9, 2, 6, 1, 8, 4, 3, 7, 5},
                        {6, 8, 3, 7, 9, 5, 1, 2, 4},
                        {4, 9, 5, 6, 1, 2, 7, 8, 3},
                        {7, 1, 2, 8, 4, 3, 5, 6, 9}
                };
        int[][] invalidBoardBox = new int[][]
                {
                        {5, 6, 1, 2, 3, 8, 4, 9, 7},
                        {8, 7, 4, 5, 6, 9, 2, 3, 1},
                        {2, 3, 5, 4, 7, 1, 6, 5, 8},
                        {1, 5, 8, 3, 2, 7, 9, 4, 6},
                        {3, 4, 7, 9, 7, 6, 8, 1, 2},
                        {9, 2, 6, 1, 8, 4, 3, 7, 5},
                        {6, 8, 3, 7, 9, 5, 1, 2, 4},
                        {4, 9, 5, 6, 1, 2, 7, 8, 3},
                        {7, 1, 2, 8, 4, 3, 5, 6, 9}
                };

        int[][][] invalidBoards = {
                invalidBoardRow,
                invalidBoardColumn,
                invalidBoardBox
        };

        for (int[][] invalidBoard: invalidBoards) {
            var invalidSudoku = new SudokuBoard(invalidBoard);
            isSudokuValid &= invalidSudoku.isBoardValid();
            assertFalse(isSudokuValid);
        }
    }
}
