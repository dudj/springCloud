package com.linziyi.order.utils;

import com.linziyi.order.dto.TimeSlotDTO;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 获取不同工种对应的不同上下班时间
 */
public class TimeSlotUtil {
    /**
     *
     * @param workType 工种
     * @param isPre 是否为节假日前一天
     * 标识为1的 区分节气
     * 上午 8:00~12:00
     * 10月1号到次年4月30号算冬季
     * 夏令 14:00~18:00 冬令14:00~17:30
     * 标识为2
     * 上午 8:00~12:00 下午 14:00~18:00
     * 标识为3
     * 上午 9:00~13:00 下午 15:00~18:30
     * 节假日前一天 所有的时间均提前半个小时
     * @return
     */
    public static Map<String,TimeSlotDTO> listDataForOne(Integer workType,Boolean isPre){
        TimeSlotDTO timeSlotUp1Normal;//上班1 正常
        TimeSlotDTO timeSlotUp1Abnormal;//上班1 异常
        TimeSlotDTO timeSlotDown1;//下班1 可能不打卡 但是没有异常情况
        TimeSlotDTO timeSlotUp2Normal;//上班2 正常
        TimeSlotDTO timeSlotUp2Abnormal;//上班2 异常
        TimeSlotDTO timeSlotDown2After;//下班2 两个时间段 18:00~00:00
        TimeSlotDTO timeSlotDown2Pre;//下班2 00:00~05:00
        TimeSlotDTO timeSlotUp1Section;//上班1 不管正常还是异常 取数据的时间段
        TimeSlotDTO timeSlotUp2Section;//上班2 不管正常还是异常 取数据的时间段
        TimeSlotDTO timeSlotDown2Section;//下班2 不管正常还是异常 取数据的时间段
        List list = new ArrayList<>();
        String closingTime = "18:00:00";
        timeSlotDown2Pre = new TimeSlotDTO("00:00:00","05:00:00","down2");
        timeSlotUp1Normal = new TimeSlotDTO("05:00:00","08:00:00","up1");
        timeSlotUp1Abnormal = new TimeSlotDTO("08:00:00","12:00:00","up1nor");
        timeSlotDown1 = new TimeSlotDTO("12:00:00","13:15:00","down1");
        timeSlotUp2Normal = new TimeSlotDTO("13:15:00","14:00:00","up2");
        Map map = new HashMap<>();
        //标识为1的 - 区分
        switch(workType){
            case 1:
                    Boolean isSection = belongCalendar(new Date(),strToDate(Calendar.getInstance().get(Calendar.YEAR)+"-04-00 00:00:00"),strToDate(Calendar.getInstance().get(Calendar.YEAR)+"-10-00 00:00:00"));
                    if(isSection){
                        if(isPre){
                            closingTime = "17:30";
                        }
                        timeSlotUp2Abnormal = new TimeSlotDTO("14:00:00",closingTime,"up2nor");
                        timeSlotDown2After = new TimeSlotDTO(closingTime,"00:00:00","down2");
                        timeSlotUp1Section = new TimeSlotDTO("05:00:00","12:00:00","up1section");
                        timeSlotUp2Section = new TimeSlotDTO("13:15:00",closingTime,"up2section");
                        timeSlotDown2Section = new TimeSlotDTO(closingTime,"05:00:00","down2section");
                    }else{
                        if(isPre){
                            closingTime = "17:00";
                        }else{
                            closingTime = "17:30";
                        }
                        timeSlotUp2Abnormal = new TimeSlotDTO("14:00:00",closingTime,"up2nor");
                        timeSlotDown2After = new TimeSlotDTO(closingTime,"00:00:00","down2");
                        timeSlotUp1Section = new TimeSlotDTO("05:00:00","12:00:00","up1section");
                        timeSlotUp2Section = new TimeSlotDTO("13:15:00",closingTime,"up2section");
                        timeSlotDown2Section = new TimeSlotDTO(closingTime,"05:00:00","down2section");
                    }
                break;
            case 2:
                    if(isPre){
                        closingTime = "17:30";
                    }
                    timeSlotUp2Abnormal = new TimeSlotDTO("14:00:00",closingTime,"up2nor");
                    timeSlotDown2After = new TimeSlotDTO(closingTime,"00:00:00","down2");
                    timeSlotUp1Section = new TimeSlotDTO("05:00:00","12:00:00","up1section");
                    timeSlotUp2Section = new TimeSlotDTO("13:15:00",closingTime,"up2section");
                    timeSlotDown2Section = new TimeSlotDTO(closingTime,"05:00:00","down2section");
                break;
            case 3:
                    timeSlotUp1Normal = new TimeSlotDTO("05:00:00","09:00:00","up1");
                    timeSlotUp1Abnormal = new TimeSlotDTO("09:00:00","13:00:00","up1nor");
                    timeSlotDown1 = new TimeSlotDTO("13:00:00","14:15:00","down1");
                    timeSlotUp2Normal = new TimeSlotDTO("14:15:00","15:00:00","up2");
                    timeSlotDown2Section = new TimeSlotDTO(closingTime,"05:00:00","down2section");
                    if(isPre){
                        closingTime = "18:00";
                    }else{
                        closingTime = "18:30";
                    }
                    timeSlotUp2Abnormal = new TimeSlotDTO("15:00:00",closingTime,"up2nor");
                    timeSlotDown2After = new TimeSlotDTO(closingTime,"00:00:00","down2");
                    timeSlotUp1Section = new TimeSlotDTO("05:00:00","13:00:00","up1section");
                    timeSlotUp2Section = new TimeSlotDTO("14:15:00",closingTime,"up2section");
                break;
            default:
                    if(isPre){
                        closingTime = "17:30";
                    }
                    timeSlotUp2Abnormal = new TimeSlotDTO("14:00:00",closingTime,"up2nor");
                    timeSlotDown2After = new TimeSlotDTO(closingTime,"00:00:00","down2");
                    timeSlotUp1Section = new TimeSlotDTO("05:00:00","12:00:00","up1section");
                    timeSlotUp2Section = new TimeSlotDTO("13:15:00",closingTime,"up2section");
                    timeSlotDown2Section = new TimeSlotDTO(closingTime,"05:00:00","down2section");
                break;
        }
        map.put("timeSlotUp1Normal",timeSlotUp1Normal);
        map.put("timeSlotUp1Abnormal",timeSlotUp1Abnormal);
        map.put("timeSlotDown1",timeSlotDown1);
        map.put("timeSlotUp2Normal",timeSlotUp2Normal);
        map.put("timeSlotUp2Abnormal",timeSlotUp2Abnormal);
        map.put("timeSlotDown2After",timeSlotDown2After);
        map.put("timeSlotDown2Pre",timeSlotDown2Pre);
        map.put("timeSlotUp1Section",timeSlotUp1Section);
        map.put("timeSlotUp2Section",timeSlotUp2Section);
        map.put("timeSlotDown2Section",timeSlotDown2Section);
        return map;
    }
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
