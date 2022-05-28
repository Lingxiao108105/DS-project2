package edu.service;

import edu.dto.ClientInfo;

import java.util.List;

/**
 * service for client to act on cluster
 * @author lingxiao
 */
public interface ClusterService {

    public List<ClientInfo> getAcceptedClient();

    public void leave(ClientInfo clientInfo);

}
