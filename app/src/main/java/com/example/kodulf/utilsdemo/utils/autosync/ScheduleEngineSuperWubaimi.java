package com.example.kodulf.utilsdemo.utils.autosync;

import com.example.kodulf.utilsdemo.utils.http.services.ServiceContext;
import com.example.kodulf.utilsdemo.utils.schedule.engine.Schedule;

/**
 * Created by Kodulf on 2017/5/17.
 */

public class ScheduleEngineSuperWubaimi {

    //其实就是开启几个thread而已
    private Thread mainThread;

    public synchronized void start(){

        if(mainThread==null){
            mainThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try{
                            //todo startSchedule
                            //在这里面启动服务
                            /*
                             * 注意这里的同步，如果是使用了while 来进行循环的执行的话，不要放在线程池里面执行的
                             * 如果是只需要按照engine的要求30分钟执行一次，那么就放到线程池里面，执行完了就释放了
                             */
                            startSchedule(ServiceContext.getErrorSchedule());
                            startSchedule(ServiceContext.getOrderSchedule());

                            Thread.sleep(30*60*1000);
                        } catch (InterruptedException e) {
                            //可能会在执行startSchedule的时候报错，这个时候还是需要重新睡觉的，
                            //不然会不停的报错和启动schedule
                            try {
                                Thread.sleep(30*60*1000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                    }
                }
            });

            mainThread.start();

        }
        if(Thread.State.TERMINATED == mainThread.getState()){
            mainThread.start();
        }
    }

    public void startSchedule(Schedule schedule){
        schedule.syncSchedule();
    }


}
