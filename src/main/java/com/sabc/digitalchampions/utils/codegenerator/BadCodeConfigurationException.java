package com.sabc.digitalchampions.utils.codegenerator;

public class BadCodeConfigurationException extends RuntimeException {
    public BadCodeConfigurationException() {
    }

    public BadCodeConfigurationException(String message) {
        super(message);
    }

    public BadCodeConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadCodeConfigurationException(Throwable cause) {
        super(cause);
    }

    public BadCodeConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
