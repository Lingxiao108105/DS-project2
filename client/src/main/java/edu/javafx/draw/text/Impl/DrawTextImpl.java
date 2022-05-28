package edu.javafx.draw.text.Impl;

import edu.common.util.DrawTextUtil;
import edu.dto.Command;
import edu.dto.Impl.DrawTextCommand;
import edu.javafx.draw.text.DrawText;
import edu.service.Impl.ServerService;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;

/**
 * describes how to draw text on canvas
 * mainly describes how to draw effort and draw on canvas
 * @author lingxiao
 */
public class DrawTextImpl extends DrawText {


    public DrawTextImpl(Canvas canvasEffort,
                     ColorPicker outlineColorPicker,
                     ColorPicker fillColorPicker) {
        super(canvasEffort, outlineColorPicker, fillColorPicker);
    }

    @Override
    public void drawEffort(double x1, double y1, String text) {
        graphicsContextEffort.clearRect(0, 0, canvasEffort.getWidth() , canvasEffort.getHeight());
        DrawTextUtil.drawText(graphicsContextEffort,outlineColorPicker.getValue(),fillColorPicker.getValue(),x1,y1,text);
    }

    @Override
    public void draw(double x1, double y1, String text) {
        Command command = new DrawTextCommand(x1, y1, text, outlineColorPicker.getValue(), fillColorPicker.getValue());
        ServerService.sendCommand(command);
    }
}
