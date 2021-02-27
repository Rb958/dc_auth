package com.sabc.digitalchampions.exceptions;

public class UserNotEnabledException extends AbstractException{
    public UserNotEnabledException() {
        super("This user has not enabled yet", 301);
    }
}
