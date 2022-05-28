package edu.draw;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author lingxiao
 */
public interface DrawText {

    public void draw(GraphicsContext graphicsContext,
                                Color strokeColor, Color fillColor,
                                double x1, double y1, String text);
}
