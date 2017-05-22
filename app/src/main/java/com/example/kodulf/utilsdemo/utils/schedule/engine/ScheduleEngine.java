package com.example.kodulf.utilsdemo.utils.schedule.engine;

import android.util.Log;

import com.example.kodulf.utilsdemo.utils.http.services.ServiceContext;

/**
 * Created by shiang on 16-1-28.
 */
public class ScheduleEngine {
    private Thread scheuleThread;
    private boolean started = false;
    public synchronized void start() {
        if(started){
            return;
        }
        started=true;
        if(scheuleThread==null){
            scheuleThread =  new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        try {
                            Log.d("scheule","scheuleThread main running");
                            startSchedule(ServiceContext.getErrorSchedule());
                            Thread.sleep(30*60*1000);//正常结束，休息一分钟
                        } catch (Throwable e) {
                            //忽略
                            try {
                                Thread.sleep(30*60*1000);//正常结束，休息一分钟
                            } catch (Throwable e1) {
                                //忽略
                                //                            e1.printStackTrace();
                            }
                        }
                    }
                }
            });
            scheuleThread.start();
        }
        if(Thread.State.TERMINATED.equals(scheuleThread.getState())){
            scheuleThread.start();
        }
    }
    void startSchedule(Schedule schedule){
        try{
            schedule.syncSchedule();
        }catch (Throwable e){
            //忽略
        }

    }
}
