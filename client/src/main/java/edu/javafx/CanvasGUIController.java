package edu.javafx;

import edu.Main;
import edu.draw.drawShapeImpl.*;
import edu.draw.drawTextImpl.DrawTextImpl;
import edu.dto.ChatMessage;
import edu.dto.ClientInfo;
import edu.javafx.component.AcceptedUserComponent;
import edu.javafx.component.ChatComponent;
import edu.javafx.component.DrawButtonComponent;
import edu.javafx.component.MenuComponent;
import edu.javafx.draw.shape.DrawShapeOnCanvas;
import edu.javafx.draw.text.DrawTextOnCanvas;
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

/**
 * Canvas GUI
 * @author lingxiao
 */
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
    private Button circleButton;
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

    /**
     * initialization, load actions on javafx components
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new DrawButtonComponent(lineButton, new DrawShapeOnCanvas(canvasEffort, outlineColorPicker, fillColorPicker, new DrawLineImpl()));
        new DrawButtonComponent(circleButton,new DrawShapeOnCanvas(canvasEffort, outlineColorPicker, fillColorPicker, new DrawCircleImpl()));
        new DrawButtonComponent(ovalButton,new DrawShapeOnCanvas(canvasEffort, outlineColorPicker, fillColorPicker, new DrawOvalImpl()));
        new DrawButtonComponent(triangleButton,new DrawShapeOnCanvas(canvasEffort, outlineColorPicker, fillColorPicker, new DrawTriangleImpl()));
        new DrawButtonComponent(rectangleButton,new DrawShapeOnCanvas(canvasEffort, outlineColorPicker, fillColorPicker, new DrawRectangleImpl()));
        new DrawButtonComponent(textButton,new DrawTextOnCanvas(canvasEffort, outlineColorPicker, fillColorPicker, new DrawTextImpl()));
        ChatComponent.init(ChatTable,nameColumn,messageColumn,chatTextArea,sendButton);
        AcceptedUserComponent.init(userTable,userColumn,kickButton);
        MenuComponent.init(canvas,canvasEffort,menuBar,fileMenu);
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

    //init canvas using canvas on server
    public void initCanvas(){
        WritableImage writableImage = ServerService.getCanvas();
        if (writableImage != null){
            this.canvas.getGraphicsContext2D().drawImage(writableImage,0,0);
        }
    }
}

