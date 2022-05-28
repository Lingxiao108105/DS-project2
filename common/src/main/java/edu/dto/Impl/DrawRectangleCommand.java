package edu.dto.Impl;

import edu.common.util.DrawShapeUtil;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * DrawRectangleCommand
 * @author lingxiao
 */
public class DrawRectangleCommand extends DrawShapeCommand{

    public DrawRectangleCommand(double x1, double y1, double x2, double y2, Color stroke, Color fill) {
        super(x1, y1, x2, y2, stroke, fill);
    }

    @Override
    protected void drawShape(Canvas canvas) {
        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
        DrawShapeUtil.drawRectangle(graphicsContext2D,stroke.getFXColor(),fill.getFXColor(),x1,y1,x2,y2);
    }
}
