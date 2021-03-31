package com.sabc.digitalchampions.exceptions;

public class NullPasswordException extends AbstractException {
    public NullPasswordException() {
        super("The password is required", 713);
    }
}
