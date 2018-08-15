package com.zhizhong.yujian;

import com.github.androidtools.MD5;

import org.junit.Test;

import java.util.Calendar;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void asdf() {
        String a="231,";
        String[] split = a.split(",");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]+"==");
        }

    }
    protected String getRnd() {
        Random random = new Random();
        int rnd = random.nextInt(9000) + 1000;
        return rnd + "";
    }
    @Test
    public void sdf() {
        //29689_
        String key="497d65e93e7076ea90246e96711048f0";
        String bizId="29689";
        //直播码=bizid+"_"+随机数
        String zhiBoCode=bizId+"_"+getRnd();
        System.out.println("直播码:"+zhiBoCode);
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY)+24);
        int shijiancuo=(int) (calendar.getTime().getTime()/1000);
        System.out.println("时间戳:"+zhiBoCode);
        //16进制时间戳(秒)
        String time = Integer.toHexString(shijiancuo);

        String str=key+zhiBoCode+time;

        //md5加密
        String packageSign = MD5.getMessageDigest(str.toString().getBytes());

        String pushStr="rtmp://"+bizId+".livepush.myqcloud.com/live/"+zhiBoCode+"?bizid="+bizId+"&txSecret="+packageSign+"&txTime="+time;
        String address="http://"+bizId+".liveplay.myqcloud.com/live/"+zhiBoCode+".flv";
        System.out.println("推流："+pushStr);
        System.out.println("地址："+address);


    }


    @Test
    public void sdasdff() {
        String key="a460154082ac27821bdf60c62132a641";
        int time=1534228508;//132
        String  timeStr = Integer.toHexString(1534228508);
        System.out.println(timeStr);
        String str=key+time;
        System.out.println(MD5.getMessageDigest(str.toString().getBytes()));
    }
    /*1534228508132
5b72781c
推流：rtmp://29689.livepush.myqcloud.com/live/29689_7594?bizid=29689&txSecret=3226fc56b8fbc944de3409a195176175&txTime=5b72781c
地址：http://29689.liveplay.myqcloud.com/live/29689_7594.flv*/
}