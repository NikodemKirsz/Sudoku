package pl.comp.exceptions;

public class NotInitializedException extends DatabaseException {
    public NotInitializedException() {
        super();
    }

    public NotInitializedException(String message) {
        super(message);
    }

    public NotInitializedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotInitializedException(Throwable cause) {
        super(cause);
    }
}
