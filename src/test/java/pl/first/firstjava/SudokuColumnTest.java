package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuColumnTest {
    private final int size = 9;

    @Test
    void verify() {
        var sudokuColumn = new SudokuColumn();

        var validSudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            validSudokuFields[i] = new SudokuField();
            validSudokuFields[i].setFieldValue(i);
        }
        sudokuColumn.setSudokuFields(validSudokuFields);
        assertTrue(sudokuColumn.verify());

        var invalidSudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            invalidSudokuFields[i] = new SudokuField();
            invalidSudokuFields[i].setFieldValue(i % 8);
        }
        sudokuColumn.setSudokuFields(invalidSudokuFields);
        assertFalse(sudokuColumn.verify());
    }
}