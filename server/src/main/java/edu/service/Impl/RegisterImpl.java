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
        if(clusterInfo.getManager() != null){
            return new RegisterManagerResponse(false,null);
        }else {
            ClientInfo clientInfo = clusterInfo.addManager(request.getName(),request.getAddress());
            return new RegisterManagerResponse(true,clientInfo);
        }
    }

    @Override
    public RegisterClientResponse registerClient(RegisterClientRequest request){
        ClusterInfo clusterInfo = ClusterInfo.getInstance();
        ClientInfo clientInfo = clusterInfo.requestToJoin(request.getName(),request.getAddress());
        if(clientInfo != null){
            //notify manager
            ClientUpdateService clientCanvasService = RpcClient.getInstance()
                    .getClientCanvasService(ClusterInfo.getInstance().getManager().getAddress());
            clientCanvasService.notifyManagerJoinRequest(clientInfo);
        }
        return new RegisterClientResponse(true,clientInfo);
    }

}
