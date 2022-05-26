package edu.service;

import edu.common.enums.Role;
import edu.config.ClientConfig;
import edu.dto.RegisterManagerRequest;
import edu.dto.RegisterManagerResponse;
import edu.rpc.RpcClient;

public class RegisterService {

    public static void register(String name){
        Register register = RpcClient.getInstance().getRegister();
        if(ClientConfig.role == Role.MANAGER){
            RegisterManagerResponse registerManagerResponse = register.registerManager(new RegisterManagerRequest(name));
            ClientConfig.clientInfo = registerManagerResponse.getClientInfo();
        }
    }
}
