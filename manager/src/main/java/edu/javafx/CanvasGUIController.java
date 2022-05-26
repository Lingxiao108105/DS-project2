package edu.javafx;

import edu.Main;
import edu.dto.ChatMessage;
import edu.javafx.component.ChatComponent;
import edu.javafx.component.Impl.*;
import edu.javafx.draw.text.Impl.DrawTextImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Getter
public class CanvasGUIController implements Initializable {

    //Canvas scene
    private static Scene scene = null;

    //>>>>>>>>>>>>>>>>>>>>>>>>>> Menu <<<<<<<<<<<<<<<<<<<<<<<
    @FXML
    private Menu fileMenu;
    @FXML
    private MenuBar menuBar;

    //>>>>>>>>>>>>>>>>>>>>>>>>>> Canvas <<<<<<<<<<<<<<<<<<<<<<<
    @FXML
    private Canvas canvas;
    @FXML
    private Canvas canvasEffort;
    @FXML
    private Button lineButton;
    @FXML
    private Button ovalButton;
    @FXML
    private Button triangleButton;
    @FXML
    private Button rectangleButton;
    @FXML
    private Button textButton;
    @FXML
    private ColorPicker outlineColorPicker;
    @FXML
    private ColorPicker fillColorPicker;

    //>>>>>>>>>>>>>>>>>>>>>>>>>> Clients <<<<<<<<<<<<<<<<<<<<<<<
    @FXML
    private TableColumn<?, ?> userColumn;
    @FXML
    private Button kickButton;

    //>>>>>>>>>>>>>>>>>>>>>>>>>> Chat <<<<<<<<<<<<<<<<<<<<<<<
    @FXML
    private TableView<ChatMessage> ChatTable;
    @FXML
    private TableColumn<ChatMessage, String> nameColumn;
    @FXML
    private TableColumn<ChatMessage, String> messageColumn;
    @FXML
    private TextArea chatTextArea;
    @FXML
    private Button sendButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DrawLineButtonComponent.init(lineButton,canvasEffort,outlineColorPicker,fillColorPicker);
        DrawOvalButtonComponent.init(ovalButton,canvasEffort,outlineColorPicker,fillColorPicker);
        DrawTriangleButtonComponent.init(triangleButton,canvasEffort,outlineColorPicker,fillColorPicker);
        DrawRectangleButtonComponent.init(rectangleButton,canvasEffort,outlineColorPicker,fillColorPicker);
        DrawTextButtonComponent.init(textButton,canvasEffort,outlineColorPicker,fillColorPicker);
        ChatComponent.init(ChatTable,nameColumn,messageColumn,chatTextArea,sendButton);
        UpdateCanvasScheduler.init(canvas);
    }

    //singleton of Canvas scene
    public static Scene getScene(){
        if(CanvasGUIController.scene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("canvas.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 1316.0, 756.0);
            } catch (IOException e) {
                System.out.println("Fail to load the scene from search.fxml");
            }
            CanvasGUIController.scene = scene;
        }
        return CanvasGUIController.scene;
    }
}

