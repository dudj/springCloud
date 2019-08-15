package com.linziyi.user.dataobject;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Clock {
    @Id
    private Integer id;
    private String name;
    private Integer type;
    private String starttime;
    private String endtime;
    private Integer season;
    private Integer preholiday;
}
