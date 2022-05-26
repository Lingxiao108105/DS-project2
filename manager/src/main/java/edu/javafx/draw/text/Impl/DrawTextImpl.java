package edu.javafx.draw.text.Impl;

import edu.common.exception.NotInitException;
import edu.common.util.DrawTextUtil;
import edu.dto.Command;
import edu.dto.Impl.DrawTextCommand;
import edu.javafx.draw.shape.Impl.DrawTriangle;
import edu.javafx.draw.text.DrawText;
import edu.service.SendCommandService;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;

public class DrawTextImpl extends DrawText {

    private static DrawTextImpl drawText = null;

    public static DrawTextImpl init(Canvas canvasEffort,
                                    ColorPicker outlineColorPicker,
                                    ColorPicker fillColorPicker) {
        drawText = new DrawTextImpl(canvasEffort,outlineColorPicker,fillColorPicker);
        return drawText;
    }

    public static DrawTextImpl getInstance(){
        if(drawText == null){
            throw new NotInitException(DrawTriangle.class.getName());
        }
        return drawText;
    }

    private DrawTextImpl(Canvas canvasEffort,
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
        SendCommandService.sendCommand(command);
    }
}
