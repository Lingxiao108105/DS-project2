package edu.service;

import edu.dto.*;

public interface CanvasService {

    public CanvasUpdateResponse canvasUpdate(CanvasUpdateRequest canvasUpdateRequest);

    public CanvasResponse getCanvas(CanvasRequest canvasRequest);

}
