/*
 * Copyright (C) 2021 RCM.
 * All rights reserved. This software is the confidential and proprietary information of RCM.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with RCM.
 */

package pl.comp.model;

import org.junit.jupiter.api.Test;
import pl.comp.exceptions.IllegalBoardValueException;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    @Test
    void getFieldValue() throws IllegalBoardValueException {
        var sudokuField = new SudokuField();

        int value = 8;
        sudokuField.setFieldValue(value);
        assertEquals(sudokuField.getFieldValue(), value);

        value = 4;
        sudokuField.setFieldValue(value);
        assertNotEquals(sudokuField.getFieldValue(), value + 1);
    }

    @Test
    void setFieldValue() throws IllegalBoardValueException {
        var sudokuField = new SudokuField();
        Locale locale = new Locale("en","US");
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("pl.comp.model.Bundle", locale);
//        System.out.println(resourceBundle.getString("cause"));
        Locale.setDefault(locale);

        int value = 8;
        sudokuField.setFieldValue(value);
        assertEquals(sudokuField.getFieldValue(), value);

        value = 4;
        sudokuField.setFieldValue(value);
        assertNotEquals(sudokuField.getFieldValue(), value + 1);

        int valueOutOfBound = 88;
        int finalValueOutOfBound = valueOutOfBound;
        assertThrows(IllegalBoardValueException.class, ()->sudokuField.setFieldValue(finalValueOutOfBound));

        valueOutOfBound = -10;
        int finalValueOutOfBound1 = valueOutOfBound;
        assertThrows(IllegalBoardValueException.class, ()->sudokuField.setFieldValue(finalValueOutOfBound1));
    }

    @Test
    void testEquals() throws IllegalBoardValueException {
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

        // cohesion
        assertEquals(sudokuField1.equals(sudokuField2), sudokuField1.equals(sudokuField2));
    }

    @Test
    void testHashCode() throws IllegalBoardValueException {
        var sudokuField1 = new SudokuField();
        var sudokuField2 = new SudokuField();
        var sudokuField3 = new SudokuField();
        sudokuField1.setFieldValue(1);
        sudokuField2.setFieldValue(1);
        sudokuField3.setFieldValue(2);

        assertEquals(sudokuField1.hashCode(), sudokuField2.hashCode());
        assertNotEquals(sudokuField1.hashCode(), sudokuField3.hashCode());

        // cohesion
        var hashCode = sudokuField1.hashCode();
        assertEquals(hashCode, sudokuField1.hashCode());
    }

    @Test
    void testToString() throws IllegalBoardValueException {
        var sudokuField1 = new SudokuField();
        var sudokuField3 = new SudokuField();
        sudokuField1.setFieldValue(1);
        sudokuField3.setFieldValue(2);

        assertTrue(sudokuField1.toString().contains("value=1"));
        assertTrue(sudokuField3.toString().contains("value=2"));

    }

    @Test
    void cloneTest() throws CloneNotSupportedException {
        var sudokuField1 = new SudokuField();
        var sudokuField2 = sudokuField1.clone();
        assertTrue(sudokuField1.equals(sudokuField2));
    }

    @Test
    void compareTest() throws IllegalBoardValueException {
        var sudokuField1 = new SudokuField();
        var sudokuField2 = new SudokuField();

        sudokuField1.setFieldValue(1);
        sudokuField2.setFieldValue(1);
        assertEquals(sudokuField1.compareTo(sudokuField2), 0);

        sudokuField1.setFieldValue(2);
        assertEquals(sudokuField1.compareTo(sudokuField2), 1);

        sudokuField1.setFieldValue(0);
        assertEquals(sudokuField1.compareTo(sudokuField2), -1);

        assertThrows(NullPointerException.class, () -> sudokuField1.compareTo(null));
    }
}