package com.example.kodulf.utilsdemo.utils.autosync;

/**
 * Created by Kodulf on 2017/5/4.
 */

public class CustomerSchedule extends Thread {

    @Override
    public synchronized void run() {
        //do something
        System.out.println(getName()+" kodulf 同步客户的信息");

        try {
            Thread.sleep(10000);
            System.out.println(getName()+" 睡觉完了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
