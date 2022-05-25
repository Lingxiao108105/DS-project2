package edu.dto;

import java.io.Serializable;


public class CanvasUpdateResponse implements Serializable {

    boolean isSuccess;

    public CanvasUpdateResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
