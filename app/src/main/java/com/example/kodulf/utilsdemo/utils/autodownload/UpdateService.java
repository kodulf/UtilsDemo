package com.example.kodulf.utilsdemo.utils.autodownload;

/**
 * Created by Kodulf on 2017/5/4.
 */
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

/**
 * http://blog.csdn.net/rodulf/article/details/51706788
 * 先判断然后才能够启动更新的服务的，就是先从服务器获取到当前的版本和自己的版本
 *
 * 调用：线程中执行下面的
//启动服务
Intent service = new Intent(MainActivity.this,UpdateService.class);
startService(service);

 */

public class UpdateService extends Service {

    public UpdateService() {

    }

    /** 安卓系统下载类 **/
    DownloadManager manager;

    /** 接收下载完的广播 **/
    DownloadCompleteReceiver receiver;

    /** 初始化下载器 **/
    private void initDownManager() {

        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        receiver = new DownloadCompleteReceiver();

        //设置下载地址
        String urlPath = "http://47.93.61.22/wordpress/app-release.apk";
        //String urlPath = "http://122.228.237.132/apk.r1.market.hiapk.com/data/upload/apkres/2016/6_12/16/com.lesports.glivesports_040405.apk";
        Uri parse = Uri.parse(urlPath);
        DownloadManager.Request down = new DownloadManager.Request(parse);

//     百度音乐           Uri.parse("http://gdown.baidu.com/data/wisegame/fd84b7f6746f0b18/baiduyinyue_4802.apk"));
//    乐视体育
         //Uri.parse("http://122.228.237.132/apk.r1.market.hiapk.com/data/upload/apkres/2016/6_12/16/com.lesports.glivesports_040405.apk"));


        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

        // 下载时，通知栏显示途中
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        }

        // 显示下载界面
        down.setVisibleInDownloadsUi(true);

        // 设置下载后文件存放的位置


        String apkName = parse.getLastPathSegment();
        down.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, apkName);

        // 将下载请求放入队列
        manager.enqueue(down);

        //注册下载广播
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // 调用下载
        initDownManager();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {

        // 注销下载广播
        if (receiver != null)
            unregisterReceiver(receiver);

        super.onDestroy();
    }

    // 接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            //判断是否下载完成的广播
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {


                //获取下载的文件id
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Log.d("kodulf","id="+downId);

                //自动安装apk
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    Uri uriForDownloadedFile = manager.getUriForDownloadedFile(downId);
                    Log.d("kodulf","uri="+uriForDownloadedFile);

                    installApkNew(uriForDownloadedFile);
                }

                //停止服务并关闭广播
                UpdateService.this.stopSelf();

            }
        }

        //安装apk
        protected void installApkNew(Uri uri) {
            Log.d("kodulf","start ");
            Intent intent = new Intent();
            //执行动作
            intent.setAction(Intent.ACTION_VIEW);
            //执行的数据类型
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");

            Log.d("kodulf","start 1");
            //不加下面这句话是可以的，查考的里面说如果不加上这句的话在apk安装完成之后点击单开会崩溃
            // android.os.Process.killProcess(android.os.Process.myPid());
            try {

                Log.d("kodulf","start 2");
                startActivity(intent);

                Log.d("kodulf","start 3");
            }catch (Exception e){
                e.printStackTrace();
                Log.d("kodulf",""+e.getMessage());
            }
        }
    }
}