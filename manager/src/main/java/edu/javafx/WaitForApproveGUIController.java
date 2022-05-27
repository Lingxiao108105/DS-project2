package edu.javafx;

import edu.Main;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class WaitForApproveGUIController {

    private static WaitForApproveGUIController waitForApproveGUIController = null;

    private static Stage stage;

    public static WaitForApproveGUIController waitForApprove(){
        if(waitForApproveGUIController == null){
            waitForApproveGUIController = new WaitForApproveGUIController();
            stage = waitForApproveGUIController.newRequest();
        }
        else {
            stage.show();
        }
        return waitForApproveGUIController;
    }

    public void close(){
        stage.close();
    }

    //create a waitForApprove stage
    private Stage newRequest(){
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("waitForApprove.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 395.0, 92.0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail to load the scene from waitForApprove.fxml");
        }
        Stage stage = new Stage();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
            }
        });
        stage.setTitle("Wait For Approve");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        return stage;
    }
}
