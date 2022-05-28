package edu.dto;

import java.io.Serializable;

/**
 * ChatMessage
 * @author lingxiao
 */
public class ChatMessage implements Serializable {

    //who send the chat
    String name;
    //message that client sent
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

    @Override
    public String toString() {
        return "ChatMessage{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
