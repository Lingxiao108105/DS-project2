package edu.javafx;

import edu.LifeCycle;
import edu.ThreadPool.ServerThreadPool;
import edu.common.util.ByteAndImageConverterUtil;
import edu.dto.Command;
import edu.service.Impl.ClientService;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import lombok.Getter;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class MyCanvas implements LifeCycle {

    private static MyCanvas myCanvas;

    private Canvas canvas;
    private byte[] snapshotBytes;
    private long snapshotIndex = 0;
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
            this.snapshotBytes = ByteAndImageConverterUtil.imageToBytes(this.canvas.snapshot(null, null));
        });
        this.canvasUpdateTask = ServerThreadPool.getInstance().getScheduledExecutorService().scheduleWithFixedDelay(
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
        this.canvasUpdateTask.cancel(true);
        this.canvas = null;
        this.canvasUpdate = null;
        this.snapshotBytes= null;
        myCanvas = null;
    }

    public void incrementSnapshotIndex(){
        this.snapshotIndex++;
    }

    public void setSnapshotBytes(byte[] snapshotBytes) {
        this.snapshotBytes = snapshotBytes;
    }

    public void addUpdateCommand(Command command){
        this.canvasUpdate.add(command);
    }

    public void executeAll(){
        try {
            int size = this.canvasUpdate.size();
            for (int i=0;i<size;i++){
                Command command = this.canvasUpdate.remove();
                try {
                    command.execute(this.canvas);
                }catch (Exception e){
                    System.out.println("Fail to update canvas in My canvas!");
                }

            }
            if(size > 0){
                ClientService.multicastSnapshot();
            }
        }catch (Exception ignored){
        }

    }
}
