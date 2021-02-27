package com.sabc.digitalchampions.exceptions;

public class NotAuthorizedException extends AbstractException{
    public NotAuthorizedException(){
        super("you are not authorized to access this",501);
    }
}
