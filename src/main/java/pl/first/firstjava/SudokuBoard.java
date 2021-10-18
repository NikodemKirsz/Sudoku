package pl.first.firstjava;

import java.lang.Math;
import java.lang.System;
import java.util.Random;

public class SudokuBoard {
    private final int[][] board;
    private final int boardSize;
    private final int boxSize; // square root of N
    private final Random random;
    // We make private field for class objects
    // implementing Interface Sudoku Solver.
    private final SudokuSolver sudokuSolver;

    // Constructor
    SudokuBoard() {
        this.boardSize = 9;
        this.boxSize = 3;
        this.random = new Random();
        this.board = new int[boardSize][boardSize];
        // We assign a proper class instance to sudokuSolver
        sudokuSolver = new BacktrackingSudokuSolver();
    }

    SudokuBoard(int[][] sudokuBoard) {
        this.boardSize = 9;
        this.boxSize = 3;
        this.random = new Random();
        this.board = sudokuBoard;
        for (int row = 0; row < boardSize; row++) {
            System.arraycopy(sudokuBoard[row], 0, this.board[row], 0, boardSize);
        }
        sudokuSolver = new BacktrackingSudokuSolver();
    }

    private void solveGame() {
        // We call a method solve
        sudokuSolver.solve(this);
    }


    public int[][] getBoard() {
        int[][] boardCopy;
        boardCopy = new int[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            System.arraycopy(this.board[row], 0, boardCopy[row], 0, boardSize);
        }
        return boardCopy;
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
            // Fill only diagonals (row==column).
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

        // Keep in mind that diagonal matrices are filled!

        // When we reach the end of the row
        // go to the next row and start from the beginning.
        if (column >= boardSize) {
            row += 1;
            column = 0;
        }

        // When we are in the first row of matrices in column
        // and j-index belongs to first matrix
        // go to the next matrix in a row
        if (row < boxSize) {
            if (column < boxSize) {
                column = boxSize;
            }
        // When we are between first and last row of matrices in column
        } else if (row < boardSize - boxSize) {
            // Check if we are in diagonal matrix
            // if so go to the next
            if (column == boxSize) {
                column += boxSize;
            }
        // When we are in the last row of matrices in column
        } else {
            // When we are in the last matrix in last row.
            // go to next row and start over
            if (column == boardSize - boxSize) {
                row += 1;
                column = 0;
                // beyond the board
                if (row >= boardSize) {
                    return true;
                }
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

    // Print board
    public void printBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}
