package edu.draw.drawShapeImpl;

import edu.draw.DrawShape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author lingxiao
 */
public class DrawOvalImpl implements DrawShape {

    @Override
    public void draw(GraphicsContext graphicsContext,
                                  Color strokeColor, Color fillColor,
                                  double x1, double y1, double x2, double y2){
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(strokeColor);
        double upperLeftX = Math.min(x1,x2);
        double upperLeftY = Math.min(y1,y2);
        double w = Math.abs(x1-x2);
        double y = Math.abs(y1-y2);
        graphicsContext.fillOval(upperLeftX, upperLeftY, w, y);
        graphicsContext.strokeOval(upperLeftX, upperLeftY, w, y);
    }
}
