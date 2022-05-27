package edu.rpc;


import com.alipay.sofa.rpc.config.ConsumerConfig;
import edu.LifeCycle;
import edu.service.ClientUpdateService;

import java.util.concurrent.ConcurrentHashMap;

public class RpcClient implements LifeCycle {

    private static RpcClient rpcClient = null;

    private ConcurrentHashMap<String, ConsumerConfig<ClientUpdateService>> clientUpdateConfigConcurrentHashMap;

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

    public ClientUpdateService getClientCanvasService(String address){
        ConsumerConfig<ClientUpdateService> consumerConfig = clientUpdateConfigConcurrentHashMap.get(address);
        if(consumerConfig == null){
            consumerConfig = new ConsumerConfig<ClientUpdateService>()
                .setInterfaceId(ClientUpdateService.class.getName())
                .setProtocol("bolt")
                .setRetries(3)
                .setDirectUrl("bolt://" + address);
            clientUpdateConfigConcurrentHashMap.put(address,consumerConfig);
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
