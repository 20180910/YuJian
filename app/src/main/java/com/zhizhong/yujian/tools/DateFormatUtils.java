package com.zhizhong.yujian.tools;

/**
 * Created by administartor on 2017/9/27.
 */

public class DateFormatUtils {
    public static String getXCTime(long startTime, long endTime) {
            //毫秒ms
            long diff = endTime - startTime;
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            /*System.out.print("两个时间相差：");
            System.out.print(diffDays + " 天, ");
            System.out.print(diffHours + " 小时, ");
            System.out.print(diffMinutes + " 分钟, ");
            System.out.print(diffSeconds + " 秒.");*/
            if(diffDays>0){
                return diffDays+"天"+diffHours + "时"+diffMinutes+"分"+diffSeconds + "秒";
            }
        return diffHours + "时"+diffMinutes+"分"+diffSeconds + "秒";
    }
    public static String getXCTime(long startTime, long endTime, boolean flag) {
            //毫秒ms
            long diff = endTime - startTime;
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours  = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            /*System.out.print("两个时间相差：");
            System.out.print(diffDays + " 天, ");
            System.out.print(diffHours + " 小时, ");
            System.out.print(diffMinutes + " 分钟, ");
            System.out.print(diffSeconds + " 秒.");*/
            String diffSecondsStr=diffSeconds+"";
            String diffMinutesStr=diffMinutes+"";
            String diffHoursStr=diffHours+"";
            if(diffSeconds<10){
                diffSecondsStr="0"+diffSecondsStr;
            }
            if(diffMinutes<10){
                diffMinutesStr="0"+diffMinutesStr;
            }
            if(diffHours<10){
                diffHoursStr="0"+diffHoursStr;
            }
            if(flag){
                if(diffDays>0){
                    return diffDays+"天"+diffHoursStr + ":"+diffMinutesStr+":"+diffSecondsStr + "";
                }
                return diffHoursStr + ":"+diffMinutesStr+":"+diffSecondsStr + "";
            }else{
                if(diffDays>0){
                    return diffDays+"天"+diffHours + "时"+diffMinutes+"分"+diffSeconds + "秒";
                }
                return diffHours + "时"+diffMinutes+"分"+diffSeconds + "秒";
            }
    }
}
