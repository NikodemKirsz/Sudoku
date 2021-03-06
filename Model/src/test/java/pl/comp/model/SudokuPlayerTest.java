/*
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.comp.model;

import org.junit.jupiter.api.Test;
import pl.comp.exceptions.IllegalBoardValueException;

class SudokuPlayerTest {

    @Test
    void onValueChanged() throws IllegalBoardValueException {
        var sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), null);
        sudokuBoard.solveGame();

        sudokuBoard.set(0, 0, 0);
        sudokuBoard.set(0, 0, 4);
        sudokuBoard.set(0, 0, 9);

        var sudokuBoardWithObserver = new SudokuBoard();
        sudokuBoardWithObserver.solveGame();

        sudokuBoardWithObserver.set(0, 0, 0);
        sudokuBoardWithObserver.set(0, 0, 4);
        sudokuBoardWithObserver.set(0, 0, 9);
       }
}