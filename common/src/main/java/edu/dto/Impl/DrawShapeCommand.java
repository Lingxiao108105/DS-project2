package edu.dto.Impl;

import edu.draw.DrawShape;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * DrawShapeCommand include common part of draw shape(coordinates)
 * @author lingxiao
 */
public class DrawShapeCommand extends DrawCommand {

    protected double x1,y1,x2,y2;
    protected DrawShape drawShape;


    public DrawShapeCommand(double x1, double y1, double x2, double y2, Color stroke, Color fill, DrawShape drawShape) {
        super(stroke,fill);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.drawShape = drawShape;
    }

    @Override
    public void execute(Canvas canvas) {
        drawShape(canvas);
    }

   protected void drawShape(Canvas canvas){
       GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
       drawShape.draw(graphicsContext2D,stroke.getFXColor(),fill.getFXColor(),x1,y1,x2,y2);
   }
}
