package edu.service;

import edu.dto.*;

public interface ChatService {

    public ChatAddResponse addChatMessage(ChatAddRequest chatAddRequest);

    public ChatGetResponse getChatMessage(ChatGetRequest chatGetRequest);

}
