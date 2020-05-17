package com.example.manager.entity;

import java.util.Date;

/**
 * 每天上报信息
 */
public class Record {
    //用户ID
    private Integer personId;
    //体温
    private  String temperature;
    //上报时间
    private Date date;

    public Record(Integer personId, String temperature, Date date) {
        this.personId = personId;
        this.temperature = temperature;
        this.date = date;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
