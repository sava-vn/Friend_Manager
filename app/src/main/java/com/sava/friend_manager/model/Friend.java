package com.sava.friend_manager.model;

public class Friend {
    private int id;
    private String avata;
    private String name;
    private String brithDay;
    private String phone;
    private String email;
    private String address;
    private int type;

    public Friend() {
        this.type =1;
    }

    public Friend(int id, String avata, String name, String brithDay, String phone, String email, String address, int type) {
        this.id = id;
        this.avata = avata;
        this.name = name;
        this.brithDay = brithDay;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.type = type;
    }

    public Friend(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrithDay() {
        return brithDay;
    }

    public void setBrithDay(String brithDay) {
        this.brithDay = brithDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
