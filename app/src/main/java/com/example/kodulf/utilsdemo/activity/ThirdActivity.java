package com.example.kodulf.utilsdemo.activity;

import android.os.Bundle;
import android.view.View;

import com.example.kodulf.utilsdemo.MyApplication;
import com.example.kodulf.utilsdemo.R;

public class ThirdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
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


    public void exitApp(View view) {
        MyApplication application = (MyApplication) getApplication();
        application.exit();
    }

}
