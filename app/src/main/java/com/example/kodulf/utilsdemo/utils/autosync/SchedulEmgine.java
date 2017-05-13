package com.example.kodulf.utilsdemo.utils.autosync;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Kodulf on 2017/5/4.
 *
 * 一定要注意的是，这里不要将线程放到线程池里面去执行，因为如果放到线程池执行，那么它的状态就不好判断了
 * 管理在这里进行管理，执行的内容在每一个Thread内部执行
 */

public class SchedulEmgine {

    private static CustomerSchedule syncCustomerThread;

    private static final int SYNC_CUSTOMER_MESSAGE = 1;
    /**
     * 间隔时间的设置
     */
    private static final int SYNC_CUSTOMER_MESSAGE_DELAYED_TIME = 1*60*1000;

    private static Handler mHandler;

    public static void start(){
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                int what = msg.what;
                switch (what){
                    case SYNC_CUSTOMER_MESSAGE:
                        syncCustomerThreadRun();
                        mHandler.sendEmptyMessageDelayed(SYNC_CUSTOMER_MESSAGE, SYNC_CUSTOMER_MESSAGE_DELAYED_TIME);
                        break;
                }
                return false;

            }
        });
        init();
    }

    /**
     * 初始化，发送handler消息给handler，让handler 去处理
     */
    public static void init(){
        mHandler.sendEmptyMessage(SYNC_CUSTOMER_MESSAGE);
    }


    /**
     * 同步会员的
     */
    private static void syncCustomerThreadRun() {

        if(syncCustomerThread == null){
            System.out.println("thread is null!");
            syncCustomerThread = new CustomerSchedule();
            //只有这个时候才会去执行
            syncCustomerThread.start();
            return;
        }

        //如果正在运行，那么什么也不错
        System.out.println("thread is not null!");

        //首先判断没有在运行，那么重新运行
        System.out.println("thread status"+syncCustomerThread.getState());
        if (syncCustomerThread.getState() == Thread.State.TERMINATED){
            System.out.println("thread status ?"+syncCustomerThread.getState());
            syncCustomerThread = new CustomerSchedule();
            //只有这个时候才会去执行
            syncCustomerThread.start();
        }
    }
}
