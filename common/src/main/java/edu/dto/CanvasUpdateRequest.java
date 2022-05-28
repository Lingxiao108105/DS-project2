package edu.dto;

/**
 * request for update canvas to server
 * @author lingxiao
 */
public class CanvasUpdateRequest {

    //command of update
    Command command;

    public CanvasUpdateRequest(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
