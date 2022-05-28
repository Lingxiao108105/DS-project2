package edu.dto;

/**
 * response for update canvas to server
 * @author lingxiao
 */
public class CanvasUpdateResponse {

    boolean isSuccess;

    public CanvasUpdateResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
