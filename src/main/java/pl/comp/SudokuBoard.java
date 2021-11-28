/**
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.comp;

import java.io.Serializable;
import java.lang.System;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuBoard implements IObservable, Serializable {
    private final SudokuField[][] sudokuFields;
    private final int boardSize;
    private final int boxSize; // square root of N
    private final SudokuSolver sudokuSolver;
    private final IObserver observer;

    SudokuBoard() {
        this(new BacktrackingSudokuSolver(), new SudokuPlayer());
    }

    SudokuBoard(SudokuSolver sudokuSolver, IObserver observer) {
        this.boardSize = 9;
        this.boxSize = 3;
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
            }
        }
        sudokuBox.setSudokuFields(sudokuFields);

        return sudokuBox;
    }

    // Board Generator
    // Fill the diagonal boxSize number of boxSize x boxSize matrices.
    private void fillDiagonal() {
        for (int x = 0; x < boardSize; x += boxSize) {
            // Fill only diagonals (row==column).
            fillBox(x, x);
        }
    }

    // Fill indicated matrix.
    private void fillBox(int row, int column) {
        List<Integer> boxFields = Arrays.asList(new Integer[boardSize]);
        for (int j = 0; j < boardSize; j++) {
            boxFields.set(j, j + 1);
        }
        Collections.shuffle(boxFields);
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                this.sudokuFields[row + i][column + j]
                        .setFieldValue(boxFields.get(i * boxSize + j));
            }
        }
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
        return checkBoard();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuBoard that)) {
            return false;
        }

        return new EqualsBuilder()
                .append(getBoardSize(), that.getBoardSize())
                .append(getBoxSize(), that.getBoxSize())
                .append(sudokuFields, that.sudokuFields)
                .append(sudokuSolver, that.sudokuSolver)
                .append(observer, that.observer)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(sudokuFields)
                .append(getBoardSize())
                .append(getBoxSize())
                .append(sudokuSolver)
                .append(observer)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("SudokuBoard{")
                .append("sudokuFields=").append(Arrays.toString(sudokuFields))
                .append(", boardSize=").append(boardSize)
                .append(", boxSize=").append(boxSize)
                .append(", sudokuSolver=").append(sudokuSolver)
                .append(", observer=").append(observer)
                .append('}')
                .toString();
    }
}

