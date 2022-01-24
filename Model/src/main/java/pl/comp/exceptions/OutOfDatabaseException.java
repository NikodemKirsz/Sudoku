package pl.comp.exceptions;

import java.util.ResourceBundle;

public class OutOfDatabaseException extends DatabaseException {

    private static final ResourceBundle
            resourceBundle = ResourceBundle.getBundle("bundle");

    public OutOfDatabaseException() {
        super();
    }

    public OutOfDatabaseException(String message) {
        super(message);
    }

    public OutOfDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfDatabaseException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getLocalizedMessage() {
        return resourceBundle.getString("OutOfDatabase");
    }
}
