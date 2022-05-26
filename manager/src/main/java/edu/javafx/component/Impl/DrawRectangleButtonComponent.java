package edu.javafx.component.Impl;

import edu.common.exception.NotInitException;
import edu.javafx.component.DrawButtonComponent;
import edu.javafx.draw.shape.Impl.DrawRectangle;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;

public class DrawRectangleButtonComponent extends DrawButtonComponent {

    private static DrawRectangleButtonComponent drawOvalButtonComponent = null;

    public static void init(Button rectangleButton,
                            Canvas canvasEffort,
                            ColorPicker outlineColorPicker,
                            ColorPicker fillColorPicker) {
        drawOvalButtonComponent = new DrawRectangleButtonComponent(rectangleButton,canvasEffort,outlineColorPicker,fillColorPicker);
    }

    public static DrawRectangleButtonComponent getInstance(){
        if(drawOvalButtonComponent == null){
            throw new NotInitException(DrawRectangleButtonComponent.class.getName());
        }
        return drawOvalButtonComponent;
    }

    private DrawRectangleButtonComponent(Button rectangleButton,
                                         Canvas canvasEffort,
                                         ColorPicker outlineColorPicker,
                                         ColorPicker fillColorPicker) {
        super(rectangleButton, DrawRectangle.init(canvasEffort,outlineColorPicker,fillColorPicker));
    }

}
