package edu.service.Impl;

import edu.ThreadPool.ServerThreadPool;
import edu.common.util.ByteAndImageConverterUtil;
import edu.data.ClusterInfo;
import edu.dto.ClientInfo;
import edu.javafx.MyCanvas;
import edu.rpc.RpcClient;
import edu.service.ClientUpdateService;
import edu.service.ManagerService;
import javafx.application.Platform;
import javafx.scene.image.WritableImage;

public class ManagerServiceImpl implements ManagerService {

    @Override
    public void joinRequestDecision(boolean decision, ClientInfo clientInfo){
        //sanity check
        if(clientInfo == null){
            return;
        }

        //client not in wait list(maybe exit)
        if(!ClusterInfo.getInstance().isWaitListClient(clientInfo)){
            return;
        }

        if(decision){
            ClusterInfo.getInstance().acceptClient(clientInfo);
            //notify the cluster change
            ClientService.notifyAcceptedClientChange();
        }else {
           ClusterInfo.getInstance().deny(clientInfo);
        }

        //notify the client
        ClientUpdateService clientCanvasService = RpcClient.getInstance()
                .getClientCanvasService(clientInfo.getAddress());
        clientCanvasService.joinRequestDecision(decision);
    }

    @Override
    public void kickClient(ClientInfo clientInfo){
        //sanity check
        if(clientInfo == null || clientInfo.getId() == null){
            return;
        }
        //client not in accepted list
        if(!ClusterInfo.getInstance().isAcceptedClient(clientInfo)){
            return;
        }

        //cannot kick manager
        if(ClusterInfo.getInstance().getManager().equals(clientInfo)){
            return;
        }

        ClusterInfo.getInstance().kick(clientInfo);

        //notify the cluster change
        ClientService.notifyAcceptedClientChange();

        //notify the kicked client
        ClientUpdateService clientCanvasService = RpcClient.getInstance()
                .getClientCanvasService(clientInfo.getAddress());
        clientCanvasService.notifyBeenKicked();
    }

    /**
     * manager open a new file and update to server
     * need to send new canvas to all the server
     * @param imageBytes
     */
    @Override
    public void updateCanvas(byte[] imageBytes) {
        Platform.runLater(() ->{
            WritableImage writableImage = ByteAndImageConverterUtil.bytesToImage(imageBytes);
            if (writableImage != null){
                MyCanvas.getInstance().getCanvas().getGraphicsContext2D().drawImage(writableImage,0,0);
            }
            ClientService.multicastSnapshot();
        });
    }

}
