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
}