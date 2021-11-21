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

class SudokuRowTest {
    private final int size = 9;
    private SudokuRow sudokuRow1;
    private SudokuRow sudokuRow2;
    private SudokuRow sudokuRow3;
    private SudokuField sudokuFields[];

    @BeforeEach
    void init() {
        sudokuRow1 = new SudokuRow();
        sudokuRow2 = new SudokuRow();
        sudokuRow3 = new SudokuRow();

        sudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            sudokuFields[i] = new SudokuField();
            sudokuFields[i].setFieldValue(i);
        }
        sudokuRow1.setSudokuFields(sudokuFields);
        sudokuRow2.setSudokuFields(sudokuFields);

        for (var i = size; i > 0; i--) {
            sudokuFields[size - i] = new SudokuField();
            sudokuFields[size - i].setFieldValue(i);
        }
        sudokuRow3.setSudokuFields(sudokuFields);
    }

    @Test
    void verify() {
        assertTrue(sudokuRow1.verify());

        var invalidSudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            invalidSudokuFields[i] = new SudokuField();
            invalidSudokuFields[i].setFieldValue(i % 8);
        }
        sudokuRow1.setSudokuFields(invalidSudokuFields);
        assertFalse(sudokuRow1.verify());
    }

    @Test
    void equalsTest() {
        assertTrue(SudokuRow.equals(sudokuRow1, sudokuRow2));
        assertFalse(SudokuRow.equals(sudokuRow1, sudokuRow3));
    }

    @Test
    void testEquals() {
        assertTrue(sudokuRow1.equals(sudokuRow2));
        assertFalse(sudokuRow1.equals(sudokuRow3));
        assertFalse(sudokuRow1.equals(new SudokuBoard()));
        assertTrue(sudokuRow1.equals(sudokuRow1));
    }

    @Test
    void testHashCode() {
        assertEquals(sudokuRow1.hashCode(), sudokuRow2.hashCode());
        assertNotEquals(sudokuRow1.hashCode(), sudokuRow3.hashCode());
    }

    @Test
    void testToString() {
        assertTrue(sudokuRow1.toString().contains("SudokuRow"));
    }
}