package edu.javafx;

import edu.common.exception.NotInitException;
import edu.ThreadPool.ClientThreadPool;
import edu.config.ClientConfig;
import edu.dto.CanvasRequest;
import edu.dto.CanvasResponse;
import edu.rpc.RpcClient;
import edu.service.CanvasService;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class UpdateCanvasScheduler{

    private static UpdateCanvasScheduler updateCanvasScheduler = null;

    private Canvas canvas;
    private ScheduledFuture<?> updateCanvasTask;
    private long snapshotIndex = 0;

    public static void init(Canvas canvas) {
        updateCanvasScheduler = new UpdateCanvasScheduler(canvas);
        updateCanvasScheduler.updateCanvasTask = ClientThreadPool.getInstance().getScheduledExecutorService().scheduleWithFixedDelay(
                ()->{
                    updateCanvasScheduler.updateCanvas();
                },
                50,
                50,
                TimeUnit.MILLISECONDS
        );
    }

    public static UpdateCanvasScheduler getInstance(){
        if(updateCanvasScheduler == null){
            throw new NotInitException(UpdateCanvasScheduler.class.getName());
        }
        return updateCanvasScheduler;
    }

    public static void stop() {
        updateCanvasScheduler.updateCanvasTask.cancel(true);
        updateCanvasScheduler = null;
    }

    private UpdateCanvasScheduler(Canvas canvas) {
        this.canvas = canvas;
    }

    private void updateCanvas(){
        CanvasService canvasService = RpcClient.getInstance().getCanvasService();
        CanvasResponse canvasResponse = canvasService.getCanvas(new CanvasRequest(snapshotIndex));
        if(canvasResponse == null){
            return;
        }
        if(canvasResponse.isSuccess()){
            //update snapshot Index
            this.snapshotIndex = Math.max(this.snapshotIndex,canvasResponse.getSnapshotIndex());
            //update canvas
            Platform.runLater(() ->{
                byte[] imageBytes = canvasResponse.getImageBytes();
                if(imageBytes==null){
                    return;
                }
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
                BufferedImage bufferedImage = null;
                try {
                    bufferedImage = ImageIO.read(byteArrayInputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                    e.printStackTrace();
                }
                WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);
                canvas.getGraphicsContext2D().drawImage(writableImage,0,0);
            });
        }
        else {
            System.out.println("Fail to get snap from server");
        }
    }
}
