package edu.dto;

import java.util.List;

/**
 * response of getting chat messages from server
 * @author lingxiao
 */
public class ChatGetResponse {

    //latest index of chat
    int lastIndex;
    //chat messages
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
