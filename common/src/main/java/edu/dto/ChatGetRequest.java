package edu.dto;

/**
 * request to get chat messages from server
 * @author lingxiao
 */
public class ChatGetRequest {

    //index of chat client got, server will send message from index
    int index;

    public ChatGetRequest(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
