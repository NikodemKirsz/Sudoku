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

class SudokuBoxTest {
    private final int size = 9;
    private SudokuBox sudokuBox1;
    private SudokuBox sudokuBox2;
    private SudokuBox sudokuBox3;
    private SudokuField sudokuFields[];

    @BeforeEach
    void init() throws NotEnoughElementsException, IllegalBoardValueException {
        sudokuBox1 = new SudokuBox();
        sudokuBox2 = new SudokuBox();
        sudokuBox3 = new SudokuBox();

        sudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            sudokuFields[i] = new SudokuField();
            sudokuFields[i].setFieldValue(i+1);
        }
        sudokuBox1.setSudokuFields(sudokuFields);
        sudokuBox2.setSudokuFields(sudokuFields);

        for (var i = size; i > 0; i--) {
            sudokuFields[size - i] = new SudokuField();
            sudokuFields[size - i].setFieldValue(i);
        }
        sudokuBox3.setSudokuFields(sudokuFields);
    }

    @Test
    void verify() throws NotEnoughElementsException, IllegalBoardValueException {
        assertTrue(sudokuBox1.verify());

        var invalidSudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            invalidSudokuFields[i] = new SudokuField();
            invalidSudokuFields[i].setFieldValue(i % 8);
        }
        sudokuBox1.setSudokuFields(invalidSudokuFields);
        assertFalse(sudokuBox1.verify());
    }

    @Test
    void equalsTest() {
        assertTrue(SudokuBox.equals(sudokuBox1, sudokuBox2));
        assertFalse(SudokuBox.equals(sudokuBox1, sudokuBox3));
    }

    @Test
    void testEquals() throws IllegalBoardValueException {
        assertTrue(sudokuBox1.equals(sudokuBox2));
        assertFalse(sudokuBox1.equals(sudokuBox3));
        assertFalse(sudokuBox1.equals(new SudokuBoard()));
        assertTrue(sudokuBox1.equals(sudokuBox1));

        // cohesion
        assertEquals(sudokuBox1.equals(sudokuBox2), sudokuBox1.equals(sudokuBox2));
    }

    @Test
    void testHashCode() {
        assertEquals(sudokuBox1.hashCode(), sudokuBox2.hashCode());
        assertNotEquals(sudokuBox1.hashCode(), sudokuBox3.hashCode());

        // cohesion
        var hashCode = sudokuBox1.hashCode();
        assertEquals(hashCode, sudokuBox1.hashCode());
    }

    @Test
    void testToString() {
        assertTrue(sudokuBox1.toString().contains("SudokuBox"));
    }

    @Test
    void cloneTest() throws CloneNotSupportedException {
        SudokuElement se = sudokuBox1.clone();
        assertEquals(se.getSudokuFields(), sudokuBox1.getSudokuFields());
        assertNotSame(se, sudokuBox1);
    }
}