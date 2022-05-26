package edu.rpc;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import edu.LifeCycle;
import edu.config.RpcServiceConfig;
import edu.javafx.MyCanvas;
import edu.security.ClientFilter;
import edu.service.CanvasService;
import edu.service.ChatService;
import edu.service.Impl.CanvasServiceImpl;
import edu.service.Impl.ChatServiceImpl;
import edu.service.Impl.RegisterImpl;
import edu.service.Register;

import java.util.List;

public class RpcServiceProvider implements LifeCycle {

    private static RpcServiceProvider rpcServiceProvider;

    private ProviderConfig<Register> registerProviderConfig = null;
    private ProviderConfig<CanvasService> canvasServiceProviderConfig = null;
    private ProviderConfig<ChatService> chatServiceProviderConfig = null;

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
                .setDaemon(false);

        registerProviderConfig = new ProviderConfig<Register>()
                .setInterfaceId(Register.class.getName())
                .setRef(new RegisterImpl())
                .setFilterRef(List.of(new ClientFilter()))
                .setServer(serverConfig);

        canvasServiceProviderConfig = new ProviderConfig<CanvasService>()
                .setInterfaceId(CanvasService.class.getName())
                .setRef(new CanvasServiceImpl())
                //.setFilterRef(List.of(new ClientFilter()))
                .setServer(serverConfig);

        chatServiceProviderConfig = new ProviderConfig<ChatService>()
                .setInterfaceId(ChatService.class.getName())
                .setRef(new ChatServiceImpl())
                //.setFilterRef(List.of(new ClientFilter()))
                .setServer(serverConfig);

        registerProviderConfig.export();
        canvasServiceProviderConfig.export();
        chatServiceProviderConfig.export();
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
    }


}
