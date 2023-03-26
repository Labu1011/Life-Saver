package com.example.lifesaver;

public class DonorListItem {
    String name, bloodGroup, address, lastDonatedOn, phone;

    public DonorListItem() {
    }

    public DonorListItem(String name, String bloodGroup, String address, String lastDonatedOn, String phone) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.lastDonatedOn = lastDonatedOn;
        this.phone = phone;
    }

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

    public String getLastDonatedOn() {
        return lastDonatedOn;
    }

    public void setLastDonatedOn(String lastDonatedOn) {
        this.lastDonatedOn = lastDonatedOn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
