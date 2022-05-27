package edu.service;

import edu.dto.ClientInfo;

import java.util.List;

public interface ClusterService {

    public List<ClientInfo> getAcceptedClient();

    public void leave(ClientInfo clientInfo);

}
