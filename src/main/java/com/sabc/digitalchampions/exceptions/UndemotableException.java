package com.sabc.digitalchampions.exceptions;

public class UndemotableException extends AbstractException{
    public UndemotableException() {
        super("This user cannot be demoted", 901);
    }
}
