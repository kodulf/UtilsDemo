package com.example.kodulf.utilsdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kodulf.utilsdemo.R;
import com.example.kodulf.utilsdemo.entity.City;
import com.example.kodulf.utilsdemo.utils.http.ResultList;
import com.example.kodulf.utilsdemo.utils.http.okhttp.OkHttpResponseCallback;
import com.example.kodulf.utilsdemo.utils.http.services.ServiceContext;

import java.util.List;

import okhttp3.Call;


public class MainActivity extends BaseActivity{

    public static final int UMENG_PHONE_STATE_REQUEST = 998;
    private TextView mTextView;


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
        mTextView = ((TextView) findViewById(R.id.details));
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

    public void startOkHttp(View view) {
        ServiceContext.getoKhttpService().getList(new OkHttpResponseCallback<ResultList<City>>() {
            @Override
            public void onFailure(Call call, Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"fail",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final ResultList<City> cityResultList) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        List<City> result = cityResultList.getResult();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < result.size(); i++) {
                            City city = result.get(i);
                            sb.append( city.getCity()+" "+city.getFlows());
                            sb.append("\n");
                        }
                        mTextView.setText(sb.toString()+"");
                    }
                });

            }
        });

    }

    public void jumptoVoice(View view) {
        Intent intent = new Intent(this,VoiceActivity.class);
        startActivity(intent);
    }

    public void jumpToClock(View view) {
        Intent intent = new Intent(this,ClockActivity.class);
        startActivity(intent);
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
