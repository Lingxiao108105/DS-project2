package edu;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import edu.javafx.CanvasGUIController;
import edu.rpc.RpcClient;
import edu.service.CanvasService;
import edu.service.Register;
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
        RpcClient rpcClient = RpcClient.getInstance();
        CanvasService canvasService = rpcClient.getCanvasServiceConsumerConfig().refer();
        System.out.println(canvasService.sayHello());
        Register register = rpcClient.getRegisterConsumerConfig().refer();
        System.out.println(register.sayHello("Hi"));

        //show search scene
        Scene scene = CanvasGUIController.getScene();
        stage.setTitle("Dictionary");
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
    }
}
