package edu.service.Impl;

import edu.Main;
import edu.ThreadPool.ClientThreadPool;
import edu.common.enums.Role;
import edu.common.util.ByteAndImageConverterUtil;
import edu.config.ClientConfig;
import edu.config.RpcServiceConfig;
import edu.dto.*;
import edu.javafx.ExceptionMessageGUIController;
import edu.javafx.WaitForApproveGUIController;
import edu.rpc.RpcClient;
import edu.service.CanvasService;
import edu.service.ChatService;
import edu.service.Register;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * communicate with server
 */
public class ServerService {

    public static void register(String name){
        Register register = RpcClient.getInstance().getRegister();
        if(ClientConfig.role == Role.MANAGER){
            RegisterManagerResponse registerManagerResponse = register.registerManager(
                    new RegisterManagerRequest(name, RpcServiceConfig.getConnectAddress()));
            if(registerManagerResponse == null){
                return;
            }
            ClientConfig.clientInfo = registerManagerResponse.getClientInfo();
            Main.loadCanvas();

        }else if(ClientConfig.role == Role.CLIENT){
            RegisterClientResponse registerClientResponse = register.registerClient(
                    new RegisterClientRequest(name, RpcServiceConfig.getConnectAddress()));
            if(registerClientResponse == null){
                return;
            }
            ClientConfig.clientInfo = registerClientResponse.getClientInfo();
            Platform.runLater(WaitForApproveGUIController::waitForApprove);
        }
    }

    public static void sendChatMessage(ChatMessage chatMessage){
        ChatService chatService = RpcClient.getInstance().getChatService();
        ChatAddRequest chatAddRequest = new ChatAddRequest(chatMessage);
        ClientThreadPool.getInstance().getExecutorService().submit(()->{
            chatService.addChatMessage(chatAddRequest);
        });
    }

    public static void sendCommand(Command command){
        CanvasService canvasService = RpcClient.getInstance().getCanvasService();
        CanvasUpdateRequest canvasUpdateRequest = new CanvasUpdateRequest(command);
        ClientThreadPool.getInstance().getExecutorService().submit(()->{
            canvasService.canvasUpdate(canvasUpdateRequest);
        });
    }

    public static WritableImage getCanvas() {
        CanvasResponse canvasResponse = RpcClient.getInstance().getCanvasService().getCanvas(new CanvasRequest(-1));
        WritableImage writableImage = ByteAndImageConverterUtil.bytesToImage(canvasResponse.getImageBytes());
        return writableImage;
    }

    public static void sendBufferedImage(BufferedImage bufferedImage) {
        WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);
        sendWritableImage(writableImage);
    }

    public static void sendWritableImage(WritableImage writableImage) {
        byte[] bytes = ByteAndImageConverterUtil.imageToBytes(writableImage);
        if(bytes != null){
            RpcClient.getInstance().getManagerService().updateCanvas(bytes);
        }
    }


    public static List<ChatMessage> getChat() {
        ChatService chatService = RpcClient.getInstance().getChatService();
        ChatGetRequest chatGetRequest = new ChatGetRequest(0);
        ChatGetResponse chatGetResponse = chatService.getChatMessage(chatGetRequest);
        List<ChatMessage> chatMessageList = new ArrayList<>();
        if(chatGetResponse == null){
            return chatMessageList;
        }
        chatMessageList = chatGetResponse.getChatMessageList();
        return chatMessageList;
    }

    public static void sendJoinRequestDecision(boolean decision,ClientInfo clientInfo){
        RpcClient.getInstance().getManagerService().joinRequestDecision(decision,clientInfo);
    }

    public static void leave(){
        RpcClient.getInstance().getClusterService().leave(ClientConfig.clientInfo);
    }

    public static List<ClientInfo> getAcceptedClient(){
        List<ClientInfo> acceptedClient = RpcClient.getInstance().getClusterService().getAcceptedClient();
        return acceptedClient;
    }

    public static void kickClient(ClientInfo clientInfo){
        //try to kick itself
        if(clientInfo.equals(ClientConfig.clientInfo)){
            Platform.runLater(()->{
                new ExceptionMessageGUIController("Cannot kick yourself!");
            });
        }else {
            RpcClient.getInstance().getManagerService().kickClient(clientInfo);
        }
    }

}
