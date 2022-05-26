package edu.javafx;

import edu.Main;
import edu.dto.ClientInfo;
import edu.rpc.RpcClient;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class JoinRequestGUIController{

    @FXML
    private Button denyButton;

    @FXML
    private TextArea joinTextArea;

    @FXML
    private Button acceptButton;

    private ClientInfo clientInfo;

    private Stage stage;

    public JoinRequestGUIController(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
        Pane root = new Pane();
        Scene scene = new Scene(root, 600.0, 400.0);

        this.joinTextArea = new TextArea();
        joinTextArea.setPrefWidth(400);
        joinTextArea.setPrefHeight(260);
        joinTextArea.setLayoutX(80);
        joinTextArea.setLayoutY(40);
        joinTextArea.setEditable(false);
        joinTextArea.setWrapText(true);
        root.getChildren().add(joinTextArea);

        this.acceptButton = new Button();
        acceptButton.setPrefWidth(66);
        acceptButton.setPrefHeight(31);
        acceptButton.setLayoutX(150);
        acceptButton.setLayoutY(328);
        acceptButton.setText("Accept");
        root.getChildren().add(acceptButton);

        this.denyButton = new Button();
        denyButton.setPrefWidth(55);
        denyButton.setPrefHeight(31);
        denyButton.setLayoutX(320);
        denyButton.setLayoutY(328);
        denyButton.setText("Deny");
        root.getChildren().add(denyButton);

        loadAction();
        Stage stage = new Stage();
        stage.setTitle("join request");
        stage.setOnCloseRequest(Event::consume);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        this.stage = stage;
    }

    public void loadAction() {
        acceptButton.setOnMouseClicked((e)->{
            System.out.println(clientInfo);
            RpcClient.getInstance().getManagerService().joinRequestDecision(true,clientInfo);
            stage.close();
        });

        denyButton.setOnMouseClicked((e)->{
            RpcClient.getInstance().getManagerService().joinRequestDecision(false,clientInfo);
            stage.close();
        });

        joinTextArea.setWrapText(true);
        joinTextArea.setText(clientInfo.getName() + " wants to share your whiteboard");
    }
}
