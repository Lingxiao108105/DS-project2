package edu.dto;

public class RegisterClientResponse {

    boolean isSuccess;
    ClientInfo clientInfo;

    public RegisterClientResponse(boolean isSuccess, ClientInfo clientInfo) {
        this.isSuccess = isSuccess;
        this.clientInfo = clientInfo;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }
}


