package edu.service.Impl;

import edu.ThreadPool.ClientThreadPool;
import edu.dto.ChatAddRequest;
import edu.dto.ChatMessage;

import edu.rpc.RpcClient;
import edu.service.ChatService;

public class SendChatMessageService {

    public static void sendChatMessage(ChatMessage chatMessage){
        ChatService chatService = RpcClient.getInstance().getChatService();
        ChatAddRequest chatAddRequest = new ChatAddRequest(chatMessage);
        ClientThreadPool.getInstance().getExecutorService().submit(()->{
            chatService.addChatMessage(chatAddRequest);
        });
    }
}
