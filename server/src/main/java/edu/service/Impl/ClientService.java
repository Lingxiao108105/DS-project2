package edu.service.Impl;

import edu.ThreadPool.ServerThreadPool;
import edu.common.util.ByteAndImageConverterUtil;
import edu.data.ClusterInfo;
import edu.dto.ClientInfo;
import edu.javafx.MyCanvas;
import edu.rpc.RpcClient;
import edu.service.ClientUpdateService;

import java.util.List;

public class ClientService {

    public static void notifyAcceptedClientChange(){
        //notify all acceptedClients
        List<ClientInfo> acceptedClients = ClusterInfo.getInstance().getAcceptedClients();
        for(ClientInfo clientInfo: acceptedClients){
            ServerThreadPool.getInstance().getExecutorService().submit(()->{
                ClientUpdateService clientCanvasService = RpcClient.getInstance().getClientCanvasService(clientInfo.getAddress());
                clientCanvasService.updateAcceptedClient(acceptedClients);
            });
        }
    }

    public static void multicastSnapshot(){
        MyCanvas myCanvas = MyCanvas.getInstance();
        myCanvas.getSnapshotLock().lock();
        try {
            byte[] imageToBytes = ByteAndImageConverterUtil.imageToBytes(myCanvas.getCanvas().snapshot(null, null));
            myCanvas.setSnapshotBytes(imageToBytes);
            myCanvas.incrementSnapshotIndex();
            for (ClientInfo clientInfo: ClusterInfo.getInstance().getAcceptedClients()){
                ServerThreadPool.getInstance().getExecutorService().submit(()->{
                    ClientUpdateService clientCanvasService = RpcClient.getInstance().getClientCanvasService(
                            clientInfo.getAddress());
                    clientCanvasService.updateClientCanvas(imageToBytes);
                });
            }
        }
        finally {
            MyCanvas.getInstance().getSnapshotLock().unlock();
        }
    }

}
