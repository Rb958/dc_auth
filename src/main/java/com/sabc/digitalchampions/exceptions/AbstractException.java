package com.sabc.digitalchampions.exceptions;

public abstract class AbstractException extends Exception {
    private final String message;
    private final int code;

    public AbstractException(String message, int code){
        super(message);
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
