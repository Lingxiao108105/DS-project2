package edu.service;

import edu.ThreadPool.ClientThreadPool;
import edu.config.ClientConfig;
import edu.dto.CanvasUpdateRequest;
import edu.dto.ChatAddRequest;
import edu.dto.ChatMessage;

import edu.rpc.RpcClient;

public class SendChatMessageService {

    public static void sendChatMessage(ChatMessage chatMessage){
        ChatService chatService = RpcClient.getInstance().getChatService();
        ChatAddRequest chatAddRequest = new ChatAddRequest(chatMessage);
        ClientThreadPool.getInstance().getExecutorService().submit(()->{
            chatService.addChatMessage(ClientConfig.clientInfo,chatAddRequest);
        });
    }
}
