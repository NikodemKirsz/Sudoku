package pl.first.firstjava;

import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver {
    private final Random random;
    private int[][] board;
    private int boardSize;
    private int boxSize;

    public BacktrackingSudokuSolver() {
        random = new Random();
    }

    public void solve(SudokuBoard sudokuBoard) {
        board = sudokuBoard.getBoard();
        boardSize = sudokuBoard.getBoardSize();
        boxSize = sudokuBoard.getBoxSize();

        fillBoard();
    }

    // Board Generator
    public void fillBoard() {
        // Fill the diagonal of 3 x 3 matrices
        fillDiagonal();

        // Fill remaining blocks.
        fillRemaining(0, boxSize);
    }

    // Fill the diagonal SRN number of SRN x SRN matrices.
    private void fillDiagonal() {
        for (int i = 0; i < boardSize; i += boxSize) {
            fillBox(i, i);
        }
    }

    // Fill indicated matrix.
    private void fillBox(int row, int column) {
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                int num;
                do {
                    num = getRandomInt();
                } while (!unUsedInBox(row, column, num));

                board[row + i][column + j] = num;
            }
        }
    }

    // A recursive function to fill remaining matrix
    private boolean fillRemaining(int row, int column) {

        // Look out for index out of bounds! (╯ ͠° ͟ʖ ͡°)╯
        if (column >= boardSize) {
            row += 1;
            column = 0;
        }

        // Search for en empty cell
        while (board[row][column] != 0) {
            column += 1;
            if (column >= boardSize) {
                row += 1;
                column = 0;
            }
            if (row >= boardSize) {
                return true;
            }
        }

        for (int num = 1; num <= boardSize; num++) {
            if (isValid(row, column, num)) {
                board[row][column] = num;
                // Go to the next cell in a row
                if (fillRemaining(row, column + 1)) {
                    return true;
                }
                board[row][column] = 0;
            }
        }
        return false; // triggers backtracking
    }

    // Check if safe to put in cell
    public boolean isValid(int i, int j, int num) {
        return (unUsedInRow(i, num)
                && unUsedInColumn(j, num)
                && unUsedInBox(i - i % boxSize, j - j % boxSize, num));
    }

    // by doing [i - i % SRN][j - j % SRN] we are always getting first cell of matrix

    // check in the row for existence
    private boolean unUsedInRow(int row, int num) {
        for (int column = 0; column < boardSize; column++) {
            if (board[row][column] == num) {
                return false;
            }
        }
        return true;
    }

    // check in the row for existence
    private boolean unUsedInColumn(int column, int num) {
        for (int row = 0; row < boardSize; row++) {
            if (board[row][column] == num) {
                return false;
            }
        }
        return true;
    }

    // Returns false if given 3 x 3 block contains num.
    private boolean unUsedInBox(int rowStart, int columnStart, int num) {
        for (int row = 0; row < boxSize; row++) {
            for (int column = 0; column < boxSize; column++) {
                if (board[rowStart + row][columnStart + column] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    // Random generator
    private int getRandomInt() {
        return getRandomInt(boardSize);
    }

    private int getRandomInt(int max) {
        return getRandomInt(max, 1);
    }

    private int getRandomInt(int max, int min) {
        return Math.abs(random.nextInt() % max + min);
    }
}
