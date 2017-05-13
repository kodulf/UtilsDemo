package com.example.kodulf.utilsdemo;

import android.os.Bundle;

import com.example.kodulf.utilsdemo.activity.BaseActivity;


public class MainActivity extends BaseActivity{

    public static final int UMENG_PHONE_STATE_REQUEST = 998;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    protected void findView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setOnClick() {

    }


//    /**
//     *
//     * @param view
//     */
//    public void update(View view) {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //启动服务
//                Intent service = new Intent(MainActivity.this,UpdateService.class);
//                startService(service);
//            }
//        }).start();
//
//    }
//
//    public void jumptoVoice(View view) {
//
//        Intent intent = new Intent(this,VoiceActivity.class);
//        startActivity(intent);
//    }
}
