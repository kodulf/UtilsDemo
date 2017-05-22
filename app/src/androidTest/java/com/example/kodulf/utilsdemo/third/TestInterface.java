package com.example.kodulf.utilsdemo.third;

import com.example.kodulf.utilsdemo.entity.City;
import com.example.kodulf.utilsdemo.utils.Log;
import com.example.kodulf.utilsdemo.utils.http.RequestCallback;
import com.example.kodulf.utilsdemo.utils.http.ResultList;
import com.example.kodulf.utilsdemo.utils.http.services.ServiceContext;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Kodulf on 2017/5/20.
 */

public class TestInterface {


    @BeforeClass
    public static void prepare(){
        Log.d("kodulf","静态方法的准备，针对所有的");
    }

    @Before
    public void init(){
        Log.d("kodulf","初始化相关的");
    }

    @Test
    public void testJuHe(){
        ServiceContext.getJuHeService().getList(new RequestCallback<ResultList<City>>() {
            @Override
            public void onFailure(ResultList<City> cityResultList, Exception e) {

            }

            @Override
            public void onResponse(ResultList<City> cityResultList) {

            }
        });
    }

    @After
    public  void after(){
        Log.d("kodulf","运行完了");
    }

    @AfterClass
    public static void end(){
        Log.d("kodulf","所有的都结束了");
    }

}
