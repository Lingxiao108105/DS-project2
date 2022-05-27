package edu.dto;

public class RegisterClientResponse {

    boolean isSuccess;
    String errorMessage;
    ClientInfo clientInfo;

    public RegisterClientResponse(boolean isSuccess, String errorMessage, ClientInfo clientInfo) {
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


