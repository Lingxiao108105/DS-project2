package edu.dto;

import javafx.scene.canvas.Canvas;

import java.io.Serializable;

public interface Command extends Serializable {

    public void execute(Canvas canvas);

}
