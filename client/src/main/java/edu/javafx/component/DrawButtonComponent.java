package edu.javafx.component;

import edu.javafx.draw.Draw;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * DrawButtonComponent controls the draw button function of canvas
 * construct a DrawButtonComponent with button and draw.
 * Then when click button, the corresponding draw function will be load on canvas
 * @author lingxiao
 */
public class DrawButtonComponent {

    protected Draw draw = null;

    public DrawButtonComponent(Button button, Draw draw) {
        this.draw = draw;
        button.setOnMouseClicked(this::onMouseClickedListener);
    }

    protected void onMouseClickedListener(MouseEvent e){
        draw.unloadAction();
        draw.loadAction();
    }
}
