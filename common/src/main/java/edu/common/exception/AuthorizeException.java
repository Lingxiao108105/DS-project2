package edu.common.exception;

/**
 * AuthorizeException when authorize information is wrong or not enough permission
 * @author lingxiao
 */
public class AuthorizeException extends RuntimeException {

    public AuthorizeException(String errorMessage) {
        super(errorMessage);
    }

}
