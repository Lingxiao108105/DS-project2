package edu.service;

import edu.dto.*;

/**
 * service for canvas in server
 * @author lingxiao
 */
public interface CanvasService {

    /**
     * send a command of updating canvas
     * @param canvasUpdateRequest canvasUpdateRequest
     * @return CanvasUpdateResponse
     */
    public CanvasUpdateResponse canvasUpdate(CanvasUpdateRequest canvasUpdateRequest);

    /**
     * request of get the canvas from server
     */
    public CanvasResponse getCanvas(CanvasRequest canvasRequest);

}
