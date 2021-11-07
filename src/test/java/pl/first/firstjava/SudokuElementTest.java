package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuElementTest {
    private final int size = 9;

    @Test
    void verify() {
        var sudokuElement = new SudokuElement();

        var validSudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            validSudokuFields[i] = new SudokuField();
            validSudokuFields[i].setFieldValue(i);
        }
        sudokuElement.setSudokuFields(validSudokuFields);
        assertTrue(sudokuElement.verify());

        var invalidSudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            invalidSudokuFields[i] = new SudokuField();
            invalidSudokuFields[i].setFieldValue(i % 8);
        }
        sudokuElement.setSudokuFields(invalidSudokuFields);
        assertFalse(sudokuElement.verify());
    }

    @Test
    void setSudokuFields() {
        var sudokuElement = new SudokuElement();

        var writtenSudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            writtenSudokuFields[i] = new SudokuField();
            writtenSudokuFields[i].setFieldValue(i);
        }
        sudokuElement.setSudokuFields(writtenSudokuFields);

        var readSudokuFields = sudokuElement.getSudokuFields();
        boolean isValid = true;
        for (var i = 0; i < size; i++)
        {
            isValid &= readSudokuFields[i].getFieldValue() == i;
        }
        assertTrue(isValid);

        isValid = true;
        for (var i = 0; i < size; i++)
        {
            isValid &= readSudokuFields[i].getFieldValue() == (i < size - 1 ? i : 4);
        }
        assertFalse(isValid);
    }

    @Test
    void equalsTest() {
        var sudokuElement1 = new SudokuElement();
        var sudokuElement2 = new SudokuElement();

        var sudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            sudokuFields[i] = new SudokuField();
            sudokuFields[i].setFieldValue(i);
        }
        sudokuElement1.setSudokuFields(sudokuFields);
        sudokuElement2.setSudokuFields(sudokuFields);

        assertTrue(SudokuElement.equals(sudokuElement1, sudokuElement2));
        assertTrue(sudokuElement1.equals(sudokuElement2));

        var differentSudokuFields = new SudokuField[size];
        for (var i = 0; i < size; i++)
        {
            differentSudokuFields[i] = new SudokuField();
            differentSudokuFields[i].setFieldValue(i + 1);
        }
        sudokuElement2.setSudokuFields(differentSudokuFields);

        assertFalse(SudokuElement.equals(sudokuElement1, sudokuElement2));
        assertFalse(sudokuElement1.equals(sudokuElement2));
    }
}