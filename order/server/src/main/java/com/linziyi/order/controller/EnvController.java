package com.linziyi.order.controller;

import com.linziyi.order.dto.TimeSlotDTO;
import com.linziyi.order.utils.TimeSlotUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/env")
@Slf4j
public class EnvController {
    @Value("${env}")
    private String env;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    @RefreshScope
    @GetMapping("/print")
    public String print(){
        return this.env;
    }
    /*public static void main(String[] args){
        Map map = TimeSlotUtil.listDataForOne(1, false);
        List<TimeSlotDTO> timeSlotDTOList = (List<TimeSlotDTO>) map.get("timeSlotList");
        for (TimeSlotDTO timeSlotDTO:timeSlotDTOList){
            log.info("-------type-------"+timeSlotDTO.getType()+"-------starttime-："+timeSlotDTO.getStratTime()+"----------------endTime------------"+timeSlotDTO.getEndTime());
        }
    }*/
}
