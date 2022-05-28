package edu.service.Impl;

import edu.ClientJavaFXApplication;
import edu.ThreadPool.ClientThreadPool;
import edu.common.ErrorMessage;
import edu.common.enums.Role;
import edu.common.exception.AuthorizeException;
import edu.common.util.ByteAndImageConverterUtil;
import edu.config.ClientConfig;
import edu.config.RpcServiceConfig;
import edu.dto.*;
import edu.javafx.ErrorMessageGUIController;
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
        try {
            Register register = RpcClient.getInstance().getRegister();
            if(ClientConfig.role == Role.MANAGER){
                RegisterManagerResponse registerManagerResponse = register.registerManager(
                        new RegisterManagerRequest(name, RpcServiceConfig.getConnectAddress()));
                if(registerManagerResponse == null){
                    return;
                }
                //not success: means manager already exist
                if(!registerManagerResponse.isSuccess()){
                    Platform.runLater(()->{
                        new ErrorMessageGUIController(registerManagerResponse.getErrorMessage());
                    });
                    return;
                }
                ClientConfig.clientInfo = registerManagerResponse.getClientInfo();
                ClientJavaFXApplication.loadCanvas();

            }else if(ClientConfig.role == Role.CLIENT){
                RegisterClientResponse registerClientResponse = register.registerClient(
                        new RegisterClientRequest(name, RpcServiceConfig.getConnectAddress()));
                if(registerClientResponse == null){
                    return;
                }
                //not success: no manager
                if(!registerClientResponse.isSuccess()){
                    Platform.runLater(()->{
                        new ErrorMessageGUIController(registerClientResponse.getErrorMessage());
                    });
                    return;
                }
                ClientConfig.clientInfo = registerClientResponse.getClientInfo();
                Platform.runLater(WaitForApproveGUIController::waitForApprove);
            }
        } catch (AuthorizeException e){
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(e.getMessage());
            });
        } catch (Exception e){
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(ErrorMessage.SERVER_ERROR);
            });
        }

    }

    public static void sendChatMessage(ChatMessage chatMessage){

            ChatService chatService = RpcClient.getInstance().getChatService();
            ChatAddRequest chatAddRequest = new ChatAddRequest(chatMessage);
            ClientThreadPool.getInstance().getExecutorService().submit(()->{
                try {
                    chatService.addChatMessage(chatAddRequest);
                } catch (AuthorizeException e){
                    //e.printStackTrace();
                    Platform.runLater(()->{
                        new ErrorMessageGUIController(e.getMessage());
                    });
                } catch (Exception e){
                    //e.printStackTrace();
                    Platform.runLater(()->{
                        new ErrorMessageGUIController(ErrorMessage.SERVER_ERROR);
                    });
                }
            });

    }

    public static void sendCommand(Command command){
        CanvasService canvasService = RpcClient.getInstance().getCanvasService();
        CanvasUpdateRequest canvasUpdateRequest = new CanvasUpdateRequest(command);
        ClientThreadPool.getInstance().getExecutorService().submit(()->{
            try {
                canvasService.canvasUpdate(canvasUpdateRequest);
            } catch (AuthorizeException e){
                //e.printStackTrace();
                Platform.runLater(()->{
                    new ErrorMessageGUIController(e.getMessage());
                });
            } catch (Exception e){
                //e.printStackTrace();
                Platform.runLater(()->{
                    new ErrorMessageGUIController(ErrorMessage.SERVER_ERROR);
                });
            }
        });
    }

    public static WritableImage getCanvas() {
        try {
            CanvasResponse canvasResponse = RpcClient.getInstance().getCanvasService().getCanvas(new CanvasRequest(-1));
            WritableImage writableImage = ByteAndImageConverterUtil.bytesToImage(canvasResponse.getImageBytes());
            return writableImage;
        } catch (AuthorizeException e){
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(e.getMessage());
            });
        } catch (Exception e) {
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(ErrorMessage.SERVER_ERROR);
            });
        }
        return null;
    }

    public static void sendBufferedImage(BufferedImage bufferedImage) {
        try {
            WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);
            sendWritableImage(writableImage);
        } catch (AuthorizeException e){
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(e.getMessage());
            });
        } catch (Exception e) {
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(ErrorMessage.SERVER_ERROR);
            });
        }
    }

    public static void sendWritableImage(WritableImage writableImage) {
        try {
            byte[] bytes = ByteAndImageConverterUtil.imageToBytes(writableImage);
            if(bytes != null){
                RpcClient.getInstance().getManagerService().updateCanvas(bytes);
            }
        } catch (AuthorizeException e){
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(e.getMessage());
            });
        } catch (Exception e) {
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(ErrorMessage.SERVER_ERROR);
            });
        }
    }


    public static List<ChatMessage> getChat() {
        try {
            ChatService chatService = RpcClient.getInstance().getChatService();
            ChatGetRequest chatGetRequest = new ChatGetRequest(0);
            ChatGetResponse chatGetResponse = chatService.getChatMessage(chatGetRequest);
            List<ChatMessage> chatMessageList = new ArrayList<>();
            if(chatGetResponse == null){
                return chatMessageList;
            }
            chatMessageList = chatGetResponse.getChatMessageList();
            return chatMessageList;
        } catch (AuthorizeException e){
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(e.getMessage());
            });
        } catch (Exception e) {
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(ErrorMessage.SERVER_ERROR);
            });
        }
        return null;
    }

    public static void sendJoinRequestDecision(boolean decision,ClientInfo clientInfo){
        try {
            RpcClient.getInstance().getManagerService().joinRequestDecision(decision,clientInfo);
        } catch (AuthorizeException e){
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(e.getMessage());
            });
        } catch (Exception e) {
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(ErrorMessage.SERVER_ERROR);
            });
        }
    }

    public static void leave(){
        if(ClientConfig.clientInfo == null || ClientConfig.clientInfo.getId() == null){
            return;
        }
        try {
            RpcClient.getInstance().getClusterService().leave(ClientConfig.clientInfo);
        } catch (AuthorizeException e){
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(e.getMessage());
            });
        } catch (Exception e) {
            System.out.println(ErrorMessage.SERVER_ERROR);
        }
    }

    public static List<ClientInfo> getAcceptedClient(){
        try {
            List<ClientInfo> acceptedClient = RpcClient.getInstance().getClusterService().getAcceptedClient();
            return acceptedClient;
        } catch (AuthorizeException e){
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(e.getMessage());
            });
        } catch (Exception e) {
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(ErrorMessage.SERVER_ERROR);
            });
        }
        return null;
    }

    public static void kickClient(ClientInfo clientInfo){
        if(clientInfo == null){
            Platform.runLater(()->{
                new ExceptionMessageGUIController("Please select a client before kick!");
            });
            return;
        }
        try {
            //try to kick itself
            if(clientInfo.equals(ClientConfig.clientInfo)){
                Platform.runLater(()->{
                    new ExceptionMessageGUIController("Cannot kick yourself!");
                });
            }else {
                RpcClient.getInstance().getManagerService().kickClient(clientInfo);
            }
        } catch (AuthorizeException e){
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(e.getMessage());
            });
        } catch (Exception e) {
            //e.printStackTrace();
            Platform.runLater(()->{
                new ErrorMessageGUIController(ErrorMessage.SERVER_ERROR);
            });
        }
    }

}
