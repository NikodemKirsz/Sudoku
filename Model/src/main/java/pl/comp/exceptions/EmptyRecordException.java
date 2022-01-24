package pl.comp.exceptions;

import java.util.ResourceBundle;

public class EmptyRecordException extends DatabaseException {

    private static final ResourceBundle
            resourceBundle = ResourceBundle.getBundle("bundle");

    public EmptyRecordException() {
        super();
    }

    public EmptyRecordException(String message) {
        super(message);
    }

    public EmptyRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyRecordException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getLocalizedMessage() {
        return resourceBundle.getString("EmptyRecord");
    }
}
