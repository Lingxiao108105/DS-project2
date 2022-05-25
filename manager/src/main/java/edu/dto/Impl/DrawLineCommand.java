package edu.dto.Impl;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawLineCommand extends DrawShapeCommand{

    public DrawLineCommand(double x1, double y1, double x2, double y2, Color stroke, Color fill) {
        super(x1, y1, x2, y2, stroke, fill);
    }

    @Override
    protected void drawShape(Canvas canvas) {
        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();

    }
}
