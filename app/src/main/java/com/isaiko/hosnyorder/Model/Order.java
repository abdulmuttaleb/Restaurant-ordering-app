package com.isaiko.hosnyorder.Model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String mOrderId, mOrderOrderedDate,mOrderShippedDate,mOrderStatus;
    private float mOrderTotalToPay;
    private Address mOrderAddress;
    private List<OrderItem> mOrderItems;

    public Order() {
        mOrderItems = new ArrayList<>();
    }

    public Order(String mOrderId) {
        this.mOrderId = mOrderId;
    }

    public List<OrderItem> getmOrderItems() {
        return mOrderItems;
    }

    public void setmOrderItems(List<OrderItem> mOrderItems) {
        this.mOrderItems = mOrderItems;
    }
}
