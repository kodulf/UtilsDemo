package com.example.kodulf.utilsdemo.utils.schedule;

import com.example.kodulf.utilsdemo.utils.ThreadPoolUtils;
import com.example.kodulf.utilsdemo.utils.schedule.engine.Schedule;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by drome on 16/7/21.
 */
public class ErrorSchedule implements Schedule {
    private boolean next;

    @Override
    public void syncSchedule() {
        syncError();
    }

    public void saveError(CashierError error){
        error.setErrorObject("errorTime:"+ (new Date())+","+error.getErrorObject());
        error.save();
    }

    public void syncError(){
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                final List<CashierError> list= CashierError.where("id>=0").find(CashierError.class);

//                boolean flag = true;
//                while(flag){
//                    //每次查找30个,直到全部完成
//                    final List<CashierError> list= DataSupport.limit(30).find(CashierError.class);
//                    if(list.size()<=0) flag = false;
                    next=true;
                //这个循环提交error是串行的
                    for(int i=0;i<list.size()&&next;i++) {
                        final CountDownLatch latch = new CountDownLatch(1);//线程同步辅助类
                        final int finalI = i;
//                        ServiceContext.getCashierErrorService().reportErrorNotSave(GlobalVariable.CASHIER.getShopUid(), list.get(i), new WrapCallback<Result<Object>>() {
//                            @Override
//                            public void onFailure(Call call, IOException e) {
//                                next = false;
//                                latch.countDown();
//
//                            }
//
//                            @Override
//                            public void onResponse(Call call, Result<Object> result) throws IOException {
//                                // TODO: 16/9/1 可以让失败的情况重试一定次数
//                                list.get(finalI).delete();
//                                latch.countDown();
//                            }
//                        });

                        try {
                            latch.await(3 * 60 * 1000, TimeUnit.MILLISECONDS);//3分钟超时
                        } catch (InterruptedException e) {
                            //忽略
    //                    e.printStackTrace();
                        }

                    }

                }
//            }
        });

    }
}
