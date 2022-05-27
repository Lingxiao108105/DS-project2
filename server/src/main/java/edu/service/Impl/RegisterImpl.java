package edu.service.Impl;

import edu.data.ClusterInfo;
import edu.dto.*;
import edu.rpc.RpcClient;
import edu.service.ClientUpdateService;
import edu.service.Register;

public class RegisterImpl implements Register {

    @Override
    public RegisterManagerResponse registerManager(RegisterManagerRequest request){
        ClusterInfo clusterInfo = ClusterInfo.getInstance();

        //check whether the old client using same address crashed
        clusterInfo.removeClientWithAddress(request.getAddress());
        //clusterInfo maybe reset
        clusterInfo = ClusterInfo.getInstance();


        if(clusterInfo.getManager() != null){
            try {
                //check whether manager is alive
                ClientUpdateService clientCanvasService = RpcClient.getInstance()
                        .getClientCanvasService(ClusterInfo.getInstance().getManager());
                clientCanvasService.alive();
            } catch (Exception e) {
                //when fail to send chat to manager, remove it
                System.out.println("Fail to send chat message to manager: " + ClusterInfo.getInstance().getManager().getAddress());
                ClusterInfo.getInstance().removeFromAcceptedClient(clusterInfo.getManager());
                ClientInfo clientInfo = clusterInfo.addManager(request.getName(),request.getAddress());
                return new RegisterManagerResponse(true,clientInfo);
            }
            return new RegisterManagerResponse(false,null);
        }else {
            ClientInfo clientInfo = clusterInfo.addManager(request.getName(),request.getAddress());
            return new RegisterManagerResponse(true,clientInfo);
        }
    }

    @Override
    public RegisterClientResponse registerClient(RegisterClientRequest request){
        //sanity check
        if(request == null || request.getAddress() == null || request.getName() == null){
            return new RegisterClientResponse(true,null);
        }

        ClusterInfo clusterInfo = ClusterInfo.getInstance();

        //check whether the old client using same address crashed
        clusterInfo.removeClientWithAddress(request.getAddress());
        //clusterInfo maybe reset
        clusterInfo = ClusterInfo.getInstance();

        //no manager
        if(clusterInfo.getManager() == null){
            return new RegisterClientResponse(false,null);
        }

        ClientInfo clientInfo = clusterInfo.requestToJoin(request.getName(),request.getAddress());

        if(clientInfo != null){
            try {
                //notify manager
                ClientUpdateService clientCanvasService = RpcClient.getInstance()
                        .getClientCanvasService(ClusterInfo.getInstance().getManager());
                clientCanvasService.notifyManagerJoinRequest(clientInfo);
            } catch (Exception e) {
                //when fail to send chat to some client, remove it
                System.out.println("Fail to send chat message to " + clientInfo.getAddress());
                ClusterInfo.getInstance().removeFromAcceptedClient(clientInfo);
                return new RegisterClientResponse(false,null);
            }
        }
        return new RegisterClientResponse(true,clientInfo);
    }

}
