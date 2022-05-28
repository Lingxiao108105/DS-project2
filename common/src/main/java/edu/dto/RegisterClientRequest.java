package edu.dto;

/**
 * request for register as a client
 * @author lingxiao
 */
public class RegisterClientRequest {

    String name;
    private String address;

    public RegisterClientRequest(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
