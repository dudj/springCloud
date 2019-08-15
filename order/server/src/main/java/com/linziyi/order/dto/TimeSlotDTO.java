package com.linziyi.order.dto;

import lombok.Data;

@Data
public class TimeSlotDTO {
    private String stratTime;
    private String endTime;
    private String type;

    public TimeSlotDTO(String stratTime, String endTime, String type) {
        this.stratTime = stratTime;
        this.endTime = endTime;
        this.type = type;
    }
}
