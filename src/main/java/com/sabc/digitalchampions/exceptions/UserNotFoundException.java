package com.sabc.digitalchampions.exceptions;

public class UserNotFoundException extends AbstractException {
    public UserNotFoundException(){
        super("This user doesn't exist", 801);
    }
}
