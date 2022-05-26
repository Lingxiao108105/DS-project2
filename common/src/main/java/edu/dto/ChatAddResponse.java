package edu.dto;

public class ChatAddResponse {

    boolean isSuccess;

    public ChatAddResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
