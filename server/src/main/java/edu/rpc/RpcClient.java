package edu.rpc;


import com.alipay.sofa.rpc.config.ConsumerConfig;
import edu.LifeCycle;
import edu.dto.ClientInfo;
import edu.service.ClientUpdateService;

import java.util.concurrent.ConcurrentHashMap;

public class RpcClient implements LifeCycle {

    private static RpcClient rpcClient = null;

    private ConcurrentHashMap<Integer, ConsumerConfig<ClientUpdateService>> clientUpdateConfigConcurrentHashMap;

    public static RpcClient getInstance(){
        if(rpcClient == null){
            rpcClient = new RpcClient();
            rpcClient.init();
        }
        return rpcClient;
    }

    @Override
    public void init() {
        this.clientUpdateConfigConcurrentHashMap = new ConcurrentHashMap<>();
    }

    @Override
    public void stop() {
        this.clientUpdateConfigConcurrentHashMap = null;
        rpcClient = null;
    }

    public ClientUpdateService getClientCanvasService(ClientInfo clientInfo){
        ConsumerConfig<ClientUpdateService> consumerConfig = clientUpdateConfigConcurrentHashMap.get(clientInfo.getId());
        if(consumerConfig == null){
            consumerConfig = new ConsumerConfig<ClientUpdateService>()
                    .setInterfaceId(ClientUpdateService.class.getName())
                    .setProtocol("bolt")
                    .setRepeatedReferLimit(3)
                    .setDirectUrl("bolt://" + clientInfo.getAddress());
            clientUpdateConfigConcurrentHashMap.put(clientInfo.getId(),consumerConfig);
        }
        ClientUpdateService clientUpdateService = null;
        try {
            clientUpdateService = consumerConfig.refer();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("fail to connect to server: " + consumerConfig.getAddressHolder());
        }
        return clientUpdateService;
    }

}
