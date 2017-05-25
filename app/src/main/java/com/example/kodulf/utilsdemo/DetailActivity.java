package com.example.kodulf.utilsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.kodulf.utilsdemo.utils.AppUtils;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent reply = getIntent();
        CharSequence voiceReply = getMessageText(reply);
        String sender = reply.getStringExtra("SENDER");
        Log.d("kodulf", "收到="+voiceReply+" sender="+sender);

        //这里的 voiceReply 是我们需要的
        String startMyResume="打开我的简历";
        String startResume ="打开简历";
        String stringVoiceReply = String.valueOf(voiceReply);

        if(startMyResume.equals(stringVoiceReply)||startResume.equals(stringVoiceReply)){
            Log.d("kodulf", "收到="+voiceReply+" sender="+sender);

            try {
                //点亮屏幕，然后打开应用
                AppUtils.lightScreen(getApplicationContext());
                AppUtils.openApp(getApplicationContext(),"tech.androidstudio.myresume");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(VoiceActivity.EXTRA_VOICE_REPLY);
        }
        return null;
    }

}
