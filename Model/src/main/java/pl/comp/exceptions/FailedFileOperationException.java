package pl.comp.exceptions;

public class FailedFileOperationException extends DaoException {
    public FailedFileOperationException(Throwable cause) {
        super(cause);
    }

    public FailedFileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
