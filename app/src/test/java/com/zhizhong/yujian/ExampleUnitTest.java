package com.zhizhong.yujian;

import org.junit.Test;

import static org.junit.Assert.*;

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
}