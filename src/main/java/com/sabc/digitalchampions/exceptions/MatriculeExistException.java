package com.sabc.digitalchampions.exceptions;

public class MatriculeExistException extends AbstractException {
    public MatriculeExistException() {
        super("This \"Matricule\" already exist", 712);
    }
}
