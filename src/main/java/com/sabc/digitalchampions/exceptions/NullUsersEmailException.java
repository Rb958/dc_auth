package com.sabc.digitalchampions.exceptions;

public class NullUsersEmailException extends AbstractException {
    public NullUsersEmailException() {
        super("The email Could no be null. it's required",701);
    }
}
