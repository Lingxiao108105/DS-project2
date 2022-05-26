package edu.javafx.component.Impl;

import edu.common.exception.NotInitException;
import edu.javafx.component.DrawButtonComponent;
import edu.javafx.draw.shape.Impl.DrawLine;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;

public class DrawLineButtonComponent extends DrawButtonComponent {

    private static DrawLineButtonComponent drawLineButtonComponent = null;

    public static void init(Button lineButton,
                            Canvas canvasEffort,
                            ColorPicker outlineColorPicker,
                            ColorPicker fillColorPicker) {
        drawLineButtonComponent = new DrawLineButtonComponent(lineButton,canvasEffort,outlineColorPicker,fillColorPicker);
    }

    public static DrawLineButtonComponent getInstance(){
        if(drawLineButtonComponent == null){
            throw new NotInitException(DrawLineButtonComponent.class.getName());
        }
        return drawLineButtonComponent;
    }

    private DrawLineButtonComponent(Button lineButton,
                                    Canvas canvasEffort,
                                   ColorPicker outlineColorPicker,
                                   ColorPicker fillColorPicker) {
        super(lineButton,DrawLine.init(canvasEffort,outlineColorPicker,fillColorPicker));
    }


}
