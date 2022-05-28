package edu.draw;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author lingxiao
 */
public interface DrawShape {

    public void draw(GraphicsContext graphicsContext,
                                Color strokeColor, Color fillColor,
                                double x1, double y1, double x2, double y2);
}
