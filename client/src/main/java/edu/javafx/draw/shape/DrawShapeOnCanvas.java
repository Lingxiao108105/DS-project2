package edu.javafx.draw.shape;

import edu.draw.DrawShape;
import edu.dto.Command;
import edu.dto.Impl.DrawShapeCommand;
import edu.javafx.draw.DrawOnCanvas;
import edu.service.Impl.ServerService;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;

/**
 * DrawShape describes how to draw shape on canvas
 * mainly describes how to behave on mouse press, drag and release action
 * @author lingxiao
 */
public class DrawShapeOnCanvas extends DrawOnCanvas {

    private double startX, startY, currentX, currentY;
    private final DrawShape drawShape;

    public DrawShapeOnCanvas(Canvas canvasEffort,
                                ColorPicker outlineColorPicker,
                                ColorPicker fillColorPicker, DrawShape drawShape) {
        super(canvasEffort, outlineColorPicker, fillColorPicker);
        this.drawShape = drawShape;
    }

    @Override
    public void loadAction(){
        canvasEffort.setOnMousePressed(this::onMousePressedListener);
        canvasEffort.setOnMouseDragged(this::onMouseDraggedListener);
        canvasEffort.setOnMouseReleased(this::onMouseReleaseListener);
    }

    public void drawEffort(){
        graphicsContextEffort.clearRect(0, 0, canvasEffort.getWidth() , canvasEffort.getHeight());
        drawShape.draw(graphicsContextEffort,outlineColorPicker.getValue(),fillColorPicker.getValue(),startX,startY,currentX,currentY);
    }

    public void draw(){
        Command command = new DrawShapeCommand(startX,startY,currentX,currentY,
                outlineColorPicker.getValue(), fillColorPicker.getValue(),drawShape);
        ServerService.sendCommand(command);
    }

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

        drawEffort();
    }

    private void onMouseReleaseListener(MouseEvent e){
        if(!this.startEdit){
            return;
        }
        this.currentX = e.getX();
        this.currentY = e.getY();

        this.startEdit = false;

        draw();
    }
}
