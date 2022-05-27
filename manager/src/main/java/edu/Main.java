package edu;

import edu.ThreadPool.ClientThreadPool;
import edu.common.enums.Role;
import edu.config.ClientConfig;
import edu.javafx.CanvasGUIController;
import edu.rpc.RpcClient;
import edu.rpc.RpcServiceProvider;
import edu.service.Impl.ServerService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.List;

public class Main extends Application {

    public static Stage stage = null;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) {

        Main.stage = stage;
        Parameters parameters = getParameters();
        List<String> args = parameters.getRaw();

        RpcServiceProvider.getInstance();


        if("client".equals(args.get(1))){
            ClientConfig.role = Role.CLIENT;
        }else {
            ClientConfig.role = Role.MANAGER;
        }
        ServerService.register(args.get(0));

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
