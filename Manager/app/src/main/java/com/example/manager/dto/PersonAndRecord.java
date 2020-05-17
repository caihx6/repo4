package com.example.manager.dto;

import com.example.manager.entity.Record;

public class PersonAndRecord {
    private String name;
    private String phone_number;
    private String idCard;
    private String address;
    private Record record;

    public PersonAndRecord(String name, String phone_number, String idCard, String address, Record record) {
        this.name = name;
        this.phone_number = phone_number;
        this.idCard = idCard;
        this.address = address;
        this.record = record;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
}
