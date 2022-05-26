package edu.service;

import edu.common.enums.Role;
import edu.config.ClientConfig;
import edu.dto.ClientInfo;
import edu.dto.RegisterManagerRequest;
import edu.dto.RegisterManagerResponse;
import edu.rpc.RpcClient;

public class RegisterService {

    public static void register(String name){
        ClientConfig.clientInfo = new ClientInfo(-1,name);
        Register register = RpcClient.getInstance().getRegister();
        if(ClientConfig.role == Role.MANAGER){
            RegisterManagerResponse registerManagerResponse = register.registerManager(new RegisterManagerRequest(name));
            if(registerManagerResponse == null){
                return;
            }
            ClientConfig.clientInfo = registerManagerResponse.getClientInfo();
        }
    }
}
