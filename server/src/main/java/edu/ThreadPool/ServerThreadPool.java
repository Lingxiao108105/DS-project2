package edu.ThreadPool;

import edu.LifeCycle;

import java.util.concurrent.*;

/**
 * <p>
 *  ServerThreadPool
 * </p>
 *
 * @author Lingxiao
 */
public class ServerThreadPool implements LifeCycle {

    private ExecutorService executorService;
    private ScheduledExecutorService scheduledExecutorService;
    private ScheduledFuture canvasUpdateTask = null;


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
