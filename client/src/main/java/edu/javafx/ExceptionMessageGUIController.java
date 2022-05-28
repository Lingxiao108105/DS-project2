package edu.javafx;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Exception Message GUI
 * constructs an ExceptionMessageGUIController with exceptionMessageText will show a pop-up with exceptionMessageText
 * and WILL NOT exit Platform
 * @author lingxiao
 */
public class ExceptionMessageGUIController{

    private final Button exitButton;

    private final TextArea exceptionMessage;

    private final Stage stage;

    public ExceptionMessageGUIController(String exceptionMessageText) {

        //make scene and create stage
        Pane root = new Pane();
        Scene scene = new Scene(root, 600.0, 400.0);

        this.exitButton = new Button("OK");
        this.exitButton.setPrefWidth(50);
        this.exitButton.setPrefHeight(30);
        this.exitButton.setLayoutX(400);
        this.exitButton.setLayoutY(300);
        root.getChildren().add(this.exitButton);

        this.exceptionMessage = new TextArea(exceptionMessageText);
        this.exceptionMessage.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent;");
        this.exceptionMessage.setPrefWidth(400);
        this.exceptionMessage.setPrefHeight(200);
        this.exceptionMessage.setLayoutX(100);
        this.exceptionMessage.setLayoutY(50);
        this.exceptionMessage.setEditable(false);
        this.exceptionMessage.setWrapText(true);
        root.getChildren().add(this.exceptionMessage);


        Stage stage = new Stage();
        stage.setTitle("Warning");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        this.stage = stage;

        loadAction();
    }

    private void loadAction() {

        this.stage.setOnCloseRequest((e)->{
            stage.close();
        });

        this.exitButton.setOnMouseClicked((e)->{
            stage.close();
        });
    }
}
