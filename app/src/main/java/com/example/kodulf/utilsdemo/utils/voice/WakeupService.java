package com.example.kodulf.utilsdemo.utils.voice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.kodulf.utilsdemo.R;
import com.example.kodulf.utilsdemo.activity.VoiceActivity;
import com.example.kodulf.utilsdemo.utils.AppUtils;
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
import com.iflytek.cloud.util.ResourceUtil;

/**
 *  更新的步骤
 *  1： 首先在官网重新创建一个新的应用，
 *  然后添加，开放语义，语音听写，在线语音合成，语音唤醒的服务
 *  http://www.xfyun.cn/index.php/mycloud/app/appManage
 *  2：http://www.xfyun.cn/index.php/sdk/dispatcher，
 *  在sdk 下载的界面，选择组合下载，选择语音听写，在线语音合成，在线命令词识别，语音唤醒
 *  平台选择android
 *  应用选择第一步创建的应用名
 *  点击"下载sdk"
 *  3： 这个时候会提示为购买，点击购买
 *  4：输入新的唤醒词语，例如"你好"
 *  5：点击创建，创建好了以后，点击下载体验包就可以了
 *  //下面是继承的步骤
 *  6：在体验包里面，找到res／ivw 的目录，里面会有一个jet 结尾的文件，更新到assets／ivw 目录下面，删除掉原有的jet 文件，这个文件是唤醒词用到的
 *  7：在体验包里面，找到libs的目录，将里面的Msc.jar Sunflower.jar 文件拷贝到androi的libs下面，覆盖原有的
 *  8：在体验包里面，找到libs的目录，将里面的文件夹都拷贝到android 的jniLibs下面，覆盖原有的
 *  9: 在体验包里面，找到assets／iflyteck的目录，将里面的东西拷贝到android额assets／iflyteck 下面，覆盖原有的
 *  9：找到新的appid，这个会在，http://www.xfyun.cn/index.php/mycloud/app/appManage 里面找到，其实jet文件的前面的就是这个appid
 *  10：使用上面的，，更新MyApplication 里面的appid
 *  11: 将res/values/strings里面的appid 也修改为最新的
 *
 */


public class WakeupService extends Service {

    /**
     * 设置是否是启动
     */
    public static boolean switchOnOrOff = true;

    private VoiceWakeuper mIvw;
    private Handler mHandler;
    private SpeechRecognizer mIat;

    public WakeupService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        int ret;
        //设置是否是启动

        if(switchOnOrOff){
            wakeup();
            ret = START_STICKY;
        }else{
            if(mIvw!=null){
                mIvw.stopListening();
            }
            if(mIat!=null){
                mIat.stopListening();
            }
            ret = START_NOT_STICKY;
        }
        return ret;
    }


    /**
     * 唤醒的命令接受
     */
    public void wakeup(){

        String appProcessName = AppUtils.getAppProcessName(getApplicationContext());
        Log.d("kodulf","appProcessName="+appProcessName);
        if(AppUtils.isAppRunning(this,appProcessName)){
            //do nothing
            Log.d("kodulf","not running");
        }else{
            Intent intent = new Intent(this,VoiceActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        if(mIvw==null) {
            mHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    wakeup();
                    return false;
                }
            });
            //1.创建唤醒对象
            //VoiceWakeuper wakeuper = VoiceWakeuper.getWakeuper();
            mIvw = VoiceWakeuper.createWakeuper(this, null);
            //2.获取唤醒词路径，resPath为唤醒资源路径
            String resPath = ResourceUtil.generateResourcePath(this, ResourceUtil.RESOURCE_TYPE.assets, "ivw/" + getString(R.string.app_id) + ".jet");
            //3.设置唤醒参数，详见《MSC Reference Manual》SpeechConstant类
            //设置唤醒词加载路径
            mIvw.setParameter(SpeechConstant.IVW_RES_PATH, resPath);
            //唤醒门限值，根据资源携带的唤醒词个数按照“id:门限;id:门限”的格式传入，
            // todo 门限值越低越容易被唤醒，默认的就是10
            mIvw.setParameter(SpeechConstant.IVW_THRESHOLD, "0:" + 10);
            //设置当前业务类型为唤醒
            mIvw.setParameter(SpeechConstant.IVW_SST, "wakeup");
            //设置唤醒一直保持，直到调用stopListening，传入0则完成一次唤醒后，会话立即结束(默认0)
            mIvw.setParameter(SpeechConstant.KEEP_ALIVE, "1");
            //4.开始唤醒
            mIvw.startListening(mWakeuperListener);
            //唤醒监听器
        }else{
            if(!mIvw.isListening()){
                mIvw.startListening(mWakeuperListener);
            }
        }
        if(mHandler!=null){
            mHandler.sendEmptyMessageDelayed(1,5000);
        }

    }

    /**
     * 唤醒时候的回调
     */
    private WakeuperListener mWakeuperListener = new WakeuperListener() {
        public void onResult(WakeuperResult result) {
            //唤醒结果回调接口(返回Json格式结果，开发者可参见 附件13.4)
            String text = result.getResultString();
            Log.d("kodulf","onResult"+text);
            String s = JsonParser.parseIatResult(text);
            Log.d("kodulf",""+s);
            //上面的已经获取到命令了

            //现自己停止，然后再listener
            mIvw.stopListening();
            speak("您请说");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listen();
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
     * speak说话
     *
     */

    public synchronized void speak(String string){
        //1.创建 SpeechSynthesizer 对象，第二个参数:本地合成时传 InitListener
        SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(this, null);
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
        mTts.startSpeaking(string, mSynListener);
//合成监听器。

    }

    /**
     * speak的时候用到的listener的回调
     */
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


    /**
     * 听的部分的
     */

    public void listen() {
        //1.创建SpeechRecognizer对象，第二个参数:本地识别时传InitListener
        mIat = SpeechRecognizer.createRecognizer(this, null);
        Log.d("kodulf",""+mIat);
        //2.设置听写参数，详见《MSC Reference Manual》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");

        //3.开始听写
        mIat.startListening(mRecoListener);

    }

    //todo 注意了，这里的RecognizerListener 是cloud 里面的
    /**
     * 听的监听器
     */
    private RecognizerListener mRecoListener = new RecognizerListener(){
        //听写结果回调接口(返回Json格式结果，用户可参见 附录13.1);
        // 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加;
        // 关于解析Json的代码可参见MscDemo中JsonParser类;
        //isLast等于true时会话结束。
        public void onResult(RecognizerResult results, boolean isLast) {
            String resultString = results.getResultString();

            String s = JsonParser.parseIatResult(resultString);
            //if("打开我的简历".equals(s)){
            if(s.contains("简历")){
                mIat.stopListening();
                speak("好的，现在打开您的简历");
                if(!AppUtils.lightScreen(getApplicationContext()))
                    speak("解锁您的手机以继续");
                //打开我的简历
                try {
                    AppUtils.openApp(getApplicationContext(),"tech.androidstudio.myresume");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.d("kodulf","监听到"+s);


        }
        //会话发生错误回调接口
        public void onError(SpeechError error) {
            Log.d("kodulf","onError");
            //打印错误码描述
            Log.d("kodulf", "error:" + error.getPlainDescription(true)); } //开始录音
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

}
