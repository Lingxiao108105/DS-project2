package edu.javafx;

import edu.LifeCycle;
import edu.ThreadPool.ServerThreadPool;
import edu.data.ClusterInfo;
import edu.dto.Command;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class MyCanvas implements LifeCycle {

    private static MyCanvas myCanvas;

    private Canvas canvas;
    private WritableImage snapshot;
    private final ReentrantLock snapshotLock = new ReentrantLock();
    private ConcurrentLinkedDeque<Command> canvasUpdate;
    private ScheduledFuture<?> canvasUpdateTask = null;


    public static MyCanvas getInstance(){
        if(myCanvas == null){
            myCanvas = new MyCanvas();
            myCanvas.init();
        }
        return myCanvas;
    }

    @Override
    public void init() {
        this.canvas = new Canvas(776.0,721.0);
        this.canvasUpdate = new ConcurrentLinkedDeque<>();
        Platform.runLater(()->{
            this.snapshot = canvas.snapshot(null,null);
        });
        this.canvasUpdateTask = ServerThreadPool.getInstance().getScheduledExecutorService().scheduleAtFixedRate(
                ()->{
                    Platform.runLater(()->{
                        MyCanvas.getInstance().executeAll();
                    });
                },
                50,
                50,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public void stop() {
        this.canvas = null;
        this.canvasUpdate = null;
        this.snapshot= null;
        this.canvasUpdateTask.cancel(true);
    }

    public void addUpdateCommand(Command command){
        this.canvasUpdate.add(command);
    }

    public void executeAll(){
        int size = this.canvasUpdate.size();
        for (int i=0;i<size;i++){
            Command command = this.canvasUpdate.remove();
            command.execute(this.canvas);
        }
        if(size > 0){
            snapshotLock.lock();
            try {
                this.snapshot = this.canvas.snapshot(null, null);
            }
            finally {
                snapshotLock.unlock();
            }
        }
    }
}
