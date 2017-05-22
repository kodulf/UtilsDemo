package com.example.kodulf.utilsdemo.third;

import com.example.kodulf.utilsdemo.utils.Log;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Created by Kodulf on 2017/5/20.
 */

public class BaseTestCase extends TestCase {

    @BeforeClass
    public static void prepare(){
        Log.d("kodulf","静态方法的准备，针对所有的");
    }

    @Before
    public void init(){
        Log.d("kodulf","初始化相关的");
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
