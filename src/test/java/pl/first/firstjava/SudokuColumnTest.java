/**
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.first.firstjava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuColumnTest {
    private final int size = 9;
    private SudokuColumn sudokuColumn1;
    private SudokuColumn sudokuColumn2;
    private SudokuColumn sudokuColumn3;
    private SudokuField sudokuFields[];

    @BeforeEach
    void init() {
        sudokuColumn1 = new SudokuColumn();
        sudokuColumn2 = new SudokuColumn();
        sudokuColumn3 = new SudokuColumn();

        sudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            sudokuFields[i] = new SudokuField();
            sudokuFields[i].setFieldValue(i);
        }
        sudokuColumn1.setSudokuFields(sudokuFields);
        sudokuColumn2.setSudokuFields(sudokuFields);

        for (var i = size; i > 0; i--) {
            sudokuFields[size - i] = new SudokuField();
            sudokuFields[size - i].setFieldValue(i);
        }
        sudokuColumn3.setSudokuFields(sudokuFields);
    }

    @Test
    void verify() {
        assertTrue(sudokuColumn1.verify());

        var invalidSudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            invalidSudokuFields[i] = new SudokuField();
            invalidSudokuFields[i].setFieldValue(i % 8);
        }
        sudokuColumn1.setSudokuFields(invalidSudokuFields);
        assertFalse(sudokuColumn1.verify());
    }

    @Test
    void equalsTest() {
        assertTrue(SudokuColumn.equals(sudokuColumn1, sudokuColumn2));
        assertFalse(SudokuColumn.equals(sudokuColumn1, sudokuColumn3));
    }

    @Test
    void testEquals() {
        assertTrue(sudokuColumn1.equals(sudokuColumn2));
        assertFalse(sudokuColumn1.equals(sudokuColumn3));
        assertFalse(sudokuColumn1.equals(new SudokuBoard()));
        assertTrue(sudokuColumn1.equals(sudokuColumn1));
    }

    @Test
    void testHashCode() {
        assertEquals(sudokuColumn1.hashCode(), sudokuColumn2.hashCode());
        assertNotEquals(sudokuColumn1.hashCode(), sudokuColumn3.hashCode());
    }

    @Test
    void testToString() {
        assertTrue(sudokuColumn1.toString().contains("SudokuColumn"));
    }
}