package pl.comp.exceptions;

public class NotEnoughElementsException extends SudokuException {
    public NotEnoughElementsException(Throwable cause) {
        super(cause);
    }

    public NotEnoughElementsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughElementsException(String message) {
        super(message);
    }
}
