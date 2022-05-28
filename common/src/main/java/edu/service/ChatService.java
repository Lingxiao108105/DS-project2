package edu.service;

import edu.dto.*;

/**
 * service for chat function
 * @author lingxiao
 */
public interface ChatService {

    /**
     * send chat message to server
     */
    public ChatAddResponse addChatMessage(ChatAddRequest chatAddRequest);

    /**
     * get chat message from server
     */
    public ChatGetResponse getChatMessage(ChatGetRequest chatGetRequest);

}
