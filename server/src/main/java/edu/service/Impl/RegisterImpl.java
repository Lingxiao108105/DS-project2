package edu.service.Impl;

import edu.data.ClusterInfo;
import edu.dto.ClientInfo;
import edu.dto.RegisterManagerRequest;
import edu.dto.RegisterManagerResponse;
import edu.service.Register;

public class RegisterImpl implements Register {

    @Override
    public RegisterManagerResponse registerManager(RegisterManagerRequest request){
        ClusterInfo clusterInfo = ClusterInfo.getInstance();
        if(clusterInfo.getManagerId() != null){
            return new RegisterManagerResponse(false,null);
        }else {
            ClientInfo clientInfo = clusterInfo.addManager(request.getName());
            return new RegisterManagerResponse(true,clientInfo);
        }
    }

}
