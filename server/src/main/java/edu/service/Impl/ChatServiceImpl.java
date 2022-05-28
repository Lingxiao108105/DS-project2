package edu.service.Impl;

import edu.data.Chat;
import edu.dto.*;
import edu.service.ChatService;

import java.util.List;

/**
 * service for chat function
 * @author lingxiao
 */
public class ChatServiceImpl implements ChatService {

    /**
     * send chat message to server
     */
    @Override
    public ChatAddResponse addChatMessage(ChatAddRequest chatAddRequest){
        Chat chat = Chat.getInstance();
        chat.addChatMessage(chatAddRequest.getChatMessage());
        return new ChatAddResponse(true);
    }

    /**
     * get chat message from server
     */
    @Override
    public ChatGetResponse getChatMessage(ChatGetRequest chatGetRequest){
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
