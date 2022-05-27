package edu.rpc;


import com.alipay.sofa.rpc.config.ConsumerConfig;
import edu.LifeCycle;
import edu.service.ClientUpdateService;

import java.util.concurrent.ConcurrentHashMap;

public class RpcClient implements LifeCycle {

    private static RpcClient rpcClient = null;

    private ConcurrentHashMap<String, ClientUpdateService> clientCanvasServiceConcurrentHashMap;

    public static RpcClient getInstance(){
        if(rpcClient == null){
            rpcClient = new RpcClient();
            rpcClient.init();
        }
        return rpcClient;
    }

    @Override
    public void init() {
        this.clientCanvasServiceConcurrentHashMap = new ConcurrentHashMap<>();
    }

    @Override
    public void stop() {
        this.clientCanvasServiceConcurrentHashMap = null;
        rpcClient = null;
    }

    public ClientUpdateService getClientCanvasService(String address){
        ClientUpdateService clientCanvasService = clientCanvasServiceConcurrentHashMap.get(address);
        if(clientCanvasService != null){
            return clientCanvasService;
        }
        ConsumerConfig<ClientUpdateService> consumerConfig = new ConsumerConfig<ClientUpdateService>()
                .setInterfaceId(ClientUpdateService.class.getName())
                .setProtocol("bolt")
                .setDirectUrl("bolt://" + address);
        try {
            clientCanvasService = consumerConfig.refer();
            clientCanvasServiceConcurrentHashMap.put(address,clientCanvasService);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("fail to connect to server: " + consumerConfig.getAddressHolder());
        }
        return clientCanvasService;
    }

}
