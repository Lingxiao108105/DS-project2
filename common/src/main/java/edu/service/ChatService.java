package edu.service;

import edu.dto.*;

public interface ChatService {

    public ChatAddResponse addChatMessage(ClientInfo clientInfo, ChatAddRequest chatAddRequest);

    public ChatGetResponse getChatMessage(ClientInfo clientInfo, ChatGetRequest chatGetRequest);

}
