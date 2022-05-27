package edu.rpc;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import edu.LifeCycle;
import edu.config.RpcServiceConfig;
import edu.security.ClientFilter;
import edu.security.ManagerFilter;
import edu.service.*;
import edu.service.Impl.*;

import java.util.List;

public class RpcServiceProvider implements LifeCycle {

    private static RpcServiceProvider rpcServiceProvider;

    private ProviderConfig<Register> registerProviderConfig = null;
    private ProviderConfig<CanvasService> canvasServiceProviderConfig = null;
    private ProviderConfig<ChatService> chatServiceProviderConfig = null;
    private ProviderConfig<ClusterService> clientServiceProviderConfig = null;
    private ProviderConfig<ManagerService> managerServiceProviderConfig = null;

    public static RpcServiceProvider getInstance(){
        if(rpcServiceProvider == null){
            rpcServiceProvider = new RpcServiceProvider();
            rpcServiceProvider.init();
        }
        return rpcServiceProvider;
    }

    @Override
    public void init() {
        //init register
        ServerConfig serverConfig = new ServerConfig()
                .setProtocol("bolt")
                .setPort(RpcServiceConfig.getRegisterPort())
                .setDaemon(true);

        registerProviderConfig = new ProviderConfig<Register>()
                .setInterfaceId(Register.class.getName())
                .setRef(new RegisterImpl())
                .setServer(serverConfig);

        canvasServiceProviderConfig = new ProviderConfig<CanvasService>()
                .setInterfaceId(CanvasService.class.getName())
                .setRef(new CanvasServiceImpl())
                .setFilterRef(List.of(new ClientFilter()))
                .setServer(serverConfig);

        chatServiceProviderConfig = new ProviderConfig<ChatService>()
                .setInterfaceId(ChatService.class.getName())
                .setRef(new ChatServiceImpl())
                .setFilterRef(List.of(new ClientFilter()))
                .setServer(serverConfig);

        clientServiceProviderConfig = new ProviderConfig<ClusterService>()
                .setInterfaceId(ClusterService.class.getName())
                .setRef(new ClusterServiceImpl())
                .setFilterRef(List.of(new ClientFilter()))
                .setServer(serverConfig);

        managerServiceProviderConfig = new ProviderConfig<ManagerService>()
                .setInterfaceId(ManagerService.class.getName())
                .setRef(new ManagerServiceImpl())
                .setFilterRef(List.of(new ManagerFilter()))
                .setServer(serverConfig);

        registerProviderConfig.export();
        canvasServiceProviderConfig.export();
        chatServiceProviderConfig.export();
        clientServiceProviderConfig.export();
        managerServiceProviderConfig.export();
    }

    @Override
    public void stop() {
        if(registerProviderConfig != null){
            registerProviderConfig.unExport();
        }
        if(canvasServiceProviderConfig != null){
            canvasServiceProviderConfig.unExport();
        }
        if(chatServiceProviderConfig != null){
            chatServiceProviderConfig.unExport();
        }
        if(clientServiceProviderConfig != null){
            clientServiceProviderConfig.unExport();
        }
        if(managerServiceProviderConfig != null){
            managerServiceProviderConfig.unExport();
        }
        rpcServiceProvider = null;
    }


}
