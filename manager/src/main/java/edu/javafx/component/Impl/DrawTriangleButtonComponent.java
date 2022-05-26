package edu.javafx.component.Impl;

import edu.common.exception.NotInitException;
import edu.javafx.component.DrawButtonComponent;
import edu.javafx.draw.shape.Impl.DrawTriangle;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;

public class DrawTriangleButtonComponent extends DrawButtonComponent {

    private static DrawTriangleButtonComponent drawTriangleButtonComponent = null;

    public static void init(Button triangleButton,
                            Canvas canvasEffort,
                            ColorPicker outlineColorPicker,
                            ColorPicker fillColorPicker) {
        drawTriangleButtonComponent = new DrawTriangleButtonComponent(triangleButton,canvasEffort,outlineColorPicker,fillColorPicker);
    }

    public static DrawTriangleButtonComponent getInstance(){
        if(drawTriangleButtonComponent == null){
            throw new NotInitException(DrawTriangleButtonComponent.class.getName());
        }
        return drawTriangleButtonComponent;
    }

    private DrawTriangleButtonComponent(Button triangleButton,
                                        Canvas canvasEffort,
                                        ColorPicker outlineColorPicker,
                                        ColorPicker fillColorPicker) {
        super(triangleButton, DrawTriangle.init(canvasEffort,outlineColorPicker,fillColorPicker));
    }

}
