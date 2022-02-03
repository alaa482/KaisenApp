package it.unimib.kaisenapp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

//PATTERN SINGLETON
//esegue chiamate api in background, non sul thread principale
public class AppExecutor {
    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(5);
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
