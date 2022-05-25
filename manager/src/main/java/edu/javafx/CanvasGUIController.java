package edu.javafx;

import edu.Main;
import edu.javafx.component.CanvasEffortComponent;
import edu.javafx.component.Impl.DrawLineButtonComponent;
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

    @FXML
    private Canvas canvas;

    @FXML
    private Canvas canvasEffort;

    @FXML
    private Button kickButton;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private Button circleButton;

    @FXML
    private TableView<?> ChatTable;

    @FXML
    private Button lineButton;

    @FXML
    private TableColumn<?, ?> userColumn;

    @FXML
    private Button sendButton;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Button textButton;

    @FXML
    private TableColumn<?, ?> chatColumn;

    @FXML
    private ColorPicker outlineColorPicker;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private Button triangleButton;

    @FXML
    private Button rectangleButton;

    @FXML
    private Menu fileMenu;

    @FXML
    private ColorPicker fillColorPicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CanvasEffortComponent.init(canvasEffort);
        DrawLineButtonComponent.init(lineButton,canvasEffort,outlineColorPicker,fillColorPicker);
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

