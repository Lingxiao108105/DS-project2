package edu.service.Impl;

import edu.Main;
import edu.common.enums.Role;
import edu.config.ClientConfig;
import edu.config.RpcServiceConfig;
import edu.dto.*;
import edu.javafx.CanvasGUIController;
import edu.javafx.WaitForApproveGUIController;
import edu.rpc.RpcClient;
import edu.service.Register;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ClientRegisterService {

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
}
