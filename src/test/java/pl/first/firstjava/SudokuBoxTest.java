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