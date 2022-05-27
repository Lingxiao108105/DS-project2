package edu.javafx.draw.shape.Impl;

import edu.common.util.DrawShapeUtil;
import edu.dto.Command;
import edu.dto.Impl.DrawRectangleCommand;
import edu.javafx.draw.shape.DrawShape;
import edu.service.Impl.ServerService;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;

public class DrawRectangle extends DrawShape {

    public DrawRectangle(Canvas canvasEffort,
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
        ServerService.sendCommand(command);
    }
}
