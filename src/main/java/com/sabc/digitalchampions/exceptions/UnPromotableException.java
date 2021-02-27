package com.sabc.digitalchampions.exceptions;

public class UnPromotableException extends AbstractException {
    public UnPromotableException() {
        super("This user cannot be promoted", 902);
    }
}
