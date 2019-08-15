package com.linziyi.user.util;

import java.util.Random;

/**
 * 生成主键
 * 格式：时间戳 + 随机数
 */
public class KeyUtil {
    //synchronized 防止多线程的时候生成同样的数据
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
