package com.example.lifesaver;

public class Donor {
    private String name;
    private String bloodGroup;
    private String address;
    private int phone;
    private String lastDonatedOn;

    public Donor() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getLastDonatedOn() {
        return lastDonatedOn;
    }

    public void setLastDonatedOn(String lastDonatedOn) {
        this.lastDonatedOn = lastDonatedOn;
    }
}
