package edu.javafx.component;

import edu.common.exception.NotInitException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuComponent {

    private static MenuComponent menuComponent = null;

    private final Canvas canvas;

    private final MenuBar menuBar;

    private final Menu fileMenu;


    public static void init(Canvas canvas,
                            MenuBar menuBar,
                            Menu fileMenu) {
        menuComponent = new MenuComponent(canvas,menuBar,fileMenu);
    }

    public static MenuComponent getInstance(){
        if(menuComponent == null){
            throw new NotInitException(MenuComponent.class.getName());
        }
        return menuComponent;
    }

    private MenuComponent(Canvas canvas,
                          MenuBar menuBar,
                          Menu fileMenu) {
        this.canvas = canvas;
        this.menuBar = menuBar;
        this.fileMenu = fileMenu;

        MenuItem newCanvas = new MenuItem("new");
        MenuItem open = new MenuItem("open");
        MenuItem save = new MenuItem("save");
        MenuItem saveAs = new MenuItem("saveAs");
        MenuItem close = new MenuItem("close");

        fileMenu.getItems().addAll(newCanvas,open,save,saveAs,close);

        open.setOnAction((event)->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Canvas");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));

            File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

            if(file != null){
                try{
                    BufferedImage read = ImageIO.read(file);
                    canvas.getGraphicsContext2D().drawImage(SwingFXUtils.toFXImage(read,null),0,0);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });


        saveAs.setOnAction((event)->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Canvas");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));

            File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

            if(file != null){
                try{
                    WritableImage snapshot = canvas.snapshot(null, null);
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
                    ImageIO.write(bufferedImage,"png",file);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

}
