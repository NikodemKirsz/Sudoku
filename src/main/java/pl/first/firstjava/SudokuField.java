package pl.first.firstjava;

public class SudokuField {
    private int value;

    SudokuField() {
        this.value = 0;
    }

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        this.value = value;
    }
}
