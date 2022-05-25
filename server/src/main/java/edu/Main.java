package edu;

import edu.ThreadPool.ServerThreadPool;
import edu.data.ClusterInfo;
import edu.javafx.MyCanvas;
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
        ClusterInfo.getInstance();
        MyCanvas.getInstance();
        RpcServiceProvider.getInstance();

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
        ServerThreadPool.getInstance().stop();
        RpcServiceProvider.getInstance().stop();
    }
}
