package com.example.kodulf.utilsdemo.utils;

import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import com.example.kodulf.utilsdemo.entity.LitepalTableEntityExample;

import java.util.Date;

/**
 * Created by Kodulf on 2017/5/1.
 */

public class DatabaseTransactionTest {
    static int num = 0;

    public static void test(){
        DatabaseTransaction.doTransaction(new SQLiteTransactionListener() {
            @Override
            public void onBegin() {
                Log.d("kodulf", "tranaction onBegin");
            }

            @Override
            public void onCommit() {
                Log.d("kodulf", "tranaction onCommit");
            }

            @Override
            public void onRollback() {
                Log.d("kodulf", "tranaction on Roll back");
            }
        }, new DatabaseTransaction.DatabaseExecutor() {
            boolean ret = false;
            @Override
            public boolean needRollback() {
                return ret;
            }

            @Override
            public void doInTransaction() {
                Date date = new Date();
                String s = date.toString();
                LitepalTableEntityExample album = new LitepalTableEntityExample();
                album.setName(s);
                album.setPrice(num++);
                album.save();

                //可以手动的设置是否执行失败
                if(num%3==0){
                    ret = true;
                }
            }
        });
    }
}
