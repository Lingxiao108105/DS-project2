package edu.dto;

/**
 * response of add a chat message to server
 * @author lingxiao
 */
public class ChatAddResponse {

    boolean isSuccess;

    public ChatAddResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
