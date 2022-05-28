package edu.service;

import edu.dto.ClientInfo;

/**
 * service for manager to manage the cluster
 * @author lingxiao
 */
public interface ManagerService {

    public void joinRequestDecision (boolean decision, ClientInfo clientInfo);

    public void kickClient (ClientInfo clientInfo);

    public void updateCanvas(byte[] imageBytes);


}
