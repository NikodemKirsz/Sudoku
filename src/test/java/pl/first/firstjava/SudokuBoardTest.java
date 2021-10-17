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
    public void unUsedInRowTest() {
        int N = 9;
        int[][] board;
        board = new int[N][N];
        // TODO
    }

}
