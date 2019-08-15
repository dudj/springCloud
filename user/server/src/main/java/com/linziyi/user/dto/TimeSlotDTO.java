package com.linziyi.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TimeSlotDTO implements Serializable {
    private String stratTime;
    private String endTime;
    private String type;

    public TimeSlotDTO(String stratTime, String endTime, String type) {
        this.stratTime = stratTime;
        this.endTime = endTime;
        this.type = type;
    }
}
