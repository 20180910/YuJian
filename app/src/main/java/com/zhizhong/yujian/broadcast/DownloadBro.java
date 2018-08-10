package com.zhizhong.yujian.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2017/12/23.
 */

public class DownloadBro extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction(); //动作
        Log.i("===","action==="+action);
        if (action.equals("download")) {
            Log.i("===","action===download");
            String path = intent.getStringExtra("xiangyupath");
            if(intent.getBooleanExtra("tag",false)){
                openDoc(context,path);
            }else{
                installApp(context,new File(path));
            }
        }
    }

    public static void openDoc(Context context,String path) {
        String str="";
        String fileNameHouZhui=path.substring(path.lastIndexOf("."), path.length());
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if(fileNameHouZhui.equalsIgnoreCase(MIME_MapTable[i][0])){
                str=MIME_MapTable[i][1];
                break;
            }
        }
        if(TextUtils.isEmpty(str)){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(path)), str);
        context.startActivity(intent);
    }

    public void installApp(Context context, File file) {
        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);*/


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(context, "com.zhizhong.yujian.fileprovider", file);
            // 给目标应用一个临时授权

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    private static final String[][] MIME_MapTable = {
//{后缀名，MIME类型}
            {".3gp", "video/3gpp"},

            {".apk", "application/vnd.android.package-archive"},

            {".asf", "video/x-ms-asf"},

            {".avi", "video/x-msvideo"},

            {".bin", "application/octet-stream"},

            {".bmp", "image/bmp"},

            {".c", "text/plain"},

            {".class", "application/octet-stream"},

            {".conf", "text/plain"},

            {".cpp", "text/plain"},

            {".doc", "application/msword"},

            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},

            {".xls", "application/vnd.ms-excel"},

            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},

            {".exe", "application/octet-stream"},

            {".gif", "image/gif"},

            {".gtar", "application/x-gtar"},

            {".gz", "application/x-gzip"},

            {".h", "text/plain"},

            {".htm", "text/html"},

            {".html", "text/html"},

            {".jar", "application/java-archive"},

            {".java", "text/plain"},

            {".jpeg", "image/jpeg"},

            {".jpg", "image/jpeg"},

            {".js", "application/x-javascript"},

            {".log", "text/plain"},

            {".m3u", "audio/x-mpegurl"},

            {".m4a", "audio/mp4a-latm"},

            {".m4b", "audio/mp4a-latm"},

            {".m4p", "audio/mp4a-latm"},

            {".m4u", "video/vnd.mpegurl"},

            {".m4v", "video/x-m4v"},

            {".mov", "video/quicktime"},

            {".mp2", "audio/x-mpeg"},

            {".mp3", "audio/x-mpeg"},

            {".mp4", "video/mp4"},

            {".mpc", "application/vnd.mpohun.certificate"},

            {".mpe", "video/mpeg"},

            {".mpeg", "video/mpeg"},

            {".mpg", "video/mpeg"},

            {".mpg4", "video/mp4"},

            {".mpga", "audio/mpeg"},

            {".msg", "application/vnd.ms-outlook"},

            {".ogg", "audio/ogg"},

            {".pdf", "application/pdf"},

            {".png", "image/png"},

            {".pps", "application/vnd.ms-powerpoint"},

            {".ppt", "application/vnd.ms-powerpoint"},

            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},

            {".prop", "text/plain"},

            {".rc", "text/plain"},

            {".rmvb", "audio/x-pn-realaudio"},

            {".rtf", "application/rtf"},

            {".sh", "text/plain"},

            {".tar", "application/x-tar"},

            {".tgz", "application/x-compressed"},

            {".txt", "text/plain"},

            {".wav", "audio/x-wav"},

            {".wma", "audio/x-ms-wma"},

            {".wmv", "audio/x-ms-wmv"},

            {".wps", "application/vnd.ms-works"},

            {".xml", "text/plain"},

            {".z", "application/x-compress"},

            {".zip", "application/x-zip-compressed"},

            {"", "*/*"}

    };
}
