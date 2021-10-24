package pl.first.firstjava;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {

    @Test
    void solveSudokuTest() {
        final var sudokuBoards = new SudokuBoard[5];
        final SudokuSolver sudokuSolver;
        sudokuSolver = new BacktrackingSudokuSolver();

        boolean areAllValid = true;
        for(var sb : sudokuBoards) {
            var validSudokuBoard = new SudokuBoard();
            sudokuSolver.solve(validSudokuBoard);
            int[][] validBoard = validSudokuBoard.getBoard();

            sb = new SudokuBoard(validBoard);

            boolean isSudokuValid = true;
            if (!(sb.isBoardValid())) {
                isSudokuValid = false;
            }
            areAllValid &= isSudokuValid;
        }
        assertTrue(areAllValid);
    }

    @Test
    void fillBoardTest() {
    }

    @Test
    void isValidTest() {
        final int N = 9;

        // valid array test
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
        var validSudoku = new SudokuBoard(validBoard);
        BacktrackingSudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        sudokuSolver.solve(validSudoku);
        boolean isSudokuValid = true;
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                int validNumber = validBoard[row][column];
                if (!(sudokuSolver.isValid(row, column, validNumber))) {
                    isSudokuValid = false;
                    break;
                }
            }
            if (!(isSudokuValid)) {
                break;
            }
        }
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
                        {1, 1, 1, 2, 3, 8, 4, 9, 7},
                        {8, 7, 4, 5, 6, 9, 2, 3, 1},
                        {2, 3, 9, 4, 7, 1, 6, 5, 8},
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
            sudokuSolver.solve(invalidSudoku);
            isSudokuValid = true;
            for (int row = 0; row < N; row++) {
                for (int column = 0; column < N; column++) {
                    int validNumber = invalidBoard[row][column];
                    if (!(sudokuSolver.isValid(row, column, validNumber))) {
                        isSudokuValid = false;
                        break;
                    }
                }
                if (!(isSudokuValid)) {
                    break;
                }
            }
            assertFalse(isSudokuValid);
        }
    }
}