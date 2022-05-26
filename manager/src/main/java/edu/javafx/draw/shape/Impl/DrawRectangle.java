package edu.javafx.draw.shape.Impl;

import edu.common.exception.NotInitException;
import edu.common.util.DrawShapeUtil;
import edu.dto.Command;
import edu.dto.Impl.DrawRectangleCommand;
import edu.javafx.draw.shape.DrawShape;
import edu.service.SendCommandService;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;

public class DrawRectangle extends DrawShape {

    private static DrawRectangle drawRectangle = null;

    public static DrawRectangle init(Canvas canvasEffort,
                                     ColorPicker outlineColorPicker,
                                     ColorPicker fillColorPicker) {
        drawRectangle = new DrawRectangle(canvasEffort,outlineColorPicker,fillColorPicker);
        return drawRectangle;
    }

    public static DrawRectangle getInstance(){
        if(drawRectangle == null){
            throw new NotInitException(DrawRectangle.class.getName());
        }
        return drawRectangle;
    }

    private DrawRectangle(Canvas canvasEffort,
                          ColorPicker outlineColorPicker,
                          ColorPicker fillColorPicker) {
        super(canvasEffort, outlineColorPicker, fillColorPicker);
    }

    @Override
    public void drawEffort(double x1, double y1, double x2, double y2) {
        graphicsContextEffort.clearRect(0, 0, canvasEffort.getWidth() , canvasEffort.getHeight());
        DrawShapeUtil.drawRectangle(graphicsContextEffort,outlineColorPicker.getValue(),fillColorPicker.getValue(),x1,y1,x2,y2);
    }

    @Override
    public void draw(double x1, double y1, double x2, double y2) {
        Command command = new DrawRectangleCommand(x1, y1, x2, y2, outlineColorPicker.getValue(), fillColorPicker.getValue());
        SendCommandService.sendCommand(command);
    }
}
