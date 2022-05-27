package edu.javafx;

import edu.Main;
import edu.dto.ChatMessage;
import edu.dto.ClientInfo;
import edu.javafx.component.AcceptedUserComponent;
import edu.javafx.component.ChatComponent;
import edu.javafx.component.DrawButtonComponent;
import edu.javafx.component.MenuComponent;
import edu.javafx.draw.shape.Impl.DrawLine;
import edu.javafx.draw.shape.Impl.DrawOval;
import edu.javafx.draw.shape.Impl.DrawRectangle;
import edu.javafx.draw.shape.Impl.DrawTriangle;
import edu.javafx.draw.text.Impl.DrawTextImpl;
import edu.service.Impl.ClientUpdateServiceImpl;
import edu.service.Impl.ServerService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
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
    private TableView<ClientInfo> userTable;
    @FXML
    private TableColumn<ClientInfo, String> userColumn;
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
        new DrawButtonComponent(lineButton,new DrawLine(canvasEffort,outlineColorPicker,fillColorPicker));
        new DrawButtonComponent(ovalButton,new DrawOval(canvasEffort,outlineColorPicker,fillColorPicker));
        new DrawButtonComponent(triangleButton,new DrawTriangle(canvasEffort,outlineColorPicker,fillColorPicker));
        new DrawButtonComponent(rectangleButton,new DrawRectangle(canvasEffort,outlineColorPicker,fillColorPicker));
        new DrawButtonComponent(textButton,new DrawTextImpl(canvasEffort,outlineColorPicker,fillColorPicker));
        ChatComponent.init(ChatTable,nameColumn,messageColumn,chatTextArea,sendButton);
        AcceptedUserComponent.init(userTable,userColumn,kickButton);
        MenuComponent.init(canvas,menuBar,fileMenu);
        initCanvas();
        ClientUpdateServiceImpl.canvas = this.canvas;
    }

    //singleton of Canvas scene
    public static Scene getScene(){
        if(CanvasGUIController.scene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("canvas.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 1316.0, 756.0);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Fail to load the scene from canvas.fxml");
            }
            CanvasGUIController.scene = scene;
        }
        return CanvasGUIController.scene;
    }

    public void initCanvas(){
        WritableImage writableImage = ServerService.getCanvas();
        if (writableImage != null){
            this.canvas.getGraphicsContext2D().drawImage(writableImage,0,0);
        }
    }
}

