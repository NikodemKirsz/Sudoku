/**
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoxTest {
    private final int size = 9;

    @Test
    void verify() {
        var sudokuBox = new SudokuBox();

        var validSudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            validSudokuFields[i] = new SudokuField();
            validSudokuFields[i].setFieldValue(i);
        }
        sudokuBox.setSudokuFields(validSudokuFields);
        assertTrue(sudokuBox.verify());

        var invalidSudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            invalidSudokuFields[i] = new SudokuField();
            invalidSudokuFields[i].setFieldValue(i % 8);
        }
        sudokuBox.setSudokuFields(invalidSudokuFields);
        assertFalse(sudokuBox.verify());
    }
}