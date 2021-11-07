package pl.first.firstjava;

public class SudokuElement {
    private final SudokuField[] sudokuFields;
    private static final int size = 9;

    public SudokuElement() {
        this.sudokuFields = new SudokuField[size];
        for (int j = 0; j < size; j++) {
            sudokuFields[j] = new SudokuField();
        }
    }

    public boolean verify() {
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (sudokuFields[i].getFieldValue() == sudokuFields[j].getFieldValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setSudokuFields(SudokuField[] sudokuFields) {
        for (int i = 0; i < size; i++) {
            this.sudokuFields[i].setFieldValue(sudokuFields[i].getFieldValue());
        }
    }

    public SudokuField[] getSudokuFields() {
        return this.sudokuFields.clone();
    }

    public boolean equals(SudokuElement rhs) {
        return equals(this, rhs);
    }

    public static boolean equals(SudokuElement lhs, SudokuElement rhs) {
        var lhsFields = lhs.getSudokuFields();
        var rhsFields = rhs.getSudokuFields();

        boolean isEqual = true;
        for (var i = 0; i < size; i++) {
            isEqual &= lhsFields[i].getFieldValue() == rhsFields[i].getFieldValue();
        }
        return isEqual;
    }
}
