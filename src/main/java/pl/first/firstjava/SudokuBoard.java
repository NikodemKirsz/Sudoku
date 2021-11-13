package pl.first.firstjava;

import java.lang.Math;
import java.lang.System;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuBoard implements IObservable {
    private final SudokuField[][] sudokuFields;
    private final int boardSize;
    private final int boxSize; // square root of N
    private final Random random;
    private final SudokuSolver sudokuSolver;
    private final IObserver observer;

    SudokuBoard() {
        this(new BacktrackingSudokuSolver(), new SudokuPlayer());
    }

    SudokuBoard(SudokuSolver sudokuSolver, IObserver observer) {
        this.boardSize = 9;
        this.boxSize = 3;
        this.random = new Random();
        this.sudokuFields = new SudokuField[boardSize][boardSize];
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                this.sudokuFields[x][y] = new SudokuField();
            }
        }
        this.observer = observer;
        this.sudokuSolver = sudokuSolver;
        this.fillDiagonal();
    }

    //
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

    public void notifyObserver() {
        if (observer != null) {
            boolean isValid = isBoardValid();
            observer.onValueChanged(isValid);
        }
    }

    public int get(int x, int y) {
        return this.sudokuFields[x][y].getFieldValue();
    }

    public void set(int x, int y, int value) {
        boolean wasChanged = this.sudokuFields[x][y].setFieldValue(value);
        if (wasChanged) {
            notifyObserver();
        }
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    public int getBoxSize() {
        return this.boxSize;
    }

    public SudokuField[][] getBoard() {
        SudokuField[][] boardCopy = new SudokuField[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            // TODO idk if this works
            System.arraycopy(this.sudokuFields[row], 0, boardCopy[row], 0, boardSize);
        }
        return boardCopy;
    }

    public SudokuRow getRow(int row) {
        var sudokuFields = new SudokuField[boardSize];
        for (int j = 0; j < boardSize; j++) {
            sudokuFields[j] = new SudokuField();
        }

        var sudokuRow = new SudokuRow();
        for (int i = 0; i < boardSize; i++) {
            sudokuFields[i].setFieldValue(this.sudokuFields[row][i].getFieldValue());
        }
        sudokuRow.setSudokuFields(sudokuFields);

        return sudokuRow;
    }

    public SudokuColumn getColumn(int column) {
        var sudokuFields = new SudokuField[boardSize];
        for (int j = 0; j < boardSize; j++) {
            sudokuFields[j] = new SudokuField();
        }

        var sudokuColumn = new SudokuColumn();
        for (int i = 0; i < boardSize; i++) {
            sudokuFields[i].setFieldValue(this.sudokuFields[i][column].getFieldValue());
        }
        sudokuColumn.setSudokuFields(sudokuFields);

        return sudokuColumn;
    }

    public SudokuBox getBox(int x, int y) {
        x -= (x % 3);
        y -= (y % 3);

        var sudokuFields = new SudokuField[boardSize];
        for (int j = 0; j < boardSize; j++) {
            sudokuFields[j] = new SudokuField();
        }

        var sudokuBox = new SudokuBox();
        int i = 0;
        for (int row = 0; row < boxSize; row++) {
            for (int column = 0; column < boxSize; column++) {
                sudokuFields[i].setFieldValue(
                        this.sudokuFields[x + row][y + column].getFieldValue()
                );
                i++;
                // todo: implement safeguard for overflow
                // Ale po co to komu.
            }
        }
        sudokuBox.setSudokuFields(sudokuFields);

        return sudokuBox;
    }

    // Board Generator
    // Fill the diagonal boxSize number of boxSize x boxSize matrices.
    private void fillDiagonal() {
        List<SudokuField> sudokuFields;
        for (int x = 0; x < boardSize; x += boxSize) {
            // Fill only diagonals (row==column).
            fillBox(x, x);

            sudokuFields = this.getBox(x, x).getSudokuFields();
            Collections.shuffle(sudokuFields);
            for (int i = x; i < boxSize + x; i++) {
                for (int j = x; j < boxSize + x; j++) {
                    this.sudokuFields[i][j].setFieldValue(
                            sudokuFields.get((i % 3) * boxSize + (j % 3))
                                    .getFieldValue()
                    );
                }
            }
        }
    }

    // Fill indicated matrix.
    private void fillBox(int row, int column) {
        int num = 0;
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                num++;
//                do {
//                    num = getRandomInt();
//                } while (!unUsedInBox(row, column, num));
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
        /*for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (!(isValid(row, column, this.sudokuFields[row][column].getFieldValue()))) {
                    return false;
                }
            }
        }*/
        return checkBoard();
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