package com.isaiko.hosnyorder.Model;

public class OrderItem {

    private int mOrderItemQuantity;
    private Item mOrderItem;
    public OrderItem() {
    }

    public OrderItem(Item mOrderItem,int mOrderItemQuantity) {

        this.mOrderItemQuantity = mOrderItemQuantity;
        this.mOrderItem = mOrderItem;
    }

    public int getmOrderItemQuantity() {
        return mOrderItemQuantity;
    }

    public void setmOrderItemQuantity(int mOrderItemQuantity) {
        this.mOrderItemQuantity = mOrderItemQuantity;
    }

    public Item getmOrderItem() {
        return mOrderItem;
    }

    public void setmOrderItem(Item mOrderItem) {
        this.mOrderItem = mOrderItem;
    }
}
