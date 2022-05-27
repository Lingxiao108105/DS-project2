package edu.service.Impl;

import edu.dto.CanvasRequest;
import edu.dto.CanvasResponse;
import edu.dto.CanvasUpdateRequest;
import edu.dto.CanvasUpdateResponse;
import edu.javafx.MyCanvas;
import edu.service.CanvasService;

public class CanvasServiceImpl implements CanvasService {

    @Override
    public CanvasUpdateResponse canvasUpdate(CanvasUpdateRequest canvasUpdateRequest){
        MyCanvas myCanvas = MyCanvas.getInstance();
        myCanvas.addUpdateCommand(canvasUpdateRequest.getCommand());
        return new CanvasUpdateResponse(true);
    }

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
