package edu.service.Impl;

import edu.ThreadPool.ClientThreadPool;
import edu.config.ClientConfig;
import edu.dto.CanvasUpdateRequest;
import edu.dto.Command;
import edu.rpc.RpcClient;
import edu.service.CanvasService;

public class SendCommandService {

    public static void sendCommand(Command command){
        CanvasService canvasService = RpcClient.getInstance().getCanvasService();
        CanvasUpdateRequest canvasUpdateRequest = new CanvasUpdateRequest(command);
        ClientThreadPool.getInstance().getExecutorService().submit(()->{
            canvasService.canvasUpdate(canvasUpdateRequest);
        });
    }
}
