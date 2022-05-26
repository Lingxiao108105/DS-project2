package edu.service.Impl;

import edu.common.util.ByteAndImageConverterUtil;
import edu.dto.*;
import edu.javafx.MyCanvas;
import edu.service.CanvasService;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;

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
