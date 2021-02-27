package com.sabc.digitalchampions.exceptions;

public class BadEmailException extends AbstractException {

    public BadEmailException(){
        super("Wrong user Email",401);
    }
}
