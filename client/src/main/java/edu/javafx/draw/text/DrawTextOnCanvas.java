package edu.javafx.draw.text;

import edu.draw.DrawText;
import edu.dto.Command;
import edu.dto.Impl.DrawTextCommand;
import edu.javafx.draw.DrawOnCanvas;
import edu.service.Impl.ServerService;
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
public class DrawTextOnCanvas extends DrawOnCanvas {

    private double startX, startY;
    private String text;
    private final DrawText drawText;

    public DrawTextOnCanvas(Canvas canvasEffort,
                               ColorPicker outlineColorPicker,
                               ColorPicker fillColorPicker, DrawText drawText) {
        super(canvasEffort, outlineColorPicker, fillColorPicker);
        this.drawText = drawText;
    }

    public void drawEffort(){
        graphicsContextEffort.clearRect(0, 0, canvasEffort.getWidth() , canvasEffort.getHeight());
        drawText.draw(graphicsContextEffort,outlineColorPicker.getValue(),fillColorPicker.getValue(),startX,startY,text);
    }

    public void draw(){
        Command command = new DrawTextCommand(startX, startY, text,
                outlineColorPicker.getValue(), fillColorPicker.getValue(),drawText);
        ServerService.sendCommand(command);
    }

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
        drawEffort();
    }

    private void onKeyPressedListener(KeyEvent e){
        if(!this.startEdit){
            return;
        }

        if(e.getCode() == KeyCode.ENTER && e.isShiftDown()) {
            text += "\n";
            drawEffort();
        }else if(e.getCode() == KeyCode.ENTER){
            draw();
            startEdit = false;
        }else if(e.getCode() == KeyCode.BACK_SPACE){
            text = text.substring(0,text.length()-1);
            drawEffort();
        }
        else {
            text += e.getText();
            drawEffort();
        }
    }
}
