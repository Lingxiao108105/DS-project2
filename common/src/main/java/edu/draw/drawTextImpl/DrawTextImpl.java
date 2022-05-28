package edu.draw.drawTextImpl;

import edu.draw.DrawText;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author lingxiao
 */
public class DrawTextImpl implements DrawText {

    @Override
    public void draw(GraphicsContext graphicsContext,
                                Color strokeColor, Color fillColor,
                                double x1, double y1, String text){
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(strokeColor);
        graphicsContext.fillText(text,x1,y1);
        graphicsContext.strokeText(text,x1,y1);
    }

}
