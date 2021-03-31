package com.sabc.digitalchampions.exceptions;

public class InvalidOtpException extends AbstractException {
    public InvalidOtpException() {
        super("Invalid Otp", 413);
    }
}
