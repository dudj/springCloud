package com.linziyi.eureka.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang.time.DateFormatUtils.format;

@RestController
@Slf4j
/**
 * 定时任务
 */
public class CrontabController {
    FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    /**
     * 考勤数据 进行定时缓存
     */
//    @Scheduled(cron = "2 * * * * ?")
    public void attendanceTimingCache() throws Exception {
        Date todayDate = new Date();
        //获取所有的员工信息

        //获取所有需要缓存的月份

        //判断当天是否为月初
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String s = "2011-07-01";
        Date date = format.parse(s);

        boolean preDayOfMonth = isPreDayOfMonth(date);
        if(preDayOfMonth){

        }else{
            //获取当前时间戳
            long timeNow = Calendar.getInstance().getTimeInMillis();
            //获取明天五点时间
            Long nextDayTime = getNextDayTime();
            Long space = nextDayTime - timeNow;
            //只缓存当月的数据

        }
        log.info("[preDayOfMonth]：" + preDayOfMonth);
        log.info("[CronJob Execute]"+fdf.format(new Date()));
    }

    /**
     * 判断给定日期是否为月初的一天（月末+1=月初）
     * @param date
     * @return
     */
    public static boolean isPreDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        if (calendar.get(Calendar.DAY_OF_MONTH)-1 == 1) {
            return true;
        }
        return false;
    }
    /**
     * 获取时间为明天的上午5点
     * @return
     */
    public static Long getNextDayTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 1);
        //小时
        c.set(Calendar.HOUR_OF_DAY, 5);
        //分钟
        c.set(Calendar.MINUTE, 0);
        //秒
        c.set(Calendar.SECOND, 0);
        //毫秒
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }
    public static void main(String[] args) throws ParseException {
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM");
        List<String> stringList = getMonthBetweenDates("2019-01", fdf.format(new Date()));
        log.info("stringList:"+stringList.toString());
    }
    /**
     * 获取某个时间段内所有月份
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    public static List<String> getMonthBetweenDates(String minDate, String maxDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        ArrayList<String> result = new ArrayList<String>();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(format.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        max.setTime(format.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(format(curr.getTime(),"yyyy-MM"));
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }
    @GetMapping("/test")
    public void Test(){
        log.info("test:{}",new Date());
    }

}
