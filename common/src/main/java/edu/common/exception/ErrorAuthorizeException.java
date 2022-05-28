package edu.common.exception;

public class ErrorAuthorizeException extends AuthorizeException{

    public ErrorAuthorizeException(String errorMessage) {
        super(errorMessage);
    }
}
