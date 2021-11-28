/*
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.comp.model;

import java.io.Serializable;

public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {
    private SudokuBoard board;
    private int boardSize;
    private int boxSize;

    public BacktrackingSudokuSolver() {
    }

    public void solve(SudokuBoard givenBoard) {
        board = givenBoard;
        boardSize = givenBoard.getBoardSize();
        boxSize = givenBoard.getBoxSize();

        fillRemaining(0, boxSize);
    }

    // A recursive function to fill remaining matrix
    private boolean fillRemaining(int row, int column) {

        // Look out for index out of bounds! (╯ ͠° ͟ʖ ͡°)╯
        if (column >= boardSize) {
            row += 1;
            column = 0;
        }

        // Search for en empty cell
        while (board.get(row, column) != 0) {
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
                board.set(row, column, num);
                // Go to the next cell in a row
                if (fillRemaining(row, column + 1)) {
                    return true;
                }
                board.set(row, column, 0);
            }
        }
        return false; // triggers backtracking
    }

    // Check if safe to put in cell
    public boolean isValid(int i, int j, int num) {
        int temp = board.get(i, j);
        board.set(i, j, 0);
        boolean isUnused = (unUsedInRow(i, num)
                && unUsedInColumn(j, num)
                && unUsedInBox(i - i % boxSize, j - j % boxSize, num));
        board.set(i, j, temp);
        return isUnused;
    }

    // by doing [i - i % SRN][j - j % SRN] we are always getting first cell of matrix

    // check in the row for existence
    private boolean unUsedInRow(int row, int num) {
        for (int column = 0; column < boardSize; column++) {
            if (board.get(row, column) == num) {
                return false;
            }
        }
        return true;
    }

    // check in the row for existence
    private boolean unUsedInColumn(int column, int num) {
        for (int row = 0; row < boardSize; row++) {
            if (board.get(row, column) == num) {
                return false;
            }
        }
        return true;
    }

    // Returns false if given 3 x 3 block contains num.
    private boolean unUsedInBox(int rowStart, int columnStart, int num) {
        for (int row = 0; row < boxSize; row++) {
            for (int column = 0; column < boxSize; column++) {
                if (board.get(rowStart + row, columnStart + column) == num) {
                    return false;
                }
            }
        }
        return true;
    }
}
