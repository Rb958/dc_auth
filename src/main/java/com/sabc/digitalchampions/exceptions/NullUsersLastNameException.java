package com.sabc.digitalchampions.exceptions;

public class NullUsersLastNameException extends AbstractException {
    public NullUsersLastNameException() {
        super("Lastname Could not be null. it's required", 702);
    }
}
