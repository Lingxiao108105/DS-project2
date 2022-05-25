package edu.dto.Impl;

import edu.common.util.SerializableColor;
import edu.dto.Command;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public abstract class DrawShapeCommand implements Command {

    protected double x1,y1,x2,y2;
    protected SerializableColor stroke, fill;

    public DrawShapeCommand(double x1, double y1, double x2, double y2, Color stroke, Color fill) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.stroke = new SerializableColor(stroke);
        this.fill = new SerializableColor(fill);
    }

    @Override
    public void execute(Canvas canvas) {
        drawShape(canvas);
    }

   protected abstract void drawShape(Canvas canvas);
}
