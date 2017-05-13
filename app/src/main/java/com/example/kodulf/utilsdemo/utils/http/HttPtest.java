package com.example.kodulf.utilsdemo.utils.http;

import com.example.kodulf.utilsdemo.entity.City;
import com.example.kodulf.utilsdemo.utils.http.okhttp.OkHttpResponseCallback;
import com.example.kodulf.utilsdemo.utils.http.services.ServiceContext;

import okhttp3.Call;

/**
 * Created by Kodulf on 2017/5/4.
 */

public class HttPtest {

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ServiceContext.getoKhttpService().getList(new OkHttpResponseCallback<ResultList<City>>() {

                    @Override
                    public void onFailure(Call call, Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, ResultList<City> cityResultList) {
                        System.out.println(cityResultList);
                    }

                });

                ServiceContext.getoKhttpService().getPhoneCanCharge("17761838076", "10", new OkHttpResponseCallback<Result<String>>() {


                    @Override
                    public void onFailure(Call call, Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Result<String> stringResult) {
                        System.out.println(stringResult);
                    }

                });

                ServiceContext.getJuHeService().getPhoneCanCharge("17761838076", "10", new RequestCallback<Result<String>>() {
                    @Override
                    public void onFailure(Result<String> stringResult, Exception e) {

                    }

                    @Override
                    public void onResponse(Result<String> stringResult) {
                        System.out.println(stringResult);
                    }
                });
            }


        }).
                start();
    }

}
