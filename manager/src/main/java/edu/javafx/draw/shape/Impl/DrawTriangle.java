package edu.javafx.draw.shape.Impl;

import edu.common.exception.NotInitException;
import edu.common.util.DrawShapeUtil;
import edu.dto.Command;
import edu.dto.Impl.DrawTriangleCommand;
import edu.javafx.draw.shape.DrawShape;
import edu.service.Impl.SendCommandService;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;

public class DrawTriangle extends DrawShape {

    private static DrawTriangle drawTriangle = null;

    public static DrawTriangle init(Canvas canvasEffort,
                                    ColorPicker outlineColorPicker,
                                    ColorPicker fillColorPicker) {
        drawTriangle = new DrawTriangle(canvasEffort,outlineColorPicker,fillColorPicker);
        return drawTriangle;
    }

    public static DrawTriangle getInstance(){
        if(drawTriangle == null){
            throw new NotInitException(DrawTriangle.class.getName());
        }
        return drawTriangle;
    }

    private DrawTriangle(Canvas canvasEffort,
                         ColorPicker outlineColorPicker,
                         ColorPicker fillColorPicker) {
        super(canvasEffort, outlineColorPicker, fillColorPicker);
    }

    @Override
    public void drawEffort(double x1, double y1, double x2, double y2) {
        graphicsContextEffort.clearRect(0, 0, canvasEffort.getWidth() , canvasEffort.getHeight());
        DrawShapeUtil.drawTriangle(graphicsContextEffort,outlineColorPicker.getValue(),fillColorPicker.getValue(),x1,y1,x2,y2);
    }

    @Override
    public void draw(double x1, double y1, double x2, double y2) {
        Command command = new DrawTriangleCommand(x1, y1, x2, y2, outlineColorPicker.getValue(), fillColorPicker.getValue());
        SendCommandService.sendCommand(command);
    }
}
