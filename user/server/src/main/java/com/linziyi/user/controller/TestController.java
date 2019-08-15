package com.linziyi.user.controller;

import com.linziyi.user.dto.TimeSlotDTO;
import com.linziyi.user.service.ClockService;
import com.linziyi.user.service.impl.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ClockService clockService;
    @Autowired
    private RedisService redisService;
    @GetMapping("/clock")
    public void clock(HttpServletRequest request) throws Exception{
        TimeSlotDTO timeSlotDTO = (TimeSlotDTO) redisService.get("huilongguan-6-0-1");
        log.info(timeSlotDTO.toString());
        log.info(timeSlotDTO.getStratTime());
        log.info(timeSlotDTO.getEndTime());
        log.info(timeSlotDTO.getType());
    }
}
