package com.sabc.digitalchampions.exceptions;

public class NullUsersMatriculeException extends AbstractException {
    public NullUsersMatriculeException() {
        super("The user's \"Matricule\" mustn't be null", 714);
    }
}
