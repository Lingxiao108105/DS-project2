package edu.dto;

import java.io.Serializable;

public class ChatMessage implements Serializable {

    String name;
    String message;

    public ChatMessage(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
