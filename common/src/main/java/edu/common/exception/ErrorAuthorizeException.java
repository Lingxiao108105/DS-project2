package edu.common.exception;

/**
 * Error level AuthorizeException, should close the application
 * @author lingxiao
 */
public class ErrorAuthorizeException extends AuthorizeException{

    public ErrorAuthorizeException(String errorMessage) {
        super(errorMessage);
    }
}
