package edu.dto;

import java.util.List;

public class ChatGetResponse {

    int lastIndex;
    List<ChatMessage> chatMessageList;

    public ChatGetResponse(int lastIndex, List<ChatMessage> chatMessageList) {
        this.lastIndex = lastIndex;
        this.chatMessageList = chatMessageList;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public List<ChatMessage> getChatMessageList() {
        return chatMessageList;
    }
}
