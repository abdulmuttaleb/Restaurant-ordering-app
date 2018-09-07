package com.isaiko.hosnyorder.Model;

public class OrderItem {
    private String mOrderItemName, mOrderItemCategory;
    private float mOrderItemPrice;
    private int mOrderItemQuantity;

    public OrderItem() {
    }

    public OrderItem(String mOrderItemName, String mOrderItemCategory, float mOrderItemPrice, int mOrderItemQuantity) {
        this.mOrderItemName = mOrderItemName;
        this.mOrderItemCategory = mOrderItemCategory;
        this.mOrderItemPrice = mOrderItemPrice;
        this.mOrderItemQuantity = mOrderItemQuantity;
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

    public int getmOrderItemQuantity() {
        return mOrderItemQuantity;
    }

    public void setmOrderItemQuantity(int mOrderItemQuantity) {
        this.mOrderItemQuantity = mOrderItemQuantity;
    }
}
