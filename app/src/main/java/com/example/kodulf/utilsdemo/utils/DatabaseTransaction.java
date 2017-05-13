package com.example.kodulf.utilsdemo.utils;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;

import org.litepal.LitePal;

/**
 * Created by Kodulf on 2017/5/1.
 */

public class DatabaseTransaction {


    /**
     * 开始，做事，判，成功，结束
     * @param sqLiteTransactionListener
     * @param databaseExecutor
     */
    public static synchronized void doTransaction(SQLiteTransactionListener sqLiteTransactionListener, DatabaseExecutor databaseExecutor){

        //获取database
        SQLiteDatabase database = LitePal.getDatabase();

        //开始执行事务
        database.beginTransactionWithListener(sqLiteTransactionListener);

        try {
            //do something
            //todo 千万要记住的是，最好不要在这里面执行LitePal里面的finalAll的方法的，之前出现过在事务里面执行findAll，然后在其他的地方执行数据库的操作的时候就报错的情况
            databaseExecutor.doInTransaction();

            //判断如果是否需要rollback，
            //当needRollback为false的时候，那么设置事务执行成功，只有这个条件的时候才将事务设置为成功
            //否则不设置为成功，那么会自动的rollback
            if(!databaseExecutor.needRollback())
                database.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //最终的时候关闭事务
            database.endTransaction();
        }
    }


    public interface DatabaseExecutor {
        /**
         * 可以在这里设置是否需要rollback
         * @return
         */
        boolean needRollback();
        /**
         * 需要在事务中执行的
         */
        void doInTransaction();
    }
}
