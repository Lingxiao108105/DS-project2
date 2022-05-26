package edu.javafx.component.Impl;

import edu.common.exception.NotInitException;
import edu.javafx.component.DrawButtonComponent;
import edu.javafx.draw.shape.Impl.DrawOval;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;

public class DrawOvalButtonComponent extends DrawButtonComponent {

    private static DrawOvalButtonComponent drawOvalButtonComponent = null;

    public static void init(Button ovalButton,
                            Canvas canvasEffort,
                            ColorPicker outlineColorPicker,
                            ColorPicker fillColorPicker) {
        drawOvalButtonComponent = new DrawOvalButtonComponent(ovalButton,canvasEffort,outlineColorPicker,fillColorPicker);
    }

    public static DrawOvalButtonComponent getInstance(){
        if(drawOvalButtonComponent == null){
            throw new NotInitException(DrawOvalButtonComponent.class.getName());
        }
        return drawOvalButtonComponent;
    }

    private DrawOvalButtonComponent(Button ovalButton,
                                    Canvas canvasEffort,
                                    ColorPicker outlineColorPicker,
                                    ColorPicker fillColorPicker) {
        super(ovalButton, DrawOval.init(canvasEffort,outlineColorPicker,fillColorPicker));
    }

}
