package edu.rpc;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import edu.LifeCycle;
import edu.config.RpcClientConfig;
import edu.filters.AuthorizeFilter;
import edu.javafx.ErrorMessageGUIController;
import edu.service.*;
import javafx.application.Platform;

import java.util.List;

public class RpcClient implements LifeCycle {

    private static RpcClient rpcClient = null;
    private ConsumerConfig<Register> registerConsumerConfig = null;
    private ConsumerConfig<CanvasService> canvasServiceConsumerConfig = null;
    private ConsumerConfig<ChatService> chatServiceConsumerConfig = null;
    private ConsumerConfig<ManagerService> managerServiceConsumerConfig = null;
    private ConsumerConfig<ClusterService> clusterServiceConsumerConfig = null;


    public static RpcClient getInstance(){
        if(rpcClient == null){
            rpcClient = new RpcClient();
            rpcClient.init();
        }
        return rpcClient;
    }

    @Override
    public void init() {
        registerConsumerConfig = new ConsumerConfig<Register>()
                .setInterfaceId(Register.class.getName())
                .setProtocol("bolt")
                .setDirectUrl("bolt://" + RpcClientConfig.getConnectAddress());

        canvasServiceConsumerConfig = new ConsumerConfig<CanvasService>()
                .setInterfaceId(CanvasService.class.getName())
                .setProtocol("bolt")
                .setFilterRef(List.of(new AuthorizeFilter()))
                .setDirectUrl("bolt://" + RpcClientConfig.getConnectAddress());

        chatServiceConsumerConfig = new ConsumerConfig<ChatService>()
                .setInterfaceId(ChatService.class.getName())
                .setProtocol("bolt")
                .setFilterRef(List.of(new AuthorizeFilter()))
                .setDirectUrl("bolt://" + RpcClientConfig.getConnectAddress());

        managerServiceConsumerConfig = new ConsumerConfig<ManagerService>()
                .setInterfaceId(ManagerService.class.getName())
                .setProtocol("bolt")
                .setFilterRef(List.of(new AuthorizeFilter()))
                .setDirectUrl("bolt://" + RpcClientConfig.getConnectAddress());

        clusterServiceConsumerConfig = new ConsumerConfig<ClusterService>()
                .setInterfaceId(ClusterService.class.getName())
                .setProtocol("bolt")
                .setFilterRef(List.of(new AuthorizeFilter()))
                .setDirectUrl("bolt://" + RpcClientConfig.getConnectAddress());
    }

    @Override
    public void stop() {
        registerConsumerConfig.unRefer();
        canvasServiceConsumerConfig.unRefer();
        chatServiceConsumerConfig.unRefer();
        managerServiceConsumerConfig.unRefer();
        clusterServiceConsumerConfig.unRefer();
    }

    public Register getRegister() {
        Register register = null;
        try {
            register = registerConsumerConfig.refer();
        }
        catch (Exception e){
            Platform.runLater(()->{
                new ErrorMessageGUIController("fail to connect to server!");
            });
        }
        return register;
    }

    public CanvasService getCanvasService() {
        CanvasService canvasService = null;
        try {
            canvasService = canvasServiceConsumerConfig.refer();
        }
        catch (Exception e){
            Platform.runLater(()->{
                new ErrorMessageGUIController("fail to connect to server!");
            });
        }
        return canvasService;
    }

    public ChatService getChatService() {
        ChatService chatService = null;
        try {
            chatService = chatServiceConsumerConfig.refer();
        }
        catch (Exception e){
            Platform.runLater(()->{
                new ErrorMessageGUIController("fail to connect to server!");
            });
        }
        return chatService;
    }

    public ManagerService getManagerService() {
        ManagerService managerService = null;
        try {
            managerService = managerServiceConsumerConfig.refer();
        }
        catch (Exception e){
            Platform.runLater(()->{
                new ErrorMessageGUIController("fail to connect to server!");
            });
        }
        return managerService;
    }

    public ClusterService getClusterService() {
        ClusterService clusterService = null;
        try {
            clusterService = clusterServiceConsumerConfig.refer();
        }
        catch (Exception e){
            Platform.runLater(()->{
                new ErrorMessageGUIController("fail to connect to server!");
            });
        }
        return clusterService;
    }
}
