package edu.service;

import edu.ThreadPool.ClientThreadPool;
import edu.config.ClientConfig;
import edu.dto.CanvasUpdateRequest;
import edu.dto.Command;
import edu.rpc.RpcClient;

public class SendCommandService {

    public static void sendCommand(Command command){
        CanvasService canvasService = RpcClient.getInstance().getCanvasService();
        CanvasUpdateRequest canvasUpdateRequest = new CanvasUpdateRequest(command);
        ClientThreadPool.getInstance().getExecutorService().submit(()->{
            canvasService.canvasUpdate(ClientConfig.clientInfo,canvasUpdateRequest);
        });
    }
}
