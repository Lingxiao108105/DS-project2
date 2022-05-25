package edu.dto;


import java.io.Serializable;

public class CanvasUpdateRequest implements Serializable {

    Command command;

    public CanvasUpdateRequest(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
