package edu.common.exception;

public class AuthorizeException extends RuntimeException {

    public AuthorizeException(String errorMessage) {
        super(errorMessage);
    }

}
