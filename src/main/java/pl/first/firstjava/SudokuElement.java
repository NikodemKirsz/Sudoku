package pl.first.firstjava;

public class SudokuElement {
    private SudokuField[] sudokuFields;
    private final int size = 9;

    public SudokuElement() {
        this.sudokuFields = new SudokuField[size];
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
}
