package pl.comp.exceptions;

public class ProszeNieUzywacTejMetodyException extends DatabaseException {
    public ProszeNieUzywacTejMetodyException(String message) {
        super(message);
    }

    public ProszeNieUzywacTejMetodyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProszeNieUzywacTejMetodyException(Throwable cause) {
        super(cause);
    }
}
