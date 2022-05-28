package edu.service.Impl;

import edu.dto.CanvasRequest;
import edu.dto.CanvasResponse;
import edu.dto.CanvasUpdateRequest;
import edu.dto.CanvasUpdateResponse;
import edu.javafx.MyCanvas;
import edu.service.CanvasService;

/**
 * service for canvas in server
 * @author lingxiao
 */
public class CanvasServiceImpl implements CanvasService {

    /**
     * send a command of updating canvas
     * @param canvasUpdateRequest canvasUpdateRequest
     * @return CanvasUpdateResponse
     */
    @Override
    public CanvasUpdateResponse canvasUpdate(CanvasUpdateRequest canvasUpdateRequest){
        MyCanvas myCanvas = MyCanvas.getInstance();
        myCanvas.addUpdateCommand(canvasUpdateRequest.getCommand());
        return new CanvasUpdateResponse(true);
    }

    /**
     * request of get the canvas from server
     */
    @Override
    public CanvasResponse getCanvas(CanvasRequest canvasRequest){
        MyCanvas myCanvas = MyCanvas.getInstance();
        if(canvasRequest.getSnapshotIndex() >= myCanvas.getSnapshotIndex()){
            return new CanvasResponse(true,null,myCanvas.getSnapshotIndex());
        }
        myCanvas.getSnapshotLock().lock();
        try {
            return new CanvasResponse(true,myCanvas.getSnapshotBytes(),myCanvas.getSnapshotIndex());
        }finally {
            myCanvas.getSnapshotLock().unlock();
        }
    }
}
