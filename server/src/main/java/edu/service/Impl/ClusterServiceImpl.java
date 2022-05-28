package edu.service.Impl;

import edu.data.ClusterInfo;
import edu.dto.ClientInfo;
import edu.rpc.RpcClient;
import edu.service.ClientUpdateService;
import edu.service.ClusterService;

import java.util.List;

/**
 * service for client to act on cluster
 * @author lingxiao
 */
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
        if(ClusterInfo.getInstance().getManager() != null){
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
            try {
                ClientUpdateService clientCanvasService = RpcClient.getInstance().getClientCanvasService(clientInfo);
                clientCanvasService.notifyManagerExit();
            }catch (Exception e){
                //when fail to send chat to some client, remove it
                System.out.println("Fail to send chat message to " + clientInfo.getAddress());
                ClusterInfo.getInstance().removeFromAcceptedClient(clientInfo);
            }

        }

        List<ClientInfo> waitListClients = ClusterInfo.getInstance().getWaitListClients();
        for(ClientInfo clientInfo: waitListClients){
            try {
                ClientUpdateService clientCanvasService = RpcClient.getInstance().getClientCanvasService(clientInfo);
                clientCanvasService.notifyManagerExit();
            } catch (Exception e) {
                //when fail to send chat to some client, remove it
                System.out.println("Fail to send chat message to " + clientInfo.getAddress());
                ClusterInfo.getInstance().removeFromWaitListClient(clientInfo);
            }
        }
    }
}
