package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowTest {
    private final int size = 9;

    @Test
    void verify() {
        var sudokuRow = new SudokuRow();

        var validSudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            validSudokuFields[i] = new SudokuField();
            validSudokuFields[i].setFieldValue(i);
        }
        sudokuRow.setSudokuFields(validSudokuFields);
        assertTrue(sudokuRow.verify());

        var invalidSudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            invalidSudokuFields[i] = new SudokuField();
            invalidSudokuFields[i].setFieldValue(i % 8);
        }
        sudokuRow.setSudokuFields(invalidSudokuFields);
        assertFalse(sudokuRow.verify());
    }
}