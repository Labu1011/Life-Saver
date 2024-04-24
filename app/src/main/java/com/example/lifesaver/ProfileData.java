package com.example.lifesaver;

public class ProfileData {
    public String name, address, phone, bloodGroup, lastDonatedOn;

    public ProfileData() {}

    public ProfileData(String name, String address, String phone, String bloodGroup, String lastDonatedOn) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.lastDonatedOn = lastDonatedOn;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getLastDonatedOn() {
        return lastDonatedOn;
    }

    public void setLastDonatedOn(String lastDonatedOn) {
        this.lastDonatedOn = lastDonatedOn;
    }
}
