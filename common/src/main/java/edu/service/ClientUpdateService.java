package edu.service;

import edu.dto.ChatMessage;
import edu.dto.ClientInfo;

import java.util.List;

/**
 * service for server to update states of client
 * @author lingxiao
 */
public interface ClientUpdateService {

    public void updateClientCanvas(byte[] imageBytes);

    public void updateChat(List<ChatMessage> chatMessageList);

    public void updateAcceptedClient(List<ClientInfo> clientInfoList);

    public void joinRequestDecision (boolean decision);

    public void notifyManagerJoinRequest(ClientInfo clientInfo);

    public void notifyBeenKicked();

    public void notifyManagerExit();

    public void alive();
}
