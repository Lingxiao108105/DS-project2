package edu.ThreadPool;

import edu.LifeCycle;
import lombok.Getter;

import java.util.concurrent.*;

/**
 * <p>
 *  ClientThreadPool
 * </p>
 *
 * @author Lingxiao
 */
@Getter
public class ClientThreadPool implements LifeCycle {

    private static ClientThreadPool clientThreadPool = null;

    private ExecutorService executorService;
    private ScheduledExecutorService scheduledExecutorService;

    public static ClientThreadPool getInstance(){
        if(clientThreadPool == null){
            clientThreadPool = new ClientThreadPool();
            clientThreadPool.init();
        }
        return clientThreadPool;
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
