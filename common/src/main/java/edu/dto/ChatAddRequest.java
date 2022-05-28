package edu.dto;

/**
 * request to add a chat message to server
 * @author lingxiao
 */
public class ChatAddRequest {

    ChatMessage chatMessage;

    public ChatAddRequest(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }
}
