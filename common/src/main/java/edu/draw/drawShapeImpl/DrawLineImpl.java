package edu.draw.drawShapeImpl;

import edu.draw.DrawShape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author lingxiao
 */
public class DrawLineImpl implements DrawShape {

    @Override
    public void draw(GraphicsContext graphicsContext,
                                  Color strokeColor, Color fillColor,
                                  double x1, double y1, double x2, double y2){
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(strokeColor);
        graphicsContext.strokeLine(x1, y1, x2, y2);
    }
}
