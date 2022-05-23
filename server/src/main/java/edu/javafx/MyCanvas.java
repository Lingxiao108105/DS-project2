package edu.javafx;

import edu.dto.Command;
import javafx.scene.canvas.Canvas;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class MyCanvas {

    private Canvas canvas;
    private ConcurrentLinkedDeque<Command> canvasUpdate;
    private ConcurrentHashMap<Integer, ConcurrentLinkedDeque<Command>> canvasEffort;


}
