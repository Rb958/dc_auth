package com.sabc.digitalchampions.exceptions;

public class PhoneExistException extends AbstractException {

    public PhoneExistException() {
        super("This phone number is already in use by another user. please change it and try again",601);
    }
}
