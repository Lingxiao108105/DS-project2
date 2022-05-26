package edu.dto;

public class ChatAddRequest {

    ChatMessage chatMessage;

    public ChatAddRequest(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }
}
