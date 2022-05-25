package edu.service.Impl;

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
    public CanvasUpdateResponse canvasUpdate(ClientInfo clientInfo,CanvasUpdateRequest canvasUpdateRequest){
        MyCanvas myCanvas = MyCanvas.getInstance();
        myCanvas.addUpdateCommand(canvasUpdateRequest.getCommand());
        return new CanvasUpdateResponse(true);
    }

    @Override
    public CanvasResponse getCanvas(ClientInfo clientInfo, CanvasRequest canvasRequest){
        MyCanvas myCanvas = MyCanvas.getInstance();
        if(canvasRequest.getSnapshotIndex() >= myCanvas.getSnapshotIndex()){
            return new CanvasResponse(true,null,myCanvas.getSnapshotIndex());
        }
        myCanvas.getSnapshotLock().lock();
        byte[] imageBytes = null;
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(myCanvas.getSnapshot(), null);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100000);
                ImageIO.write(bufferedImage,"png",byteArrayOutputStream);
                imageBytes = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new CanvasResponse(true,imageBytes,myCanvas.getSnapshotIndex());
        }finally {
            myCanvas.getSnapshotLock().unlock();
        }
    }
}
