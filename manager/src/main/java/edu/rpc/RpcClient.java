package edu.rpc;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import edu.LifeCycle;
import edu.service.CanvasService;
import edu.service.Register;
import lombok.Getter;

@Getter
public class RpcClient implements LifeCycle {

    private static RpcClient rpcClient = null;
    ConsumerConfig<Register> registerConsumerConfig = null;
    ConsumerConfig<CanvasService> canvasServiceConsumerConfig = null;

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
                .setDirectUrl("bolt://127.0.0.1:10000");

        canvasServiceConsumerConfig = new ConsumerConfig<CanvasService>()
                .setInterfaceId(CanvasService.class.getName())
                .setProtocol("bolt")
                .setDirectUrl("bolt://127.0.0.1:10000");
    }

    @Override
    public void stop() {

    }
}
