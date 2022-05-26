package edu.javafx.component.Impl;

import edu.common.exception.NotInitException;
import edu.javafx.component.DrawButtonComponent;
import edu.javafx.draw.shape.Impl.DrawTriangle;
import edu.javafx.draw.text.DrawText;
import edu.javafx.draw.text.Impl.DrawTextImpl;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;

public class DrawTextButtonComponent extends DrawButtonComponent {

    private static DrawTextButtonComponent drawTextButtonComponent = null;

    public static void init(Button textButton,
                            Canvas canvasEffort,
                            ColorPicker outlineColorPicker,
                            ColorPicker fillColorPicker) {
        drawTextButtonComponent = new DrawTextButtonComponent(textButton,canvasEffort,outlineColorPicker,fillColorPicker);
    }

    public static DrawTextButtonComponent getInstance(){
        if(drawTextButtonComponent == null){
            throw new NotInitException(DrawTextButtonComponent.class.getName());
        }
        return drawTextButtonComponent;
    }

    private DrawTextButtonComponent(Button textButton,
                                    Canvas canvasEffort,
                                    ColorPicker outlineColorPicker,
                                    ColorPicker fillColorPicker) {
        super(textButton, DrawTextImpl.init(canvasEffort,outlineColorPicker,fillColorPicker));
    }

}
