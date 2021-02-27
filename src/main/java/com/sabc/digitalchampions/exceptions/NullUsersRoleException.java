package com.sabc.digitalchampions.exceptions;

public class NullUsersRoleException extends AbstractException {
    public NullUsersRoleException() {
        super("Role Could not be null. it's required", 704);
    }
}
