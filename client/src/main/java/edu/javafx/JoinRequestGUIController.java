package edu.javafx;

import edu.dto.ClientInfo;
import edu.service.Impl.ServerService;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;



/**
 * Join Request GUI when new user want to join
 * @author lingxiao
 */
public class JoinRequestGUIController{

    private final Button denyButton;

    private final TextArea joinTextArea;

    private final Button acceptButton;

    private final ClientInfo clientInfo;

    private final Stage stage;

    public JoinRequestGUIController(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;

        //make scene and create stage
        Pane root = new Pane();
        Scene scene = new Scene(root, 600.0, 400.0);

        this.joinTextArea = new TextArea();
        joinTextArea.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent;");
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

        Stage stage = new Stage();
        stage.setTitle("join request");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnCloseRequest(Event::consume);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        this.stage = stage;

        loadAction();
    }

    private void loadAction() {
        acceptButton.setOnMouseClicked((e)->{
            ServerService.sendJoinRequestDecision(true,clientInfo);
            stage.close();
        });

        denyButton.setOnMouseClicked((e)->{
            ServerService.sendJoinRequestDecision(false,clientInfo);
            stage.close();
        });

        joinTextArea.setWrapText(true);
        joinTextArea.setText(clientInfo.getName() + " wants to share your whiteboard");
    }
}
