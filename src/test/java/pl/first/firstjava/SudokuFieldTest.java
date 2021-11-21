/**
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    @Test
    void getFieldValue() {
        var sudokuField = new SudokuField();

        int value = 8;
        sudokuField.setFieldValue(value);
        assertEquals(sudokuField.getFieldValue(), value);

        value = 4;
        sudokuField.setFieldValue(value);
        assertNotEquals(sudokuField.getFieldValue(), value + 1);
    }

    @Test
    void setFieldValue() {
        var sudokuField = new SudokuField();

        int value = 8;
        sudokuField.setFieldValue(value);
        assertEquals(sudokuField.getFieldValue(), value);

        value = 4;
        sudokuField.setFieldValue(value);
        assertNotEquals(sudokuField.getFieldValue(), value + 1);

        int valueOutOfBound = 88;
        sudokuField.setFieldValue(valueOutOfBound);
        assertEquals(sudokuField.getFieldValue(), value);

        valueOutOfBound = -10;
        sudokuField.setFieldValue(valueOutOfBound);
        assertEquals(sudokuField.getFieldValue(), value);
    }

    @Test
    void testEquals() {
        var sudokuField1 = new SudokuField();
        var sudokuField2 = new SudokuField();
        var sudokuField3 = new SudokuField();
        sudokuField1.setFieldValue(1);
        sudokuField2.setFieldValue(1);
        sudokuField3.setFieldValue(2);

        assertTrue(sudokuField1.equals(sudokuField2));
        assertFalse(sudokuField1.equals(sudokuField3));
        assertFalse(sudokuField1.equals(new SudokuBoard()));
        assertTrue(sudokuField1.equals(sudokuField1));
    }

    @Test
    void testHashCode() {
        var sudokuField1 = new SudokuField();
        var sudokuField2 = new SudokuField();
        var sudokuField3 = new SudokuField();
        sudokuField1.setFieldValue(1);
        sudokuField2.setFieldValue(1);
        sudokuField3.setFieldValue(2);

        assertEquals(sudokuField1.hashCode(), sudokuField2.hashCode());
        assertNotEquals(sudokuField1.hashCode(), sudokuField3.hashCode());
    }

    @Test
    void testToString() {
        var sudokuField1 = new SudokuField();
        var sudokuField3 = new SudokuField();
        sudokuField1.setFieldValue(1);
        sudokuField3.setFieldValue(2);

        assertTrue(sudokuField1.toString().contains("value=1"));
        assertTrue(sudokuField3.toString().contains("value=2"));

    }
}