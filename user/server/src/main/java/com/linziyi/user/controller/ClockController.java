package com.linziyi.user.controller;

import com.linziyi.user.dataobject.Clock;
import com.linziyi.user.dto.TimeSlotDTO;
import com.linziyi.user.service.ClockService;
import com.linziyi.user.service.impl.RedisService;
import com.linziyi.user.util.PinyinUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author:lkk
 * @Date:2019-08-07
 * @Effort:定时任务实现时间点的缓存
 */
@RestController
@Slf4j
public class ClockController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private ClockService clockService;
    /**
     * 将节假日前一天和所有数据 都进行缓存
     * key = 园区-type(上班1或下班1)-preholiday(是否为节假日)-season(节令)
     */
    @Scheduled(cron = "00 00 00 1 * ?")
    public void resultData() throws Exception{
        long time = System.currentTimeMillis();
        Long expireTime = Long.valueOf(60*60*24*30);
        List<Clock> clockList = this.clockService.findAll();
        String key = "";
        TimeSlotDTO timeSlotDTO;
        PinyinUtil pinyinUtil = new PinyinUtil();
        for(Clock clock:clockList){
            String[] nameArr = convertStrToArray(clock.getName());
            timeSlotDTO = new TimeSlotDTO(clock.getStarttime(),clock.getEndtime(),clock.getType().toString());
            for (int i=0;i<nameArr.length;i++){
                key = pinyinUtil.toPinYin(nameArr[i],"",PinyinUtil.Type.LOWERCASE)+"-"+clock.getType()+"-"+clock.getPreholiday()+"-"+clock.getSeason();
                log.info("key:"+i+"---"+key);
                log.info("value:"+i+"---"+timeSlotDTO.toString());
                redisService.set(key,timeSlotDTO,expireTime);
            }
        }
        System.out.println("time:"+(System.currentTimeMillis() - time));
    }
    /**
     * 将字符串转为数组
     * @param str
     * @return
     */
    public static String[] convertStrToArray(String str){
        String[] strArray = null;
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }
}
