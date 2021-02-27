package com.sabc.digitalchampions.exceptions;

public class EmailExistException extends AbstractException{

    public EmailExistException(){
        super("This email is already in use by another user please change it and try again",601);
    }
}
