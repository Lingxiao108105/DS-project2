package edu.dto.Impl;

import edu.draw.DrawText;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * DrawTextCommand
 * @author lingxiao
 */
public class DrawTextCommand extends DrawCommand{

    protected double x1,y1;
    protected String text;
    protected DrawText drawText;

    public DrawTextCommand(double x1, double y1, String text, Color stroke, Color fill, DrawText drawText) {
        super(stroke, fill);
        this.x1 = x1;
        this.y1 = y1;
        this.text = text;
        this.drawText = drawText;
    }

    @Override
    public void execute(Canvas canvas) {
        drawText(canvas);
    }

    protected void drawText(Canvas canvas){
        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
        drawText.draw(graphicsContext2D,stroke.getFXColor(),fill.getFXColor(),x1,y1,text);
    }
}
