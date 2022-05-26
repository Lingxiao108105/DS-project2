package edu.service;

import edu.dto.ClientInfo;

public interface ManagerService {

    public ClientInfo getClientInWaitList();

    public void joinRequestDecision (boolean decision, ClientInfo clientInfo);

    public void kickClient (ClientInfo clientInfo);


}
