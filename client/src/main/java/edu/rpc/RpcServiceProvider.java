package edu.rpc;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import edu.LifeCycle;
import edu.config.RpcServiceConfig;
import edu.service.ClientUpdateService;
import edu.service.Impl.ClientUpdateServiceImpl;

/**
 * RpcServiceProvider to provide rpc service for server to update the state of client
 * @author lingxiao
 */
public class RpcServiceProvider implements LifeCycle {

    private static RpcServiceProvider rpcServiceProvider;

    private ProviderConfig<ClientUpdateService> clientCanvasServiceProviderConfig = null;

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
                .setDaemon(true)
                //auto increment port when current port is busy
                .setAdaptivePort(true);


        clientCanvasServiceProviderConfig = new ProviderConfig<ClientUpdateService>()
                .setInterfaceId(ClientUpdateService.class.getName())
                .setRef(new ClientUpdateServiceImpl())
                .setServer(serverConfig);

        clientCanvasServiceProviderConfig.export();

        //set the real port
        RpcServiceConfig.setRegisterPort(serverConfig.getPort());
    }

    @Override
    public void stop() {
        if(clientCanvasServiceProviderConfig != null){
            clientCanvasServiceProviderConfig.unExport();
        }
    }
}
