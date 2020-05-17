package com.example.manager.entity;

public class Person {
    //居民姓名
    private String name;
    //居民电话（作为登录账号）
    private String phone_number;
    //登录密码
    private String password;
    //居民身份号
    private String id_Card;
    //居民住址
    private String address;

    public Person(String name, String address, String id_Card, String phone_number, String password) {
        this.name = name;
        this.address = address;
        this.id_Card = id_Card;
        this.phone_number = phone_number;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId_Card() {
        return id_Card;
    }

    public void setId_Card(String id_Card) {
        this.id_Card = id_Card;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
