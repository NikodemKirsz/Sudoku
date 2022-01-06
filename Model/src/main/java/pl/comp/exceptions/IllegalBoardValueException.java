package pl.comp.exceptions;

public class IllegalBoardValueException extends SudokuException {
    public IllegalBoardValueException(Throwable cause) {
        super(cause);
    }

    public IllegalBoardValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalBoardValueException(String message) {
        super(message);
    }
}
