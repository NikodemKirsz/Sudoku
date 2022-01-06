package pl.comp.exceptions;

import java.io.IOException;

public class DaoException extends IOException {
    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
