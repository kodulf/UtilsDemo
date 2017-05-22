package com.example.kodulf.utilsdemo.utils.autosync;

import com.example.kodulf.utilsdemo.utils.Log;
import com.example.kodulf.utilsdemo.utils.ThreadPoolUtils;
import com.example.kodulf.utilsdemo.utils.schedule.engine.Schedule;

/**
 * Created by Kodulf on 2017/5/18.
 */

public class OrderSchedule implements Schedule {
    @Override
    public void syncSchedule() {
        sync();
    }

    /**
     * 注意这里的同步，如果是使用了while 来进行循环的执行的话，不要放在线程池里面执行的
     * 如果是只需要按照engine的要求30分钟执行一次，那么就放到线程池里面，执行完了就释放了
     */
    public void sync(){
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.d("kodulf", "开始order 的同步");
                }
            }
        });
    }
}
