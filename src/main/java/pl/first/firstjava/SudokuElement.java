package pl.first.firstjava;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SudokuElement {
    private final List<SudokuField> sudokuFields;
    private static final int size = 9;

    public SudokuElement() {
        this.sudokuFields = Arrays.asList(new SudokuField[size]);
        for (int i = 0; i < size; i++) {
            sudokuFields.set(i, new SudokuField());
        }
    }

    public boolean verify() {
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (sudokuFields.get(i).getFieldValue() == sudokuFields.get(j).getFieldValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setSudokuFields(SudokuField[] sudokuFields) {
        for (int i = 0; i < size; i++) {
            this.sudokuFields.get(i).setFieldValue(sudokuFields[i].getFieldValue());
        }
    }

    public List<SudokuField> getSudokuFields() {
        List<SudokuField> boardCopy = Arrays.asList(new SudokuField[size]);
        for (int row = 0; row < size; row++) {
            // TODO idk if this works
            Collections.copy(boardCopy, sudokuFields);
        }
        return boardCopy;
    }

    public boolean equals(SudokuElement rhs) {
        return equals(this, rhs);
    }

    public static boolean equals(SudokuElement lhs, SudokuElement rhs) {
        var lhsFields = lhs.getSudokuFields();
        var rhsFields = rhs.getSudokuFields();

        boolean isEqual = true;
        for (var i = 0; i < size; i++) {
            isEqual &= lhsFields.get(i).getFieldValue() == rhsFields.get(i).getFieldValue();
        }
        return isEqual;
    }
}
