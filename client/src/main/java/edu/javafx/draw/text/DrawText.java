package edu.javafx.draw.text;

import edu.javafx.draw.Draw;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * DrawText describes how to draw text on canvas
 * mainly describes how to behave on mouse pressed and key pressed
 * @author lingxiao
 */
public abstract class DrawText extends Draw {

    private double startX, startY;
    private String text;

    protected DrawText(Canvas canvasEffort,
                        ColorPicker outlineColorPicker,
                        ColorPicker fillColorPicker) {
        super(canvasEffort, outlineColorPicker, fillColorPicker);
    }

    public abstract void drawEffort(double x1, double y1, String text);

    public abstract void draw(double x1, double y1, String text);

    @Override
    public void loadAction(){
        canvasEffort.setOnMousePressed(this::onMousePressedListener);
        canvasEffort.setOnKeyPressed(this::onKeyPressedListener);
    }

    private void onMousePressedListener(MouseEvent e){

        this.startEdit = true;
        canvasEffort.requestFocus();

        this.startX = e.getX();
        this.startY = e.getY();
        this.text = "";
        drawEffort(startX,startY,text);
    }

    private void onKeyPressedListener(KeyEvent e){
        if(!this.startEdit){
            return;
        }

        if(e.getCode() == KeyCode.ENTER && e.isShiftDown()) {
            text += "\n";
            drawEffort(startX,startY,text);
        }else if(e.getCode() == KeyCode.ENTER){
            draw(startX,startY,text);
            startEdit = false;
        }else if(e.getCode() == KeyCode.BACK_SPACE){
            text = text.substring(0,text.length()-1);
            drawEffort(startX,startY,text);
        }
        else {
            text += e.getText();
            drawEffort(startX,startY,text);
        }
    }
}
