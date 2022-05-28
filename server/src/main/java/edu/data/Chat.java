package edu.data;

import edu.LifeCycle;
import edu.ThreadPool.ServerThreadPool;
import edu.dto.ChatMessage;
import edu.dto.ClientInfo;
import edu.rpc.RpcClient;
import edu.service.ClientUpdateService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * chat messages
 * @author lingxiao
 */
public class Chat implements LifeCycle {

    private static Chat chat = null;

    List<ChatMessage> chatMessageList;
    ReentrantLock chatMessageListLock;

    public static Chat getInstance(){
        if(chat == null){
            chat = new Chat();
            chat.init();
        }
        return chat;
    }

    @Override
    public void init() {
        this.chatMessageList = new ArrayList<>();
        this.chatMessageListLock = new ReentrantLock();
    }

    @Override
    public void stop() {
        this.chatMessageList = null;
        this.chatMessageListLock = null;
        chat = null;
    }

    /**
     * add chat message and multicast it to all accepted clients
     * @param chatMessage chat message to add
     */
    public void addChatMessage(ChatMessage chatMessage){
        chatMessageListLock.lock();
        try {
            chatMessageList.add(chatMessage);
            List<ChatMessage> chatMessages = new ArrayList<>();
            chatMessages.add(chatMessage);
            for (ClientInfo clientInfo:ClusterInfo.getInstance().getAcceptedClients()){
                ServerThreadPool.getInstance().getExecutorService().submit(()->{
                    try {
                        ClientUpdateService clientCanvasService = RpcClient.getInstance().getClientCanvasService(clientInfo);
                        clientCanvasService.updateChat(chatMessages);
                    }catch (Exception e){
                        //when fail to send chat to some client, remove it
                        System.out.println("Fail to send chat message to " + clientInfo.getAddress());
                        ClusterInfo.getInstance().removeFromAcceptedClient(clientInfo);
                    }
                });
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
