package pl.first.firstjava;

import java.lang.Math;
import java.lang.System;
import java.util.Random;

public class SudokuBoard {
    private final SudokuField[][] sudokuFields;
    private final int boardSize;
    private final int boxSize; // square root of N
    private final Random random;
    private final SudokuSolver sudokuSolver;

    SudokuBoard() {
        this(new BacktrackingSudokuSolver());
    }

    SudokuBoard(SudokuSolver sudokuSolver) {
        this.boardSize = 9;
        this.boxSize = 3;
        this.random = new Random();
        this.sudokuFields = new SudokuField[boardSize][boardSize];
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                this.sudokuFields[x][y] = new SudokuField();
            }
        }
        this.sudokuSolver = sudokuSolver;
        this.fillDiagonal();
    }

    SudokuBoard(int[][] givenBoard) {
        this();
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                this.sudokuFields[row][column].setFieldValue(givenBoard[row][column]);
            }
        }
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public int get(int x, int y) {
        return this.sudokuFields[x][y].getFieldValue();
    }

    public void set(int x, int y, int value) {
        this.sudokuFields[x][y].setFieldValue(value);
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    public int getBoxSize() {
        return this.boxSize;
    }

    public SudokuField[][] getBoard() {
        SudokuField[][] boardCopy;
        boardCopy = new SudokuField[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            // TODO idk if this works
            System.arraycopy(this.sudokuFields[row], 0, boardCopy[row], 0, boardSize);
        }
        return boardCopy;
    }

    public SudokuRow getRow(int row) {
        SudokuField[] sudokuFields = new SudokuField[boardSize];
        for (int j = 0; j < boardSize; j++) {
            sudokuFields[j] = new SudokuField();
        }
        SudokuRow sudokuRow = new SudokuRow();
        for (int i = 0; i < boardSize; i++) {
            sudokuFields[i].setFieldValue(this.sudokuFields[row][i].getFieldValue());
        }
        sudokuRow.setSudokuFields(sudokuFields);
        return sudokuRow;
    }

    public SudokuColumn getColumn(int column) {
        SudokuField[] sudokuFields = new SudokuField[boardSize];
        for (int j = 0; j < boardSize; j++) {
            sudokuFields[j] = new SudokuField();
        }
        SudokuColumn sudokuColumn = new SudokuColumn();
        for (int i = 0; i < boardSize; i++) {
            sudokuFields[i].setFieldValue(this.sudokuFields[i][column].getFieldValue());
        }
        sudokuColumn.setSudokuFields(sudokuFields);
        return sudokuColumn;
    }

    public SudokuBox getBox(int x, int y) {
        x = x - (x % 3);
        y = y - (y % 3);
        int i = 0;
        SudokuField[] sudokuFields = new SudokuField[boardSize];
        for (int j = 0; j < boardSize; j++) {
            sudokuFields[j] = new SudokuField();
        }
        SudokuBox sudokuBox = new SudokuBox();
        for (int row = 0; row < boxSize; row++) {
            for (int column = 0; column < boxSize; column++) {
                sudokuFields[i].setFieldValue(
                        this.sudokuFields[x + row][y + column].getFieldValue());
                i++;
                // todo: implement safeguard for overflow
            }
        }
        sudokuBox.setSudokuFields(sudokuFields);
        return sudokuBox;
    }

    // Board Generator
    // Fill the diagonal boxSize number of boxSize x boxSize matrices.
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
                this.sudokuFields[row + i][column + j].setFieldValue(num);
            }
        }
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

    private boolean checkBoard() {
        boolean isValid = true;
        for (int i = 0; i < boardSize; i++) {
            isValid &= getRow(i).verify();
            isValid &= getColumn(i).verify();
            for (int j = 0; j < boardSize; j++) {
                isValid &= getBox(i, j).verify();
            }
        }
        return isValid;
    }

    // Checking whole board
    public boolean isBoardValid() {
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (!(isValid(row, column, this.sudokuFields[row][column].getFieldValue()))) {
                    return false;
                }
            }
        }
        return true;
    }

    // Check if safe to put in cell
    private boolean isValid(int i, int j, int num) {
        int temp = this.sudokuFields[i][j].getFieldValue();
        this.sudokuFields[i][j].setFieldValue(-1);
        boolean isUnused = (unUsedInRow(i, num)
                && unUsedInColumn(j, num)
                && unUsedInBox(i - i % boxSize, j - j % boxSize, num));
        this.sudokuFields[i][j].setFieldValue(temp);
        return isUnused;
    }

    // by doing [i - i % SRN][j - j % SRN] we are always getting first cell of matrix

    // check in the row for existence
    private boolean unUsedInRow(int row, int num) {
        for (int column = 0; column < boardSize; column++) {
            if (this.sudokuFields[row][column].getFieldValue() == num) {
                return false;
            }
        }
        return true;
    }

    // check in the row for existence
    private boolean unUsedInColumn(int column, int num) {
        for (int row = 0; row < boardSize; row++) {
            if (this.sudokuFields[row][column].getFieldValue() == num) {
                return false;
            }
        }
        return true;
    }

    // Returns false if given 3 x 3 block contains num.
    private boolean unUsedInBox(int rowStart, int columnStart, int num) {
        for (int row = 0; row < boxSize; row++) {
            for (int column = 0; column < boxSize; column++) {
                if (this.sudokuFields[rowStart + row][columnStart + column]
                        .getFieldValue() == num) {
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
                System.out.print(this.sudokuFields[i][j].getFieldValue() + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}
