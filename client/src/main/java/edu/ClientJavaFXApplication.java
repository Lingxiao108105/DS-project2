package edu;

import edu.ThreadPool.ClientThreadPool;
import edu.common.ErrorMessage;
import edu.common.enums.Role;
import edu.config.ClientConfig;
import edu.config.RpcClientConfig;
import edu.config.RpcServiceConfig;
import edu.javafx.CanvasGUIController;
import edu.javafx.ErrorMessageGUIController;
import edu.rpc.RpcClient;
import edu.rpc.RpcServiceProvider;
import edu.service.Impl.ServerService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.util.List;

public class ClientJavaFXApplication extends Application {

    public static Stage stage = null;

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage stage) {

        ClientJavaFXApplication.stage = stage;
        Parameters parameters = getParameters();
        List<String> args = parameters.getRaw();

        if(args.size() < 5) {
            Platform.runLater(() -> {
                new ErrorMessageGUIController(ErrorMessage.NOT_ENOUGH_ARGUMENT_ERROR);
            });
            return;
        }

        InetAddressValidator addressValidator = InetAddressValidator.getInstance();

        //ip address
        String ipAddress = args.get(0);
        if(!addressValidator.isValid(ipAddress)){
            Platform.runLater(() -> {
                new ErrorMessageGUIController("Invalid <serverIPAddress> :" + ipAddress + ". \nPlease enter valid ip address!");
            });
            return;
        }
        RpcClientConfig.setServerIPAddress(ipAddress);
        //port
        int port = -1;
        try {
            port = Integer.parseInt(args.get(1));
        }catch (Exception e){
            Platform.runLater(() -> {
                new ErrorMessageGUIController("Invalid <port> :" + args.get(1) + ". \nPlease enter a number as <server-port>!");
            });
            return;
        }
        if(port < 0 || port > 65535){
            Platform.runLater(() -> {
                new ErrorMessageGUIController("Invalid <port> :" + args.get(1) + ". \nPlease enter a number between 0-65535 as <server-port>!");
            });
            return;
        }
        RpcClientConfig.setServerPort(args.get(1));
        //name
        String name = args.get(2);
        //role
        String role = args.get(3);
        if("client".equals(role)){
            ClientConfig.role = Role.CLIENT;
        }else if("manager".equals(role)){
            ClientConfig.role = Role.MANAGER;
        }else {
            Platform.runLater(() -> {
                new ErrorMessageGUIController("Invalid <role> :" + role + ". \nPlease enter a <role> from (client,manager)!");
            });
            return;
        }
        //local Ip Address
        String localIpAddress = args.get(4);
        if(!addressValidator.isValid(localIpAddress)){
            Platform.runLater(() -> {
                new ErrorMessageGUIController("Invalid <localIpAddress> :" + localIpAddress + ". \nPlease enter valid ip address!");
            });
            return;
        }
        RpcServiceConfig.setIpAddress(localIpAddress);

        RpcServiceProvider.getInstance();
        ServerService.register(name);

    }

    /**
     * stop the javafx GUI
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        ServerService.leave();
        ClientThreadPool.getInstance().stop();
        RpcClient.getInstance().stop();
        RpcServiceProvider.getInstance().stop();
        System.exit(0);
        System.out.flush();
    }

    /**
     * load Canvas
     */
    public static void loadCanvas(){
        //show canvas scene
        Scene scene = CanvasGUIController.getScene();
        stage.setTitle("Canvas");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
            }
        });
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
