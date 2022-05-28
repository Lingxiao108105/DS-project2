package edu.common.exception;

/**
 * NotInitException: the singleton class has not been init
 * @author lingxiao
 */
public class NotInitException extends RuntimeException {

    public NotInitException(String className) {
        super("Please init the "+ className + " first!");
    }

}
