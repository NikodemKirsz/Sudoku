/*
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.comp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.comp.exceptions.IllegalBoardValueException;
import pl.comp.exceptions.NotEnoughElementsException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuElementTest {
    private final int size = 9;
    private SudokuElement sudokuElement1;
    private SudokuElement sudokuElement2;
    private SudokuElement sudokuElement3;
    private SudokuElement sudokuElement4;
    private SudokuField sudokuFields[];

    @BeforeEach
    void init() throws NotEnoughElementsException, IllegalBoardValueException {
        sudokuElement1 = new SudokuElement();
        sudokuElement2 = new SudokuElement();
        sudokuElement3 = new SudokuElement();
        sudokuElement4 = new SudokuElement();

        sudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            sudokuFields[i] = new SudokuField();
            sudokuFields[i].setFieldValue(i+1);
        }
        sudokuElement1.setSudokuFields(sudokuFields);
        sudokuElement2.setSudokuFields(sudokuFields);

        for (var i = size; i > 0; i--) {
            sudokuFields[size - i] = new SudokuField();
            sudokuFields[size - i].setFieldValue(i);
        }
        sudokuElement3.setSudokuFields(sudokuFields);

        sudokuFields = new SudokuField[size - 1];
        for (var i = size - 1; i > 0; i--) {
            sudokuFields[size - 1 - i] = new SudokuField();
            sudokuFields[size - 1 - i].setFieldValue(i);
        }
    }

    @Test
    void verify() throws NotEnoughElementsException, IllegalBoardValueException {
        assertTrue(sudokuElement1.verify());

        var invalidSudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            invalidSudokuFields[i] = new SudokuField();
            invalidSudokuFields[i].setFieldValue(i % 8);
        }
        sudokuElement1.setSudokuFields(invalidSudokuFields);
        assertFalse(sudokuElement1.verify());
    }

    @Test
    void setSudokuFields() {
        var readSudokuFields = sudokuElement1.getSudokuFields();
        boolean isValid = true;
        for (var i = 0; i < size; i++)
        {
            isValid &= readSudokuFields.get(i).getFieldValue() == i+1;
        }
        assertTrue(isValid);

        isValid = true;
        for (var i = 0; i < size; i++)
        {
            isValid &= readSudokuFields.get(i).getFieldValue() == (i < size - 1 ? i : 4);
        }
        assertFalse(isValid);
    }

    @Test
    void setSudokuFieldsException() {
        assertThrows(NotEnoughElementsException.class, () -> sudokuElement4.setSudokuFields(sudokuFields));
    }

    @Test
    void equalsTest() {
        assertTrue(SudokuElement.equals(sudokuElement1, sudokuElement2));
        assertFalse(SudokuElement.equals(sudokuElement1, sudokuElement3));
    }

    @Test
    void testEquals() throws IllegalBoardValueException {
        assertTrue(sudokuElement1.equals(sudokuElement2));
        assertFalse(sudokuElement1.equals(sudokuElement3));
        assertFalse(sudokuElement1.equals(new SudokuBoard()));
        assertTrue(sudokuElement1.equals(sudokuElement1));

        // cohesion
        assertEquals(sudokuElement1.equals(sudokuElement2), sudokuElement1.equals(sudokuElement2));
    }

    @Test
    void testHashCode() {
        assertEquals(sudokuElement1.hashCode(), sudokuElement2.hashCode());
        assertNotEquals(sudokuElement1.hashCode(), sudokuElement3.hashCode());

        // cohesion
        var hashCode = sudokuElement1.hashCode();
        assertEquals(hashCode, sudokuElement1.hashCode());
    }

    @Test
    void testToString() {
        assertTrue(sudokuElement1.toString().contains("SudokuElement"));
    }

    @Test
    void cloneTest() throws CloneNotSupportedException {
        SudokuElement se = new SudokuElement();
        se = sudokuElement1.clone();
        assertEquals(sudokuElement1, se);
    }
}