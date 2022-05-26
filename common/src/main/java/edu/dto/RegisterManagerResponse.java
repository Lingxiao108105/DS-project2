package edu.dto;

public class RegisterManagerResponse {

    boolean isSuccess;
    ClientInfo clientInfo;

    public RegisterManagerResponse(boolean isSuccess, ClientInfo clientInfo) {
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


