package edu.draw.drawShapeImpl;

import edu.draw.DrawShape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author lingxiao
 */
public class DrawCircleImpl implements DrawShape {

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
        double r = Math.max(w,y);
        graphicsContext.fillOval(upperLeftX, upperLeftY, r, r);
        graphicsContext.strokeOval(upperLeftX, upperLeftY, r, r);
    }
}
