package edu.javafx.shape.Impl;

import edu.Common.Exception.NotInitException;
import edu.dto.Command;
import edu.dto.Impl.DrawLineCommand;
import edu.javafx.shape.DrawShape;
import edu.service.SendCommandService;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class DrawLine implements DrawShape {

    private static DrawLine drawLine = null;

    private Canvas canvasEffort = null;
    private ColorPicker outlineColorPicker,fillColorPicker;

    private GraphicsContext graphicsContextEffort = null;

    public static void init(Canvas canvasEffort,
                            ColorPicker outlineColorPicker,
                            ColorPicker fillColorPicker) {
        drawLine = new DrawLine(canvasEffort,outlineColorPicker,fillColorPicker);
    }

    public static DrawLine getInstance(){
        if(drawLine == null){
            throw new NotInitException(DrawLine.class.getName());
        }
        return drawLine;
    }

    private DrawLine(Canvas canvasEffort,
                     ColorPicker outlineColorPicker,
                     ColorPicker fillColorPicker) {
        this.canvasEffort = canvasEffort;
        this.outlineColorPicker = outlineColorPicker;
        this.fillColorPicker = fillColorPicker;

        this.graphicsContextEffort = canvasEffort.getGraphicsContext2D();
    }

    @Override
    public void drawEffort(double x1, double y1, double x2, double y2) {
        graphicsContextEffort.setFill(fillColorPicker.getValue());
        graphicsContextEffort.setStroke(outlineColorPicker.getValue());
        graphicsContextEffort.clearRect(0, 0, canvasEffort.getWidth() , canvasEffort.getHeight());
        graphicsContextEffort.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public void draw(double x1, double y1, double x2, double y2) {
        Command command = new DrawLineCommand(x1, y1, x2, y2, outlineColorPicker.getValue(), fillColorPicker.getValue());
        SendCommandService.sendCommand(command);
    }
}
