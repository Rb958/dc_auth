package com.sabc.digitalchampions.exceptions;

public class NullUsersPhoneException extends AbstractException {
    public NullUsersPhoneException() {
        super("Phone Could not be null. it's required", 703);
    }
}
