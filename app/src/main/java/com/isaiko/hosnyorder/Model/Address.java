package com.isaiko.hosnyorder.Model;

public class Address {
    private String mCity, mArea, mAddressNote;
    private int mBuilding, mFloor, mApartmentNumber;

    public Address() {
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmArea() {
        return mArea;
    }

    public void setmArea(String mArea) {
        this.mArea = mArea;
    }

    public String getmAddressNote() {
        return mAddressNote;
    }

    public void setmAddressNote(String mAddressNote) {
        this.mAddressNote = mAddressNote;
    }

    public int getmBuilding() {
        return mBuilding;
    }

    public void setmBuilding(int mBuilding) {
        this.mBuilding = mBuilding;
    }

    public int getmFloor() {
        return mFloor;
    }

    public void setmFloor(int mFloor) {
        this.mFloor = mFloor;
    }

    public int getmApartmentNumber() {
        return mApartmentNumber;
    }

    public void setmApartmentNumber(int mApartmentNumber) {
        this.mApartmentNumber = mApartmentNumber;
    }
}
