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
                return new RegisterManagerResponse(true,null,clientInfo);
            }
            return new RegisterManagerResponse(false,"Manager already exist!",null);
        }else {
            ClientInfo clientInfo = clusterInfo.addManager(request.getName(),request.getAddress());
            return new RegisterManagerResponse(true,null,clientInfo);
        }
    }

    @Override
    public RegisterClientResponse registerClient(RegisterClientRequest request){
        //sanity check
        if(request == null || request.getAddress() == null || request.getName() == null){
            return new RegisterClientResponse(false,"RegisterClientRequest is null or attribute in it is null!",null);
        }

        ClusterInfo clusterInfo = ClusterInfo.getInstance();

        //check whether the old client using same address crashed
        clusterInfo.removeClientWithAddress(request.getAddress());
        //clusterInfo maybe reset
        clusterInfo = ClusterInfo.getInstance();

        //no manager
        if(clusterInfo.getManager() == null){
            return new RegisterClientResponse(false,"There is no manager in the whiteboard!",null);
        }

        ClientInfo clientInfo = clusterInfo.requestToJoin(request.getName(),request.getAddress());

        if(clientInfo != null){
            try {
                //notify manager
                ClientUpdateService clientCanvasService = RpcClient.getInstance()
                        .getClientCanvasService(ClusterInfo.getInstance().getManager());
                clientCanvasService.notifyManagerJoinRequest(clientInfo);
            } catch (Exception e) {
                //when fail to connect to manager
                System.out.println("Fail to send chat message to manager: " + ClusterInfo.getInstance().getManager());
                ClusterInfo.getInstance().removeFromAcceptedClient(ClusterInfo.getInstance().getManager());
                return new RegisterClientResponse(false,"Manager crashed!",null);
            }
        }else {
            return new RegisterClientResponse(false,"Duplicate name! Please use a new User name!",clientInfo);
        }
        return new RegisterClientResponse(true,null,clientInfo);
    }

}
