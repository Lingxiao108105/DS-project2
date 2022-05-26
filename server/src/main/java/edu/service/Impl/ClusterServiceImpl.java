package edu.service.Impl;

import edu.data.ClusterInfo;
import edu.dto.ClientInfo;
import edu.service.ClusterService;

import java.util.List;

public class ClusterServiceImpl implements ClusterService {

    @Override
    public List<ClientInfo> getAcceptedClient(){
        return ClusterInfo.getInstance().getAcceptedClients();
    }

}
