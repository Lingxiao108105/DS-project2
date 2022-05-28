package edu.common.exception;

/**
 * Exception level AuthorizeException, should just notify the user
 * @author lingxiao
 */
public class ExceptionAuthorizeException extends AuthorizeException{

    public ExceptionAuthorizeException(String errorMessage) {
        super(errorMessage);
    }
}
