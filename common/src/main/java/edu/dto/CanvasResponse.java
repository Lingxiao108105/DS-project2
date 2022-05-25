package edu.dto;


public class CanvasResponse {

    boolean isSuccess;
    byte[] imageBytes;

    public CanvasResponse(boolean isSuccess, byte[] imageBytes) {
        this.isSuccess = isSuccess;
        this.imageBytes = imageBytes;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }
}
