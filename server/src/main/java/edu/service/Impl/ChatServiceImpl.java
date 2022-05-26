package edu.service.Impl;

import edu.data.Chat;
import edu.dto.*;
import edu.service.ChatService;

import java.util.List;

public class ChatServiceImpl implements ChatService {

    @Override
    public ChatAddResponse addChatMessage(ClientInfo clientInfo, ChatAddRequest chatAddRequest){
        Chat chat = Chat.getInstance();
        chat.addChatMessage(chatAddRequest.getChatMessage());
        return new ChatAddResponse(true);
    }

    @Override
    public ChatGetResponse getChatMessage(ClientInfo clientInfo, ChatGetRequest chatGetRequest){
        Chat chat = Chat.getInstance();
        chat.getChatMessageListLock().lock();
        try {
            List<ChatMessage> chatMessages = chat.getChatMessages(chatGetRequest.getIndex());
            return new ChatGetResponse(chat.getIndex(),chatMessages);
        }finally {
            chat.getChatMessageListLock().unlock();
        }
    }


}
