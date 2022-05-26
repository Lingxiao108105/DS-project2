package edu.dto;

public class CanvasUpdateResponse {

    boolean isSuccess;

    public CanvasUpdateResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
