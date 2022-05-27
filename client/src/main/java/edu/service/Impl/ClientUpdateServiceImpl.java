package edu.service.Impl;

import edu.Main;
import edu.common.exception.NotInitException;
import edu.common.util.ByteAndImageConverterUtil;
import edu.dto.ChatMessage;
import edu.dto.ClientInfo;
import edu.javafx.ErrorMessageGUIController;
import edu.javafx.JoinRequestGUIController;
import edu.javafx.WaitForApproveGUIController;
import edu.javafx.component.AcceptedUserComponent;
import edu.javafx.component.ChatComponent;
import edu.service.ClientUpdateService;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

import java.util.List;


public class ClientUpdateServiceImpl implements ClientUpdateService {

    public static Canvas canvas;

    @Override
    public void updateClientCanvas(byte[] imageBytes) {
        Platform.runLater(() ->{
            WritableImage writableImage = ByteAndImageConverterUtil.bytesToImage(imageBytes);
            if (writableImage != null){
                canvas.getGraphicsContext2D().drawImage(writableImage,0,0);
            }
        });
    }

    @Override
    public void updateChat(List<ChatMessage> chatMessageList) {
        ChatComponent.getInstance().getChatMessageList().addAll(chatMessageList);
    }

    @Override
    public void updateAcceptedClient(List<ClientInfo> clientInfoList) {
        AcceptedUserComponent acceptedUserComponent = null;
        try {
            acceptedUserComponent = AcceptedUserComponent.getInstance();
        }catch (NotInitException e){
            return;
        }
        acceptedUserComponent.getClientNameObservableList().clear();
        acceptedUserComponent.getClientNameObservableList().addAll(clientInfoList);
    }

    @Override
    public void joinRequestDecision(boolean decision) {
        Platform.runLater(()-> {
            WaitForApproveGUIController.waitForApprove().close();
            if (!decision) {
                new ErrorMessageGUIController("You are not approved to join!");
            }else {
                Main.loadCanvas();
            }
        });
    }

    @Override
    public void notifyManagerJoinRequest(ClientInfo clientInfo) {
        Platform.runLater(()-> {
            new JoinRequestGUIController(clientInfo);
        });
    }

    @Override
    public void notifyBeenKicked() {
        Platform.runLater(()-> {
            new ErrorMessageGUIController("You have been kicked by Manager");
        });
    }

    @Override
    public void notifyManagerExit() {
        Platform.runLater(()-> {
            new ErrorMessageGUIController("Manager exit! The whiteboard is closing!");
        });
    }
}
