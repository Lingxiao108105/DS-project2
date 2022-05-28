package edu.dto.Impl;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * DrawShapeCommand include common part of draw shape(coordinates)
 * @author lingxiao
 */
public abstract class DrawShapeCommand extends DrawCommand {

    protected double x1,y1,x2,y2;


    public DrawShapeCommand(double x1, double y1, double x2, double y2, Color stroke, Color fill) {
        super(stroke,fill);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void execute(Canvas canvas) {
        drawShape(canvas);
    }

   protected abstract void drawShape(Canvas canvas);
}
