package com.example.kodulf.utilsdemo.utils.http.services;


import com.example.kodulf.utilsdemo.utils.http.okhttp.OKhttpService;

/**
 * Created by zhangyinshan on 2017/5/3.
 */
public class ServiceContext {

    private static JuHeService juHeService  = new JuHeService();
    private static OKhttpService oKhttpService = new OKhttpService();


    public static JuHeService getJuHeService(){return juHeService;}
    public static OKhttpService getoKhttpService(){return oKhttpService;}
}
