package edu.javafx.draw.shape.Impl;

import edu.common.exception.NotInitException;
import edu.common.util.DrawShapeUtil;
import edu.dto.Command;
import edu.dto.Impl.DrawLineCommand;
import edu.javafx.draw.shape.DrawShape;
import edu.service.SendCommandService;
import javafx.scene.canvas.Canvas;

import javafx.scene.control.ColorPicker;

public class DrawLine extends DrawShape {

    private static DrawLine drawLine = null;

    public static DrawLine init(Canvas canvasEffort,
                            ColorPicker outlineColorPicker,
                            ColorPicker fillColorPicker) {
        drawLine = new DrawLine(canvasEffort,outlineColorPicker,fillColorPicker);
        return drawLine;
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
        super(canvasEffort, outlineColorPicker, fillColorPicker);
    }

    @Override
    public void drawEffort(double x1, double y1, double x2, double y2) {
        graphicsContextEffort.clearRect(0, 0, canvasEffort.getWidth() , canvasEffort.getHeight());
        DrawShapeUtil.drawLine(graphicsContextEffort,outlineColorPicker.getValue(),fillColorPicker.getValue(),x1,y1,x2,y2);
    }

    @Override
    public void draw(double x1, double y1, double x2, double y2) {
        Command command = new DrawLineCommand(x1, y1, x2, y2, outlineColorPicker.getValue(), fillColorPicker.getValue());
        SendCommandService.sendCommand(command);
    }
}
