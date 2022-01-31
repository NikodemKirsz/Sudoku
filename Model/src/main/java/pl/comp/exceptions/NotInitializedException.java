package pl.comp.exceptions;

import java.util.ResourceBundle;

public class NotInitializedException extends DatabaseException {

    private static final ResourceBundle
            resourceBundle = ResourceBundle.getBundle("bundle");

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

    @Override
    public String getLocalizedMessage() {
        return resourceBundle.getString("NotInitialized");
    }
}
