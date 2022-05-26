package edu.javafx.draw;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public abstract class Draw {

    protected boolean startEdit = false;

    protected Canvas canvasEffort = null;
    protected ColorPicker outlineColorPicker,fillColorPicker;

    protected GraphicsContext graphicsContextEffort = null;

    protected Draw(Canvas canvasEffort,
                        ColorPicker outlineColorPicker,
                        ColorPicker fillColorPicker) {
        this.canvasEffort = canvasEffort;
        this.outlineColorPicker = outlineColorPicker;
        this.fillColorPicker = fillColorPicker;

        this.graphicsContextEffort = canvasEffort.getGraphicsContext2D();
    }

    public abstract void loadAction();

    public void unloadAction(){
        canvasEffort.setOnMousePressed((e)->{});
        canvasEffort.setOnMouseDragged((e)->{});
        canvasEffort.setOnMouseReleased((e)->{});
        canvasEffort.setOnKeyPressed((e)->{});
    }
}
