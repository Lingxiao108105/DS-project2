package edu.service.Impl;

import edu.data.ClusterInfo;
import edu.dto.ClientInfo;
import edu.rpc.RpcClient;
import edu.service.ClientUpdateService;
import edu.service.ManagerService;

public class ManagerServiceImpl implements ManagerService {

    @Override
    public void joinRequestDecision(boolean decision, ClientInfo clientInfo){
        //sanity check
        if(clientInfo == null){
            return;
        }

        //client not in wait list(maybe exit)
        if(!ClusterInfo.getInstance().isWaitListClient(clientInfo)){
            return;
        }

        if(decision){
            ClusterInfo.getInstance().acceptClient(clientInfo);
        }else {
           ClusterInfo.getInstance().deny(clientInfo);
        }

        //notify the client
        ClientUpdateService clientCanvasService = RpcClient.getInstance()
                .getClientCanvasService(clientInfo.getAddress());
        clientCanvasService.joinRequestDecision(decision);
    }

    @Override
    public void kickClient(ClientInfo clientInfo){
        //sanity check
        if(clientInfo == null){
            return;
        }
        //client not in accepted list
        if(!ClusterInfo.getInstance().isAcceptedClient(clientInfo)){
            return;
        }

        ClusterInfo.getInstance().kick(clientInfo);
    }

}
