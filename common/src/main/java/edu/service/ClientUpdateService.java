package edu.service;

import edu.dto.ChatMessage;
import edu.dto.ClientInfo;

import java.util.List;

public interface ClientUpdateService {

    public void updateClientCanvas(byte[] imageBytes);

    public void updateChat(List<ChatMessage> chatMessageList);

    public void joinRequestDecision (boolean decision);

    public void notifyManagerJoinRequest(ClientInfo clientInfo);
}
