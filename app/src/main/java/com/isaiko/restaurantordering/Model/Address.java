package com.isaiko.restaurantordering.Model;

public class Address {
    private String mCity, mArea, mAddressNote, mBuilding, mFloor, mApartmentNumber;

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

    public String getmBuilding() {
        return mBuilding;
    }

    public void setmBuilding(String mBuilding) {
        this.mBuilding = mBuilding;
    }

    public String getmFloor() {
        return mFloor;
    }

    public void setmFloor(String mFloor) {
        this.mFloor = mFloor;
    }

    public String getmApartmentNumber() {
        return mApartmentNumber;
    }

    public void setmApartmentNumber(String mApartmentNumber) {
        this.mApartmentNumber = mApartmentNumber;
    }


    public String addressToString() {
        return  mArea +
                ",Building: " + mBuilding+
                ",Floor: " + mFloor+
                ",Unit: " + mApartmentNumber;
    }
}
