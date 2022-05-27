package edu.javafx.draw.shape;

import edu.javafx.draw.Draw;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;

public abstract class DrawShape extends Draw {

    private double startX, startY, currentX, currentY;

    protected DrawShape(Canvas canvasEffort,
                     ColorPicker outlineColorPicker,
                     ColorPicker fillColorPicker) {
        super(canvasEffort, outlineColorPicker, fillColorPicker);
    }

    @Override
    public void loadAction(){
        canvasEffort.setOnMousePressed(this::onMousePressedListener);
        canvasEffort.setOnMouseDragged(this::onMouseDraggedListener);
        canvasEffort.setOnMouseReleased(this::onMouseReleaseListener);
    }

    public abstract void drawEffort(double x1, double y1, double x2, double y2);

    public abstract void draw(double x1,double y1, double x2, double y2);

    private void onMousePressedListener(MouseEvent e){

        this.startEdit = true;

        this.startX = e.getX();
        this.startY = e.getY();
    }

    private void onMouseDraggedListener(MouseEvent e){
        if(!this.startEdit){
            return;
        }

        this.currentX = e.getX();
        this.currentY = e.getY();

        drawEffort(startX,startY,currentX,currentY);
    }

    private void onMouseReleaseListener(MouseEvent e){
        if(!this.startEdit){
            return;
        }
        this.currentX = e.getX();
        this.currentY = e.getY();

        this.startEdit = false;
        draw(startX,startY,currentX,currentY);
    }
}
