package edu.dto;


public class CanvasUpdateRequest {

    Command command;

    public CanvasUpdateRequest(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
