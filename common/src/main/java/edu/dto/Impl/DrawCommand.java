package edu.dto.Impl;

import edu.common.util.SerializableColor;
import edu.dto.Command;
import javafx.scene.paint.Color;

/**
 * DrawCommand include common part of draw(color)
 * @author lingxiao
 */
public abstract class DrawCommand implements Command {

    protected SerializableColor stroke, fill;

    public DrawCommand(Color stroke, Color fill) {
        this.stroke = new SerializableColor(stroke);
        this.fill = new SerializableColor(fill);
    }
}
