package com.sabc.digitalchampions.security.payload.response;

import com.sabc.digitalchampions.exceptions.AbstractException;

public class ResponseException {

    private String message;
    private int status;

    public ResponseException(AbstractException e) {
        this.message = e.getMessage();
        this.status = e.getCode();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
