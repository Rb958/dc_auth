package com.sabc.digitalchampions.utils.codegenerator;

public class ConfigurationNotFoundException extends RuntimeException {
    public ConfigurationNotFoundException(String message) {
        super(message);
    }

    public ConfigurationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationNotFoundException(Throwable cause) {
        super(cause);
    }

    public ConfigurationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ConfigurationNotFoundException() {
    }
}
