package it.unimib.kaisenapp.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

//PATTERN SINGLETON
public class AppExecutor {
    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(Constants.CORE_POOL_SIZE); //the number of threads to keep in the pool, even if they are idle.
    private static AppExecutor instance;

    public static AppExecutor getInstance(){
        if(instance==null){
            instance=new AppExecutor();
        }
        return instance;
    }

    public ScheduledExecutorService networkIO(){
        return mNetworkIO;
    }
}
