package com.example.kodulf.utilsdemo.utils.http.services;


import com.example.kodulf.utilsdemo.utils.http.okhttp.OKhttpService;
import com.example.kodulf.utilsdemo.utils.schedule.ErrorSchedule;

/**
 * Created by zhangyinshan on 2017/5/3.
 */
public class ServiceContext {

    private static JuHeService juHeService  = new JuHeService();
    private static OKhttpService oKhttpService = new OKhttpService();
    private static ErrorSchedule errorSchedule = new ErrorSchedule();

    public static JuHeService getJuHeService(){return juHeService;}
    public static OKhttpService getoKhttpService(){return oKhttpService;}
    public static ErrorSchedule getErrorSchedule() {return errorSchedule;}
}
