package com.example.lifesaver;

import com.google.firebase.Timestamp;

public class Donor {
    private String name;
    private String bloodGroup;
    private String address;
    private int phone;
    private Timestamp lastDonated;

    public Donor() {}

    public Donor(String name, String bloodGroup, String address, int phone, Timestamp lastDonated) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.phone = phone;
        this.lastDonated = lastDonated;
    }

    public String getName() {
        return name;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public int getPhone() {
        return phone;
    }

    public Timestamp getLastDonated() {
        return lastDonated;
    }
}
