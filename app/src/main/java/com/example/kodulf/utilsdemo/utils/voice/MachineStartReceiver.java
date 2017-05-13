package com.example.kodulf.utilsdemo.utils.voice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MachineStartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        String action = intent.getAction();

        if(Intent.ACTION_BOOT_COMPLETED.equals(action)){

            //开机就启动这个服务
            Intent serviceIntent = new Intent(context,WakeupService.class);
            context.startService(serviceIntent);
        }

    }
}
