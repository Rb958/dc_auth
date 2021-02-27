package com.sabc.digitalchampions.exceptions;

public class UnknowErrorException extends AbstractException {
    public UnknowErrorException(String action, int code){
        super("An error occurred while trying to " + action + " (please contact our support if this error persists)", code);
    }
}
