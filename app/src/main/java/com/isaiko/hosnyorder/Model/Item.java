package com.isaiko.hosnyorder.Model;

public class Item {
    private String mOrderItemName, mOrderItemCategory;
    private float mOrderItemPrice;

    public Item() {
    }

    public Item(String mOrderItemName, String mOrderItemCategory, float mOrderItemPrice) {
        this.mOrderItemName = mOrderItemName;
        this.mOrderItemCategory = mOrderItemCategory;
        this.mOrderItemPrice = mOrderItemPrice;
    }


    public String getmOrderItemName() {
        return mOrderItemName;
    }

    public void setmOrderItemName(String mOrderItemName) {
        this.mOrderItemName = mOrderItemName;
    }

    public String getmOrderItemCategory() {
        return mOrderItemCategory;
    }

    public void setmOrderItemCategory(String mOrderItemCategory) {
        this.mOrderItemCategory = mOrderItemCategory;
    }

    public float getmOrderItemPrice() {
        return mOrderItemPrice;
    }

    public void setmOrderItemPrice(float mOrderItemPrice) {
        this.mOrderItemPrice = mOrderItemPrice;
    }
}
