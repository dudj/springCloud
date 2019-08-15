package com.linziyi.user.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author:lkk
 * @Date:2019-08-07
 * @Effort:定时任务实现时间点的缓存工具类
 */
public class ClockUtil {
    /**
     * 判断是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    private static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else if (nowTime.compareTo(beginTime) == 0 || nowTime.compareTo(endTime) == 0) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 字符串 转 日期
     * @param str
     * @return
     */
    private static Date strToDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (Exception e) {

        }
        return date;
    }
}
