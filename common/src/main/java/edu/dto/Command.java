package edu.dto;

import javafx.scene.canvas.Canvas;

import java.io.Serializable;

/**
 * Command describing how to draw shape
 * For client to update canvas at server side
 * @author lingxiao
 */
public interface Command extends Serializable {

    public void execute(Canvas canvas);

}
