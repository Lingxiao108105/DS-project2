package edu.common.util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * define how to draw text on javafx canvas
 * @author lingxiao
 */
public class DrawTextUtil {

    public static void drawText(GraphicsContext graphicsContext,
                           Color strokeColor, Color fillColor,
                           double x1, double y1, String text){
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(strokeColor);
        graphicsContext.fillText(text,x1,y1);
        graphicsContext.strokeText(text,x1,y1);
    }



}
