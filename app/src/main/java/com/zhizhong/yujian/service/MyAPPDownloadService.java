package com.zhizhong.yujian.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.aspsine.multithreaddownload.CallBack;
import com.aspsine.multithreaddownload.DownloadException;
import com.aspsine.multithreaddownload.DownloadManager;
import com.aspsine.multithreaddownload.DownloadRequest;
import com.aspsine.multithreaddownload.util.L;
import com.github.androidtools.ToastUtils;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.bean.AppInfo;
import com.zhizhong.yujian.tools.FileUtils;

import java.io.File;


/**
 * Created by aspsine on 15/7/28.
 */
public class MyAPPDownloadService extends Service {
    public static final String downloadName = "xiangyu";
    public static final String DOWNLOAD_DIR = downloadName+"download";

    public static final File getDownloadDir() {
        File file = new File(Environment.getExternalStorageDirectory(), DOWNLOAD_DIR);
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    private static final String tag_InitDownload ="下载初始化";
    private static final String tag_StartDownload ="开始下载:";
    private static final String tag_Connecting ="连接中";
    private static final String tag_Downloading ="下载中";
    private static final String tag_DownloadComplete ="下载完成";
    private static final String tag_DownloadPaused ="下载暂停";
    private static final String tag_DownloadCanceled ="下载取消";
    private static final String tag_DownloadFailed ="下载失败";

    private static final String TAG = MyAPPDownloadService.class.getSimpleName();

    public static final String ACTION_DOWNLOAD_BROAD_CAST = "com.aspsine.multithreaddownload.demo:action_download_broad_cast";

    public static final String ACTION_DOWNLOAD = "com.aspsine.multithreaddownload.demo:action_download";

    public static final String ACTION_PAUSE = "com.aspsine.multithreaddownload.demo:action_pause";

    public static final String ACTION_CANCEL = "com.aspsine.multithreaddownload.demo:action_cancel";

    public static final String ACTION_PAUSE_ALL = "com.aspsine.multithreaddownload.demo:action_pause_all";

    public static final String ACTION_CANCEL_ALL = "com.aspsine.multithreaddownload.demo:action_cancel_all";

    public static final String EXTRA_POSITION = "extra_position";

    public static final String EXTRA_TAG = "extra_tag";

    public static final String EXTRA_APP_INFO = "extra_app_info";
    public static Context mContext;
    /**
     * Dir: /Download
     */
//    private File mDownloadDir;

    private DownloadManager mDownloadManager;

    private NotificationManagerCompat mNotificationManager;

    public static void intentDownload(Context context, AppInfo info) {
        Intent intent = new Intent(context, MyAPPDownloadService.class);
        intent.setAction(ACTION_DOWNLOAD);
//        intent.putExtra(EXTRA_POSITION, position);
        intent.putExtra(EXTRA_TAG, info.getId());
        intent.putExtra(EXTRA_APP_INFO, info);
        context.startService(intent);
    }

    public static void intentPause(Context context, String tag) {
        Intent intent = new Intent(context, MyAPPDownloadService.class);
        intent.setAction(ACTION_PAUSE);
        intent.putExtra(EXTRA_TAG, tag);
        context.startService(intent);
    }

    public static void intentPauseAll(Context context) {
        Intent intent = new Intent(context, MyAPPDownloadService.class);
        context.startService(intent);
    }

    public static void destory(Context context) {
        Intent intent = new Intent(context, MyAPPDownloadService.class);
        context.stopService(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext=getApplicationContext();
        if (intent != null) {
            String action = intent.getAction();
            int position = intent.getIntExtra(EXTRA_POSITION, 0);
            AppInfo appInfo = (AppInfo) intent.getSerializableExtra(EXTRA_APP_INFO);
            String tag = intent.getStringExtra(EXTRA_TAG);
            switch (action) {
                case ACTION_DOWNLOAD:
                    download(appInfo);
                    break;
                case ACTION_PAUSE:
                    pause(tag);
                    break;
                case ACTION_CANCEL:
                    cancel(tag);
                    break;
                case ACTION_PAUSE_ALL:
                    pauseAll();
                    break;
                case ACTION_CANCEL_ALL:
                    cancelAll();
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void download(final AppInfo appInfo) {
        ToastUtils.showToast(mContext,"正在下载中...");
        appInfo.setTitle(getResources().getString(R.string.app_name));
        if(appInfo.getHouZhui().indexOf(".")==-1){
            appInfo.setHouZhui("."+appInfo.getHouZhui());
        }
        File dirPath=getDownloadDir();
        final DownloadRequest request = new DownloadRequest.Builder()
                .setName(appInfo.getFileName()+appInfo.getHouZhui())
                .setUri(appInfo.getUrl())
                .setFolder(dirPath)
                .build();
        mDownloadManager.download(request,appInfo.getId(), new DownloadCallBack( appInfo, mNotificationManager, getApplicationContext()));
    }

    private void pause(String tag) {
        mDownloadManager.pause(tag);
    }

    private void cancel(String tag) {
        mDownloadManager.cancel(tag);
    }

    private void pauseAll() {
        mDownloadManager.pauseAll();
    }

    private void cancelAll() {
        mDownloadManager.cancelAll();
    }

    public static class DownloadCallBack implements CallBack {

        private int mPosition;

        private AppInfo mAppInfo;

        private LocalBroadcastManager mLocalBroadcastManager;

        private NotificationCompat.Builder mBuilder;

        private NotificationManagerCompat mNotificationManager;

        private long mLastTime;

        public DownloadCallBack( AppInfo appInfo, NotificationManagerCompat notificationManager, Context context) {
            mPosition = 1;
            mAppInfo = appInfo;
            mNotificationManager = notificationManager;
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(context);
            mBuilder = new NotificationCompat.Builder(context);
        }

        @Override
        public void onStarted() {
            L.i(TAG, "onStart()");
            mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(mAppInfo.getTitle())
                    .setContentText(mAppInfo.getTitle()+tag_InitDownload)
                    .setProgress(100, 0, true)
                    .setTicker(tag_StartDownload + mAppInfo.getTitle());
            updateNotification();
        }

        @Override
        public void onConnecting() {
            L.i(TAG, "onConnecting()");
            mBuilder.setContentText(mAppInfo.getTitle()+tag_Connecting)
                    .setProgress(100, 0, true);
            updateNotification();

            mAppInfo.setStatus(AppInfo.STATUS_CONNECTING);
            sendBroadCast(mAppInfo);
        }

        @Override
        public void onConnected(long total, boolean isRangeSupport) {
            L.i(TAG, "onConnected()");
            mBuilder.setContentText(mAppInfo.getTitle()+tag_Connecting)
                    .setProgress(100, 0, true);
            updateNotification();
        }

        @Override
        public void onProgress(long finished, long total, int progress) {

            if (mLastTime == 0) {
                mLastTime = System.currentTimeMillis();
                Log.i(TAG+"===","total==="+total);
                mAppInfo.setFileSize(total+"");
            }

            mAppInfo.setStatus(AppInfo.STATUS_DOWNLOADING);
            mAppInfo.setProgress(progress);
            mAppInfo.setDownloadPerSize(FileUtils.getDownloadPerSize(finished, total));

            long currentTime = System.currentTimeMillis();
            if (currentTime - mLastTime > 500) {
                L.i(TAG, "onProgress()");
                mBuilder.setContentText(mAppInfo.getTitle()+tag_Downloading);
                mBuilder.setProgress(100, progress, false);
                updateNotification();

                sendBroadCast(mAppInfo);

                mLastTime = currentTime;
            }
        }
        @Override
        public void onCompleted() {
            L.i(TAG, "onCompleted()");
            Intent intent = new Intent("download");

            intent.putExtra("xiangyupath",getDownloadDir()+"/"+mAppInfo.getFileName()+mAppInfo.getHouZhui());
            if(mAppInfo.isNoApk()){
                intent.putExtra("tag",true);
            }
            mBuilder.setContentText(mAppInfo.getTitle()+tag_DownloadComplete);
            mBuilder.setProgress(0, 0, false);
            mBuilder.setTicker(mAppInfo.getTitle() + tag_DownloadComplete)
                    .setAutoCancel(true)
                    .setContentIntent(PendingIntent.getBroadcast(mContext,2,intent,PendingIntent.FLAG_UPDATE_CURRENT));
            updateNotification();

            mAppInfo.setStatus(AppInfo.STATUS_COMPLETE);
            mAppInfo.setProgress(100);
            mContext.sendBroadcast(intent);
            sendBroadCast(mAppInfo);
        }

        @Override
        public void onDownloadPaused() {
            L.i(TAG, "onDownloadPaused()");
            mBuilder.setContentText(mAppInfo.getTitle()+tag_DownloadPaused);
            mBuilder.setTicker(mAppInfo.getTitle() + tag_DownloadPaused);
            mBuilder.setProgress(100, mAppInfo.getProgress(), false);
            updateNotification();

            mAppInfo.setStatus(AppInfo.STATUS_PAUSED);
            sendBroadCast(mAppInfo);
        }

        @Override
        public void onDownloadCanceled() {
            L.i(TAG, "onDownloadCanceled()");
            mBuilder.setContentText(mAppInfo.getTitle()+tag_DownloadCanceled);
            mBuilder.setTicker(mAppInfo.getTitle() + tag_DownloadCanceled);
            updateNotification();

            //there is 1000 ms memory leak, shouldn't be a problem
            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mNotificationManager.cancel(mPosition + 1000);
                }
            }, 1000);*/

            mAppInfo.setStatus(AppInfo.STATUS_NOT_DOWNLOAD);
            mAppInfo.setProgress(0);
            mAppInfo.setDownloadPerSize("");
            sendBroadCast(mAppInfo);
        }

        @Override
        public void onFailed(DownloadException e) {
            L.i(TAG, "onFailed()");
            e.printStackTrace();
            mBuilder.setContentText(mAppInfo.getTitle()+tag_DownloadFailed);
            mBuilder.setTicker(mAppInfo.getTitle() + tag_DownloadFailed);
            mBuilder.setProgress(100, mAppInfo.getProgress(), false);
            updateNotification();

            mAppInfo.setStatus(AppInfo.STATUS_DOWNLOAD_ERROR);
            sendBroadCast(mAppInfo);
        }

        private void updateNotification() {
//            mPosition++;
            mNotificationManager.notify(mPosition + 1000, mBuilder.build());
        }

        private void sendBroadCast(AppInfo appInfo) {
            Intent intent = new Intent();
            intent.setAction(MyAPPDownloadService.ACTION_DOWNLOAD_BROAD_CAST);
            intent.putExtra(EXTRA_POSITION, mPosition);
            intent.putExtra(EXTRA_APP_INFO, appInfo);
            mLocalBroadcastManager.sendBroadcast(intent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDownloadManager = DownloadManager.getInstance();
        mNotificationManager = NotificationManagerCompat.from(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDownloadManager.pauseAll();
    }


}
