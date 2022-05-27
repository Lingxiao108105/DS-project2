package edu.ThreadPool;

import edu.LifeCycle;
import lombok.Getter;

import java.util.concurrent.*;

/**
 * <p>
 *  ServerThreadPool
 * </p>
 *
 * @author Lingxiao
 */
@Getter
public class ServerThreadPool implements LifeCycle {

    private static ServerThreadPool serverThreadPool = null;

    private ExecutorService executorService;
    private ScheduledExecutorService scheduledExecutorService;

    public static ServerThreadPool getInstance(){
        if(serverThreadPool == null){
            serverThreadPool = new ServerThreadPool();
            serverThreadPool.init();
        }
        return serverThreadPool;
    }


    @Override
    public void init() {
        executorService = new ThreadPoolExecutor(
                20,
                20,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        scheduledExecutorService = Executors.newScheduledThreadPool(5);
    }


    @Override
    public void stop() {
        executorService.shutdown();
        scheduledExecutorService.shutdown();
    }
}
