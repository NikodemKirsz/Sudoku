/*
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

//         ______     __  __     _____     ______     __  __     __  __
//        /\  ___\   /\ \/\ \   /\  __-.  /\  __ \   /\ \/ /    /\ \/\ \
//        \ \___  \  \ \ \_\ \  \ \ \/\ \ \ \ \/\ \  \ \  _"-.  \ \ \_\ \
//         \/\_____\  \ \_____\  \ \____-  \ \_____\  \ \_\ \_\  \ \_____\
//          \/_____/   \/_____/   \/____/   \/_____/   \/_/\/_/   \/_____/

package pl.comp.model;

import java.io.Serializable;
import java.lang.System;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.exceptions.IllegalBoardValueException;
import pl.comp.exceptions.RandomizerException;
import pl.comp.exceptions.SudokuException;

public class SudokuBoard implements IObservable, Serializable, Cloneable {
    private final SudokuField[][] sudokuFields;
    private final int boardSize;
    private final int boxSize; // square root of N
    private final transient SudokuSolver sudokuSolver;
    private final transient IObserver observer;
    private static final Logger printBoardLogger = LoggerFactory.getLogger("printBoardLogger");
    private static final Logger logger = LoggerFactory.getLogger(SudokuBoard.class);

    public SudokuBoard(IObserver observer) {
        this(new BacktrackingSudokuSolver(), observer);
    }

    public SudokuBoard(SudokuSolver sudokuSolver, IObserver observer) {
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

    SudokuBoard() {
        this(new BacktrackingSudokuSolver(), new SudokuPlayer());
    }

    SudokuBoard(int[][] givenBoard) {
        this();
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                try {
                    this.sudokuFields[row][column].setFieldValue(givenBoard[row][column]);
                } catch (SudokuException e) {
                    logger.error(e.getLocalizedMessage());
                }
            }
        }
    }

    public void generateSudokuPuzzle(DifficultyLevel level)  {
        solveGame();
        // this.printBoard();
        deleteFields(level.getFieldsToDelete());
    }

    private void deleteFields(int count) {
        var rand = new Randomizer();

        int changed = 0;
        var changedArray = new boolean[boardSize][boardSize];

        int field = 0;
        while (changed < count) {
            try {
                field = rand.getRandomInt(80, 0);
            } catch (IllegalBoardValueException e) {
                var exception = new RandomizerException(e);
                logger.error(exception.getLocalizedMessage());
            }
            int row = field % 9;
            int column = field / 9;

            if (!changedArray[row][column]) {
                set(row, column, 0);
                changed++;
                changedArray[row][column] = true;
            }
        }
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public void notifyObserver() {
        if (observer != null) {
            boolean isValid;
            isValid = isBoardValid();
            observer.onValueChanged(isValid);
        }
    }

    public int get(int x, int y) {
        return this.sudokuFields[x][y].getFieldValue();
    }

    public SudokuField getField(int x, int y) {
        return this.sudokuFields[x][y];
    }

    public void set(int x, int y, int value) {
        boolean wasChanged = false;
        try {
            wasChanged = this.sudokuFields[x][y].setFieldValue(value);
        } catch (SudokuException e) {
            logger.error(e.getLocalizedMessage());
        }
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
        try {
            for (int i = 0; i < boardSize; i++) {
                sudokuFields[i].setFieldValue(this.sudokuFields[row][i].getFieldValue());
            }
            sudokuRow.setSudokuFields(sudokuFields);
        } catch (SudokuException e) {
            logger.error(e.getLocalizedMessage());
        }

        return sudokuRow;
    }

    public SudokuColumn getColumn(int column) {
        var sudokuFields = new SudokuField[boardSize];
        for (int j = 0; j < boardSize; j++) {
            sudokuFields[j] = new SudokuField();
        }

        var sudokuColumn = new SudokuColumn();
        try {
            for (int i = 0; i < boardSize; i++) {
                sudokuFields[i].setFieldValue(this.sudokuFields[i][column].getFieldValue());
            }
            sudokuColumn.setSudokuFields(sudokuFields);
        } catch (SudokuException e) {
            logger.error(e.getLocalizedMessage());
        }

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
        try {
            for (int row = 0; row < boxSize; row++) {
                for (int column = 0; column < boxSize; column++) {
                    sudokuFields[i].setFieldValue(
                            this.sudokuFields[x + row][y + column].getFieldValue()
                    );
                    i++;
                }
            }
            sudokuBox.setSudokuFields(sudokuFields);
        } catch (SudokuException e) {
            logger.error(e.getLocalizedMessage());
        }

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
                printBoardLogger.info(this.sudokuFields[i][j].getFieldValue() + "\t");
            }
            printBoardLogger.info("\n");
        }
        printBoardLogger.info("\n");
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

    @Override
    public SudokuBoard clone() throws CloneNotSupportedException {
        SudokuBoard clonedSudokuBoard = null;

        clonedSudokuBoard = new SudokuBoard();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                clonedSudokuBoard.set(i, j, this.get(i,j));
            }
        }
        return clonedSudokuBoard;
    }
}
