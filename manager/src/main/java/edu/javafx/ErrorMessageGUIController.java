package edu.javafx;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ErrorMessageGUIController {

    private final Button exitButton;

    private final TextArea errorMessage;

    private final Stage stage;

    public ErrorMessageGUIController(String errorMessageText) {

        //make scene and create stage
        Pane root = new Pane();
        Scene scene = new Scene(root, 600.0, 400.0);

        this.exitButton = new Button("EXIT");
        this.exitButton.setPrefWidth(50);
        this.exitButton.setPrefHeight(30);
        this.exitButton.setLayoutX(400);
        this.exitButton.setLayoutY(300);
        root.getChildren().add(this.exitButton);

        this.errorMessage = new TextArea(errorMessageText);
        this.errorMessage.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent;");
        this.errorMessage.setPrefWidth(400);
        this.errorMessage.setPrefHeight(200);
        this.errorMessage.setLayoutX(100);
        this.errorMessage.setLayoutY(50);
        this.errorMessage.setEditable(false);
        this.errorMessage.setWrapText(true);
        root.getChildren().add(this.errorMessage);

        loadAction();
        Stage stage = new Stage();
        stage.setTitle("Error");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        this.stage = stage;
    }

    private void loadAction() {

        this.stage.setOnCloseRequest((e)->{
            Platform.exit();
        });

        this.exitButton.setOnMouseClicked((e)->{
            Platform.exit();
        });
    }
}
