package edu;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import edu.ThreadPool.ClientThreadPool;
import edu.javafx.CanvasGUIController;
import edu.rpc.RpcClient;
import edu.service.CanvasService;
import edu.service.Register;
import edu.service.RegisterService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) {
        RegisterService.register("hello");
        //show search scene
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

    /**
     * stop the javafx GUI
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        ClientThreadPool.getInstance().stop();
        RpcClient.getInstance().stop();
    }
}
