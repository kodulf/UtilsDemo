package com.example.kodulf.utilsdemo;

import android.util.Log;

import junit.framework.TestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kodulf on 2017/5/10.
 */

public class TestCountDownLatch extends TestCase {
    public void test(){
        try {
            testCountDownLatch();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void testCountDownLatch() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100000000; i++) {
                    System.out.println("++++++");
                }
                countDownLatch.countDown();
            }
        }).start();

        countDownLatch.await(30 * 60 * 10, TimeUnit.MILLISECONDS);

        Thread.sleep(60*1000);
        Log.d("kodulf","test end");
    }
}
