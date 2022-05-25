package edu.javafx.component;


import edu.Common.Exception.NotInitException;
import edu.javafx.shape.DrawShape;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class CanvasEffortComponent{

    private static CanvasEffortComponent canvasEffortComponent = null;

    private double startX, startY, currentX, currentY;

    private DrawShape drawShape = null;
    private boolean startEdit = false;

    public static void init(Canvas canvasEffort) {
        canvasEffortComponent = new CanvasEffortComponent(canvasEffort);
    }

    public static CanvasEffortComponent getInstance(){
        if(canvasEffortComponent == null){
            throw new NotInitException(CanvasEffortComponent.class.getName());
        }
        return canvasEffortComponent;
    }

    private CanvasEffortComponent(Canvas canvasEffort) {
        canvasEffort.setOnMousePressed(this::onMousePressedListener);
        canvasEffort.setOnMouseDragged(this::onMouseDraggedListener);
        canvasEffort.setOnMouseReleased(this::onMouseReleaseListener);
    }

    public void setDrawShape(DrawShape drawShape) {
        this.drawShape = drawShape;
    }

    private void onMousePressedListener(MouseEvent e){
        if(this.drawShape == null){
            return;
        }

        this.startEdit = true;

        this.startX = e.getX();
        this.startY = e.getY();
    }

    private void onMouseDraggedListener(MouseEvent e){
        if(drawShape == null || !this.startEdit){
            return;
        }

        this.currentX = e.getX();
        this.currentY = e.getY();

        this.drawShape.drawEffort(startX,startY,currentX,currentY);
    }

    private void onMouseReleaseListener(MouseEvent e){
        if(drawShape == null){
            return;
        }

        this.startEdit = false;
        this.drawShape.draw(startX,startY,currentX,currentY);
    }

}
