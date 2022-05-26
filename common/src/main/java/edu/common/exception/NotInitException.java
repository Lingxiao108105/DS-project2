package edu.common.exception;

public class NotInitException extends RuntimeException {

    public NotInitException(String className) {
        super("Please init the "+ className + " first!");
    }

}
