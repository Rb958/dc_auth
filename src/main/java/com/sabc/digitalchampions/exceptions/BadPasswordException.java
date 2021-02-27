package com.sabc.digitalchampions.exceptions;

public class BadPasswordException extends AbstractException {
    public BadPasswordException() {
        super("Wrong password", 402);
    }
}
