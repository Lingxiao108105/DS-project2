package edu.dto;

public class RegisterManagerResponse {

    boolean isSuccess;
    String errorMessage;
    ClientInfo clientInfo;

    public RegisterManagerResponse(boolean isSuccess, String errorMessage, ClientInfo clientInfo) {
        this.isSuccess = isSuccess;
        this.errorMessage = errorMessage;
        this.clientInfo = clientInfo;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }
}


