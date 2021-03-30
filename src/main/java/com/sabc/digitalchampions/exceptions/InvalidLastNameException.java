package com.sabc.digitalchampions.exceptions;

public class InvalidLastNameException extends AbstractException {
    public InvalidLastNameException() {
        super("Lastname must have at least 2 characters", 713);
    }
}
