package edu.javafx;

import edu.Main;
import edu.dto.ClientInfo;
import edu.rpc.RpcClient;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExceptionMessageGUIController implements Initializable {

    @FXML
    private Button exitButton;

    @FXML
    private TextArea exceptionMessage;

    private String exceptionMessageText;

    public ExceptionMessageGUIController(String exceptionMessageText) {
        this.exceptionMessageText = exceptionMessageText;
        Stage stage = newRequest();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitButton.setOnMouseClicked((e)->{
            Platform.exit();
        });
        exceptionMessage.setText(exceptionMessageText);

    }

    //create a joinRequest stage
    private Stage newRequest(){
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ExceptionMessage.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600.0, 400.0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail to load the scene from ExceptionMessage.fxml");
        }
        Stage stage = new Stage();
        stage.setTitle("Exception Message");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
            }
        });
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        return stage;
    }

}
