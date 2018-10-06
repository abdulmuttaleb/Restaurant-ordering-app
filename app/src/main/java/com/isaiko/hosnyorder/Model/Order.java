package com.isaiko.hosnyorder.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable{
    private String mOrderKey, mOrderOrderedDate,mOrderTime,mOrderStatus,mOrderType,mOrderComments,mOrderAddressComments, orderUserId, itemBranch, itemUserName;
    private float mOrderTotalToPay;
    private String mOrderAddress;
    private List<OrderItem> mOrderItems;

    public Order() {
        mOrderItems = new ArrayList<>();
    }

    public Order(String mOrderId) {
        this.mOrderKey = mOrderId;
    }

    public List<OrderItem> getmOrderItems() {
        return mOrderItems;
    }

    public void setmOrderItems(List<OrderItem> mOrderItems) {
        this.mOrderItems = mOrderItems;
    }

    public String getmOrderType() {
        return mOrderType;
    }

    public void setmOrderType(String mOrderType) {
        this.mOrderType = mOrderType;
    }

    public String getmOrderKey() {
        return mOrderKey;
    }

    public void setmOrderKey(String mOrderId) {
        this.mOrderKey = mOrderId;
    }

    public String getmOrderOrderedDate() {
        return mOrderOrderedDate;
    }

    public void setmOrderOrderedDate(String mOrderOrderedDate) {
        this.mOrderOrderedDate = mOrderOrderedDate;
    }

    public String getmOrderStatus() {
        return mOrderStatus;
    }

    public void setmOrderStatus(String mOrderStatus) {
        this.mOrderStatus = mOrderStatus;
    }

    public float getmOrderTotalToPay() {
        return mOrderTotalToPay;
    }

    public void setmOrderTotalToPay(float mOrderTotalToPay) {
        this.mOrderTotalToPay = mOrderTotalToPay;
    }

    public String getmOrderAddress() {
        return mOrderAddress;
    }

    public void setmOrderAddress(String mOrderAddress) {
        this.mOrderAddress = mOrderAddress;
    }

    public String getmOrderComments() {
        return mOrderComments;
    }

    public void setmOrderComments(String mOrderComments) {
        this.mOrderComments = mOrderComments;
    }

    public String getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(String orderUserId) {
        this.orderUserId = orderUserId;
    }

    public String getmOrderAddressComments() {
        return mOrderAddressComments;
    }

    public void setmOrderAddressComments(String mOrderAddressComments) {
        this.mOrderAddressComments = mOrderAddressComments;
    }

    public String getmOrderTime() {
        return mOrderTime;
    }

    public void setmOrderTime(String mOrderTime) {
        this.mOrderTime = mOrderTime;
    }

    public String getItemBranch() {
        return itemBranch;
    }

    public void setItemBranch(String itemBranch) {
        this.itemBranch = itemBranch;
    }

    public String getItemUserName() {
        return itemUserName;
    }

    public void setItemUserName(String itemUserName) {
        this.itemUserName = itemUserName;
    }
}
