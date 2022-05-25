package edu.javafx.component.Impl;

import edu.Common.Exception.NotInitException;
import edu.javafx.component.CanvasEffortComponent;
import edu.javafx.shape.DrawShape;
import edu.javafx.shape.Impl.DrawLine;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;

public class DrawLineButtonComponent {

    private static DrawLineButtonComponent drawLineButtonComponent = null;

    private DrawLine drawLine = null;

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
        DrawLine.init(canvasEffort,outlineColorPicker,fillColorPicker);
        this.drawLine = DrawLine.getInstance();
        lineButton.setOnMouseClicked(this::onMouseClickedListener);
    }

    private void onMouseClickedListener(MouseEvent e){
        CanvasEffortComponent.getInstance().setDrawShape(this.drawLine);
    }


}
