package com.example.kodulf.utilsdemo;

import android.content.Intent;
import android.util.Log;

import com.example.kodulf.utilsdemo.activity.BaseActivity;
import com.example.kodulf.utilsdemo.utils.voice.WakeupService;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import org.litepal.LitePalApplication;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Kodulf on 2017/5/5.
 */

public class MyApplication extends LitePalApplication {

    public static LinkedList<BaseActivity> mActivityList;

    @Override
    public void onCreate() {
        super.onCreate();
        //语音部分的初始化

        mActivityList = new LinkedList<>();

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "="+"590de610");//后面的根据每次的不同可以修改的

        //startWakeUpService();
    }

    /**
     * 退出应用
     */
    public void exit(){

        Iterator<BaseActivity> iterator = mActivityList.iterator();
        while (iterator.hasNext()){
            BaseActivity next = iterator.next();
            next.finish();
        }

        Log.d("kodulf","exit");
        System.exit(0);
    }


    /**
     * 启动wakeup的服务
     */
    public void startWakeUpService() {
        Intent intent = new Intent(this,WakeupService.class);
        startService(intent);
    }
}
