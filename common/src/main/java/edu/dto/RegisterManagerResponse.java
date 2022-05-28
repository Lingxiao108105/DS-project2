package edu.dto;

/**
 * response of register as a manager
 * @author lingxiao
 */
public class RegisterManagerResponse {

    boolean isSuccess;
    //error message when fail
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


