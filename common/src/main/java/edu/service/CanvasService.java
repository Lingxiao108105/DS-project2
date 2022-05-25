package edu.service;

import edu.dto.*;

public interface CanvasService {

    public CanvasUpdateResponse canvasUpdate(ClientInfo clientInfo,CanvasUpdateRequest canvasUpdateRequest);

    public CanvasResponse getCanvas(ClientInfo clientInfo, CanvasRequest canvasRequest);

}
