package pl.comp.exceptions;

public class SudokuException extends Exception {
    public SudokuException(Throwable cause) {
        super(cause);
    }

    public SudokuException(String message, Throwable cause) {
        super(message, cause);
    }

    public SudokuException(String message) {
        super(message);
    }
}
