package edu.draw.drawShapeImpl;

import edu.draw.DrawShape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author lingxiao
 */
public class DrawTriangleImpl implements DrawShape {

    @Override
    public void draw(GraphicsContext graphicsContext,
                                  Color strokeColor, Color fillColor,
                                  double x1, double y1, double x2, double y2){
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(strokeColor);
        double upperX = (x1+x2)/2.0;
        double upperY = Math.min(y1,y2);
        double lowerY = Math.max(y1,y2);
        graphicsContext.fillPolygon(new double[]{upperX,x1,x2},new double[]{upperY,lowerY,lowerY},3);
        graphicsContext.strokePolygon(new double[]{upperX,x1,x2},new double[]{upperY,lowerY,lowerY},3);
    }
}
