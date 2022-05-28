package edu.javafx.component;

import edu.common.enums.Role;
import edu.common.exception.NotInitException;
import edu.config.ClientConfig;
import edu.javafx.ExceptionMessageGUIController;
import edu.service.Impl.ServerService;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * MenuComponent controls the menu of canvas(only file menu now)
 * @author lingxiao
 */
public class MenuComponent {

    private static MenuComponent menuComponent = null;

    private final Canvas canvas, canvasEffort;

    private final MenuBar menuBar;

    private final Menu fileMenu;

    private File file = null;

    public static void init(Canvas canvas,
                            Canvas canvasEffort,
                            MenuBar menuBar,
                            Menu fileMenu) {
        menuComponent = new MenuComponent(canvas, canvasEffort, menuBar,fileMenu);
    }

    public static MenuComponent getInstance(){
        if(menuComponent == null){
            throw new NotInitException(MenuComponent.class.getName());
        }
        return menuComponent;
    }

    private MenuComponent(Canvas canvas,
                          Canvas canvasEffort,
                          MenuBar menuBar,
                          Menu fileMenu) {
        this.canvas = canvas;
        this.canvasEffort = canvasEffort;
        this.menuBar = menuBar;
        this.fileMenu = fileMenu;

        //client cannot use this function
        if(ClientConfig.role == Role.CLIENT){
            fileMenu.setVisible(false);
            return;
        }

        //create menu items
        MenuItem newCanvas = new MenuItem("new");
        newCanvas.setOnAction(this::setOnNewAction);

        MenuItem open = new MenuItem("open");
        open.setOnAction(this::setOnOpenAction);

        MenuItem save = new MenuItem("save");
        save.setOnAction(this::setOnSaveAction);

        MenuItem saveAs = new MenuItem("saveAs");
        saveAs.setOnAction(this::setOnSaveAsAction);

        MenuItem close = new MenuItem("close");
        close.setOnAction(this::setOnCloseAction);

        fileMenu.getItems().addAll(newCanvas,open,save,saveAs,close);
        
    }

    /**
     * create a new blank canvas
     */
    private void setOnNewAction(ActionEvent event){
        //uncomment to save previous canvas
        //saveCanvas();

        this.file = null;

        //clean the canvasEffort
        canvasEffort.getGraphicsContext2D().clearRect(0, 0, canvasEffort.getWidth() , canvasEffort.getHeight());
        //clean the canvas
        WritableImage snapshot = new Canvas(canvas.getWidth(), canvas.getHeight()).snapshot(null, null);
        ServerService.sendWritableImage(snapshot);
    }

    /**
     * open a canvas
     */
    private void setOnOpenAction(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Canvas");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));

        this.file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if(this.file != null){
            //clean the canvasEffort
            canvasEffort.getGraphicsContext2D().clearRect(0, 0, canvasEffort.getWidth() , canvasEffort.getHeight());
        }
        openCanvas();
    }

    /**
     * save canvas to file
     * if the file do not exist, call SaveAs
     */
    private void setOnSaveAction(ActionEvent event){
        if(this.file != null){
            saveCanvas();
        }else {
            setOnSaveAsAction(event);
        }
    }

    /**
     * chosen file or create file first
     * and then save canvas to the chosen file
     */
    private void setOnSaveAsAction(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Canvas");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));

        this.file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

        saveCanvas();
    }

    /**
     * try to save canvas
     * then exit the application
     */
    private void setOnCloseAction(ActionEvent event){
        setOnSaveAction(event);
        Platform.exit();
    }

    /**
     * save canvas to file
     */
    private void saveCanvas(){
        if(this.file != null){
            try{
                WritableImage snapshot = canvas.snapshot(null, null);
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
                ImageIO.write(bufferedImage,"png",this.file);
            }catch (IOException e){
                //e.printStackTrace();
                new ExceptionMessageGUIController("Fail to save the canvas!");
            }
        }
    }

    /**
     * open canvas from file
     */
    private void openCanvas(){
        if(this.file != null){
            try{
                BufferedImage read = ImageIO.read(this.file);
                ServerService.sendBufferedImage(read);
            }catch (IOException e){
                //e.printStackTrace();
                new ExceptionMessageGUIController("Fail to open the canvas!");
            }
        }
    }

}
