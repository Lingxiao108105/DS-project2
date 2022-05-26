package edu.data;

import edu.dto.ChatMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Chat {

    private static Chat chat = null;

    List<ChatMessage> chatMessageList = new ArrayList<>();
    ReentrantLock chatMessageListLock = new ReentrantLock();

    public static Chat getInstance(){
        if(chat == null){
            chat = new Chat();
        }
        return chat;
    }

    public void addChatMessage(ChatMessage chatMessage){
        chatMessageListLock.lock();
        try {
            chatMessageList.add(chatMessage);
            if(chatMessageList.size() > 0){
                System.out.println(Arrays.toString(chatMessageList.toArray()));
            }
        }finally {
            chatMessageListLock.unlock();
        }
    }

    /**
     * get chat massages from index to last
     * please lock the chatMessageListLock
     */
    public List<ChatMessage> getChatMessages(int index){
        return chatMessageList.subList(index,chatMessageList.size());
    }

    public int getIndex(){
        return chatMessageList.size();
    }

    public ReentrantLock getChatMessageListLock() {
        return chatMessageListLock;
    }
}
