package edu.javafx;

import edu.Common.Exception.NotInitException;
import edu.ThreadPool.ClientThreadPool;
import edu.config.ClientConfig;
import edu.dto.CanvasRequest;
import edu.dto.CanvasResponse;
import edu.rpc.RpcClient;
import edu.service.CanvasService;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class UpdateCanvasScheduler{

    private static UpdateCanvasScheduler updateCanvasScheduler = null;

    private Canvas canvas;
    private ScheduledFuture<?> updateCanvasTask;

    public static void init(Canvas canvas) {
        updateCanvasScheduler = new UpdateCanvasScheduler(canvas);
        updateCanvasScheduler.updateCanvasTask = ClientThreadPool.getInstance().getScheduledExecutorService().scheduleAtFixedRate(
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
        CanvasResponse canvasResponse = canvasService.getCanvas(ClientConfig.clientInfo, new CanvasRequest());
        if(canvasResponse.isSuccess()){
            Platform.runLater(() ->{
                byte[] imageBytes = canvasResponse.getImageBytes();
                Image img = new Image(new ByteArrayInputStream(imageBytes));
                canvas.getGraphicsContext2D().drawImage(img,0,0);
            });
        }
        else {
            System.out.println("Fail to get snap from server");
        }

    }
}
