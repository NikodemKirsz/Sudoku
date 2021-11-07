package pl.first.firstjava;

public class SudokuField {
    private int value;

    SudokuField() {
        this.value = 0;
    }

    public int getFieldValue() {
        return value;
    }

    public boolean setFieldValue(int value) {
        if (value >= 0 && value <= 9) {
            this.value = value;
            return true;
        }
        return false;
    }
}
