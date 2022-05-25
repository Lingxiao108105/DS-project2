package edu.dto;


import java.io.Serializable;

public class RegisterManagerRequest implements Serializable {

    String name;

    public RegisterManagerRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
