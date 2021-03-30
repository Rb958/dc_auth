package com.sabc.digitalchampions.exceptions;

public class InvalidePhoneException extends AbstractException {
    public InvalidePhoneException() {
        super("The phone number is not valid", 411);
    }
}
