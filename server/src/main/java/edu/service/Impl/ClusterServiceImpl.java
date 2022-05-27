package edu.service.Impl;

import edu.Main;
import edu.ThreadPool.ServerThreadPool;
import edu.data.ClusterInfo;
import edu.dto.ClientInfo;
import edu.rpc.RpcClient;
import edu.service.ClientUpdateService;
import edu.service.ClusterService;

import java.util.List;


public class ClusterServiceImpl implements ClusterService {

    @Override
    public List<ClientInfo> getAcceptedClient(){
        List<ClientInfo> acceptedClients = ClusterInfo.getInstance().getAcceptedClients();
        return acceptedClients;
    }

    @Override
    public void leave(ClientInfo clientInfo) {
        //sanity check
        if(clientInfo == null || clientInfo.getId() == null){
            return;
        }
        //client not in accepted list or wait list
        if(!ClusterInfo.getInstance().isAcceptedClient(clientInfo)
                && !ClusterInfo.getInstance().isWaitListClient(clientInfo)){
            return;
        }

        //remove the client from accepted list or wait list
        ClusterInfo.getInstance().removeFromAcceptedClient(clientInfo);
        ClusterInfo.getInstance().removeFromWaitListClient(clientInfo);

        //check whether the client is the manager
        if(clientInfo.equals(ClusterInfo.getInstance().getManager())){
            //manager want to leave
            closeAllPeer();
            Main.restartCanvas();
        }else {
            //notify the cluster change
            ClientService.notifyAcceptedClientChange();
        }
    }

    /**
     * close all peer when server exit or manager exit
     */
    public static void closeAllPeer(){
        List<ClientInfo> activeClients = ClusterInfo.getInstance().getAcceptedClients();
        for(ClientInfo clientInfo: activeClients){
            ClientUpdateService clientCanvasService = RpcClient.getInstance().getClientCanvasService(clientInfo.getAddress());
            clientCanvasService.notifyManagerExit();
        }

        List<ClientInfo> waitListClients = ClusterInfo.getInstance().getWaitListClients();
        for(ClientInfo clientInfo: waitListClients){
            ClientUpdateService clientCanvasService = RpcClient.getInstance().getClientCanvasService(clientInfo.getAddress());
            clientCanvasService.notifyManagerExit();
        }
    }
}
