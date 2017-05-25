package com.example.kodulf.utilsdemo.activity;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kodulf.utilsdemo.R;
import com.example.kodulf.utilsdemo.entity.City;
import com.example.kodulf.utilsdemo.utils.Log;
import com.example.kodulf.utilsdemo.utils.http.ResultList;
import com.example.kodulf.utilsdemo.utils.http.okhttp.OkHttpResponseCallback;
import com.example.kodulf.utilsdemo.utils.http.services.ServiceContext;
import com.example.kodulf.utilsdemo.utils.voice.JsonParser;
import com.example.kodulf.utilsdemo.utils.voice.WakeupService;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.VoiceWakeuper;
import com.iflytek.cloud.WakeuperListener;
import com.iflytek.cloud.WakeuperResult;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.util.ResourceUtil;

import java.util.List;

import okhttp3.Call;

/**
 * 这个是一个一个测试的，
 * 其实只是为了测试
 * 最后只要一个WakeupService就够了
 */
public class VoiceActivity extends BaseActivity {
    private TextView mTextView;
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        //默认开启这个监听的service,已经在application 里面打开了
        //startWakeUpService();
    }


    @Override
    protected void onPause() {
        super.onPause();

        if(mIat!=null)
            mIat.stopListening();
    }

    @Override
    protected void findView() {
        mTextView = ((TextView) findViewById(R.id.details));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setOnClick() {

    }




    private SpeechRecognizer mIat;
    private String TAG = "kodulf";
    private Context context = this;
    //todo 注意了，这里的RecognizerListener 是cloud 里面的
    //听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener(){
        //听写结果回调接口(返回Json格式结果，用户可参见 附录13.1);
        // 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加;
        // 关于解析Json的代码可参见MscDemo中JsonParser类;
        //isLast等于true时会话结束。
        public void onResult(RecognizerResult results, boolean isLast) {

            String resultString = results.getResultString();
            String s = JsonParser.parseIatResult(resultString);
            Log.d("kodulf","onResult "+s);
        }
        //会话发生错误回调接口
        public void onError(SpeechError error) {
            Log.d("kodulf","onError");
            //打印错误码描述
            Log.d(TAG, "error:" + error.getPlainDescription(true)); } //开始录音
        public void onBeginOfSpeech() {
            Log.d("kodulf","onBeginOfSpeech");
        } //volume音量值0~30，data音频数据
        public void onVolumeChanged(int volume, byte[] data){
            Log.d("kodulf","onVolumeChanged");
        } //结束录音
        public void onEndOfSpeech() {
            Log.d("kodulf","end of Speech");
        }
        //扩展用接口
        public void onEvent(int eventType,int arg1,int arg2, Bundle obj) {}
    };

    private SynthesizerListener mSynListener = new SynthesizerListener(){
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {}
        //缓冲进度回调
        // percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在
        //文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {}
        //开始播放
        public void onSpeakBegin() {}
        //暂停播放
        public void onSpeakPaused() {}
        //播放进度回调
        // percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {} //恢复播放回调接口
        public void onSpeakResumed() {}
        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {}
    };

    public void listen(View view) {
        //1.创建SpeechRecognizer对象，第二个参数:本地识别时传InitListener
        mIat = SpeechRecognizer.createRecognizer(this, new InitListener() {
            @Override
            public void onInit(int i) {

            }
        });
        Log.d("kodulf",""+mIat);
        //2.设置听写参数，详见《MSC Reference Manual》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");

        //3.开始听写
        mIat.startListening(mRecoListener);

    }

    public void stop(View view){
        if(mIat!=null)
            mIat.stopListening();
    }

    public void speak(View view){
        //1.创建 SpeechSynthesizer 对象，第二个参数:本地合成时传 InitListener
        SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(context, null);
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        // 设置发音人(更多在线发音人，开发者可参见 附录13.2 )
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        // 设置合成音频保存位置(可自定义保存位置)，保存在“./sdcard/iflytek.pcm”
//保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
// 仅支持保存为 pcm 和 wav 格式，如果不需要保存合成音频，注释该行代码
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm"); //3.开始合成
        mTts.startSpeaking("科大讯飞，让世界聆听我们的声音", mSynListener);
//合成监听器。

    }

    public void wakeup(View view){
        //1.创建唤醒对象
        VoiceWakeuper mIvw = VoiceWakeuper.createWakeuper(context, null);
        //2.获取唤醒词路径，resPath为唤醒资源路径
        String resPath = ResourceUtil.generateResourcePath(this, ResourceUtil.RESOURCE_TYPE.assets, "ivw/" + getString(R.string.app_id) + ".jet");
        //3.设置唤醒参数，详见《MSC Reference Manual》SpeechConstant类
        //设置唤醒词加载路径
        mIvw.setParameter(SpeechConstant.IVW_RES_PATH, resPath);
        //唤醒门限值，根据资源携带的唤醒词个数按照“id:门限;id:门限”的格式传入，
        // todo 门限值越低越容易被唤醒，默认的就是10
        mIvw.setParameter(SpeechConstant.IVW_THRESHOLD,"0:"+10);
        //设置当前业务类型为唤醒
        mIvw.setParameter(SpeechConstant.IVW_SST,"wakeup");
        //设置唤醒一直保持，直到调用stopListening，传入0则完成一次唤醒后，会话立即结束(默认0)
        mIvw.setParameter(SpeechConstant.KEEP_ALIVE,"1");
        //4.开始唤醒
        mIvw.startListening(mWakeuperListener);
        //唤醒监听器

    }
    //唤醒监听器
    private WakeuperListener mWakeuperListener = new WakeuperListener() {
        public void onResult(WakeuperResult result) {
            //唤醒结果回调接口(返回Json格式结果，开发者可参见 附件13.4)
            String text = result.getResultString();
            Log.d("kodulf","onResult"+text);
        }
        public void onError(SpeechError error) {
            Log.d("kodulf","onError");
        }
        public void onBeginOfSpeech() {
            Log.d("kodulf","onBeginOfSpeech");
        }
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            Log.d("kodulf","onEvent");
            if (SpeechEvent.EVENT_IVW_RESULT == eventType) {
                Log.d("kodulf","onEvent SpeechEvent.EVENT_IVW_RESULT");
                //当使用唤醒+识别功能时获取识别结果
                // arg1:是否最后一个结果，1:是，0:否。
                RecognizerResult reslut =
                        ((RecognizerResult)obj.get(SpeechEvent.KEY_EVENT_IVW_RESULT));
                Log.d("kodulf","onEvent reslut"+reslut.getResultString());
            }}

        @Override
        public void onVolumeChanged(int i) {

        }
    };


    /**
     * 启动wakeup的服务
     * @param view
     */
    public void startWakeUpService(View view) {
        startWakeUpService();
    }

    private void startWakeUpService() {
        //只有在当前没有在运行的时候采取启动
        //if(!AppUtils.isServiceRunning(getApplicationContext(),"com.example.kodulf.utilsdemo.utils.voice.WakeupService")) {
            Log.d("kodulf","service 没有在运行");
            Intent intent = new Intent(getApplicationContext(), WakeupService.class);
            startService(intent);
//        }else{
//            Log.d("kodulf","service 正在在运行");
//        }
    }


    private void showDialog() {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(context, null); //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
        // 结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        //3.设置回调接口
        mDialog.setListener(mRecognizerDialogListener);
        // 4.显示dialog，接收语音输入
        mDialog.show();
    }

    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {

        }

        @Override
        public void onError(SpeechError speechError) {

        }
    };

    public void stopWakeUpService(View view) {
        WakeupService.switchOnOrOff = false;
    }

    public void jumpToThirdActivity(View view) {
        Intent intent = new Intent(this,ThirdActivity.class);
        startActivity(intent);
    }

    public void startOkHttp(View view) {
        ServiceContext.getoKhttpService().getList(new OkHttpResponseCallback<ResultList<City>>() {
            @Override
            public void onFailure(Call call, Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(VoiceActivity.this,"fail",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final ResultList<City> cityResultList) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(VoiceActivity.this,"success",Toast.LENGTH_SHORT).show();
                        List<City> result = cityResultList.getResult();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < result.size(); i++) {
                            City city = result.get(i);
                            sb.append( city.getCity()+" "+city.getFlows());
                            sb.append("\n");
                        }
                        if(mTextView!=null)
                        mTextView.setText(sb.toString()+"");
                    }
                });

            }
        });
    }

    /**
     * 发送一个支持wear的notification
     * @param view
     */
    public void sendNotificationSupportWear(View view) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Start")
                .setContentText("Resume")
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE);//设置震动

//                    Intent voiceReply = new Intent();
//                    intent.setAction("android.intent.action.CALL");
//                    intent.setData(Uri.parse("tel://17761838076"));

        //单独的给可穿戴的启动ａｃｔｉｖｉｔｙ　，然后再在DetailActivity里面接收数据
        Intent voiceReply =new Intent(this,DetailActivity.class);

        //语音回复的输入
        String replyLabel = "语音回复";
        RemoteInput remoteInput = new RemoteInput.Builder(VoiceActivity.EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .build();

        PendingIntent pendingIntent = PendingIntent.getActivity(this,1998,voiceReply,PendingIntent.FLAG_UPDATE_CURRENT) ;
        NotificationCompat.Action notificationAction =
                new NotificationCompat.Action.Builder(R.mipmap.ic_launcher,"语音回复",pendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();


//                    builder.addAction(R.mipmap.ic_launcher,"action",pendingIntent);
        builder.setContentIntent(pendingIntent);

        //设置上面的notificatoinAction 只在手表端显示
        builder.extend(new NotificationCompat.WearableExtender().addAction(notificationAction));

        Notification n = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(998,n);

    }
}
