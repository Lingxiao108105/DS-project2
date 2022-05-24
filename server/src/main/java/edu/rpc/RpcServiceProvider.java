package edu.rpc;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import edu.LifeCycle;
import edu.config.RpcServiceConfig;
import edu.service.CanvasService;
import edu.service.Impl.CanvasServiceImpl;
import edu.service.Impl.RegisterImpl;
import edu.service.Register;

public class RpcServiceProvider implements LifeCycle {

    ProviderConfig<Register> registerProviderConfig = null;
    ProviderConfig<CanvasService> canvasServiceProviderConfig = null;

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
                .setServer(serverConfig);

        canvasServiceProviderConfig = new ProviderConfig<CanvasService>()
                .setInterfaceId(CanvasService.class.getName())
                .setRef(new CanvasServiceImpl())
                .setServer(serverConfig);

        registerProviderConfig.export();
        canvasServiceProviderConfig.export();
    }

    @Override
    public void stop() {
        if(registerProviderConfig != null){
            registerProviderConfig.unExport();
        }
        if(canvasServiceProviderConfig != null){
            canvasServiceProviderConfig.unExport();
        }
    }


}
