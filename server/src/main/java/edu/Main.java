package edu;

import edu.ThreadPool.ServerThreadPool;
import edu.data.Chat;
import edu.data.ClusterInfo;
import edu.javafx.MyCanvas;
import edu.rpc.RpcClient;
import edu.rpc.RpcServiceProvider;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ServerThreadPool.getInstance();
        RpcServiceProvider.getInstance();
        initCanvas();

//        Pane root = new Pane();
//        root.getChildren().add(MyCanvas.getInstance().getCanvas());
//        stage.setScene(new Scene(root));
//        stage.show();
    }

    /**
     * stop the javafx GUI
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        stopCanvas();
        ServerThreadPool.getInstance().stop();
        RpcServiceProvider.getInstance().stop();
    }

    public static void restartCanvas(){
        stopCanvas();
        initCanvas();
    }

    public static void initCanvas(){
        RpcClient.getInstance();
        ClusterInfo.getInstance();
        MyCanvas.getInstance();
        Chat.getInstance();
    }

    public static void stopCanvas(){
        Chat.getInstance().stop();
        MyCanvas.getInstance().stop();
        ClusterInfo.getInstance().stop();
        RpcClient.getInstance().stop();
    }
}
