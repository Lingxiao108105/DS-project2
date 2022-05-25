package edu.service.Impl;

import edu.dto.*;
import edu.javafx.MyCanvas;
import edu.service.CanvasService;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
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
        myCanvas.getSnapshotLock().lock();
        try {
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(myCanvas.getSnapshot(), null);
            byte[] imageBytes = null;
            try {
                ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(renderedImage);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100000);
                int counter = 0;
                while (true) {
                    try {
                        byteArrayOutputStream.write(imageOutputStream.readByte());
                        counter++;
                    } catch (EOFException e) {
                        break;
                    }
                }
                System.out.println("Total bytes of image: " + counter);
                imageBytes = byteArrayOutputStream.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return new CanvasResponse(true,imageBytes);
        }finally {
            myCanvas.getSnapshotLock().unlock();
        }

    }
}
