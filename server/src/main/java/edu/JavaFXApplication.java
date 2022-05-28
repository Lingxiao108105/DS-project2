package edu;

import edu.ThreadPool.ServerThreadPool;
import edu.config.RpcServiceConfig;
import edu.data.Chat;
import edu.data.ClusterInfo;
import edu.javafx.MyCanvas;
import edu.rpc.RpcClient;
import edu.rpc.RpcServiceProvider;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class JavaFXApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parameters parameters = getParameters();
        List<String> args = parameters.getRaw();
        //sanity check
        if(args.size() >= 1){
            int port = -1;
            try {
                port = Integer.parseInt(args.get(0));
            }catch (Exception e){
                System.out.println("Please enter a number as <server-port>!");
                System.exit(0);
                return;
            }
            if(port < 0 || port > 65535){
                System.out.println("Please enter a number between 0-65535 as <server-port>!");
                System.exit(0);
                return;
            }
            RpcServiceConfig.setRegisterPort(port);
        }


        ServerThreadPool.getInstance();
        RpcServiceProvider.getInstance();
        initCanvas();

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
        System.exit(0);
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
