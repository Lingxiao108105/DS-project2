package edu;

import edu.ThreadPool.ServerThreadPool;
import edu.data.ClusterInfo;
import edu.javafx.MyCanvas;
import edu.rpc.RpcServiceProvider;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ServerThreadPool.getInstance();
        ClusterInfo.getInstance();
        MyCanvas.getInstance();
        RpcServiceProvider.getInstance();
        primaryStage.show();
    }

    /**
     * stop the javafx GUI
     */
    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
