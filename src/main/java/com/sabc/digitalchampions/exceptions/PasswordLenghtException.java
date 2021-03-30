package com.sabc.digitalchampions.exceptions;

public class PasswordLenghtException extends AbstractException {
    public PasswordLenghtException() {
        super("The password must have at least 6 characters", 412);
    }
}
