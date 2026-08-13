package com.studentattendance.models;
import java.io.Serializable;
import java.util.ArrayList;

public class Pearson implements Serializable {
    private String name;
    private String gender;
    private ArrayList<String> phoneNumber = new ArrayList<>();
    private String Address;

    public Pearson() {
    }
    public Pearson(String name, String gender, String address) {
        this.name = name;
        this.gender = gender;
        Address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber1, String phoneNumber2) {
        if (phoneNumber.size() < 2){
            phoneNumber.add(0, phoneNumber1);
            phoneNumber.add(1, phoneNumber2);
        }
        else {
            phoneNumber.set(0, phoneNumber1);
            phoneNumber.set(1, phoneNumber2);
        }
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
