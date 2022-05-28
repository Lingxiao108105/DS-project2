package edu.dto;

/**
 * request for register as a manager
 * @author lingxiao
 */
public class RegisterManagerRequest{

    String name;
    private String address;

    public RegisterManagerRequest(String name, String address) {
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
