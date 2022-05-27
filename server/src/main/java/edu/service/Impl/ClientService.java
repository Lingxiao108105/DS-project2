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
                try {
                    ClientUpdateService clientCanvasService = RpcClient.getInstance().getClientCanvasService(clientInfo);
                    clientCanvasService.updateAcceptedClient(acceptedClients);
                } catch (Exception e) {
                    //when fail to send chat to some client, remove it
                    System.out.println("Fail to send chat message to " + clientInfo.getAddress());
                    ClusterInfo.getInstance().removeFromAcceptedClient(clientInfo);
                }
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
                    try {
                        ClientUpdateService clientCanvasService = RpcClient.getInstance().getClientCanvasService(
                                clientInfo);
                        clientCanvasService.updateClientCanvas(imageToBytes);
                    } catch (Exception e) {
                        //when fail to send chat to some client, remove it
                        System.out.println("Fail to send chat message to " + clientInfo.getAddress());
                        ClusterInfo.getInstance().removeFromAcceptedClient(clientInfo);
                    }
                });
            }
        }
        finally {
            MyCanvas.getInstance().getSnapshotLock().unlock();
        }
    }

}
