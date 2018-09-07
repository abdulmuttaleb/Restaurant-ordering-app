package com.isaiko.hosnyorder.Model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<OrderItem> mItems = new ArrayList<>();

    public Cart() {
    }

    public List<OrderItem> getmItems() {
        return mItems;
    }

    public void setmItems(List<OrderItem> mItems) {
        this.mItems = mItems;
    }

    public void insertItem(OrderItem item){
        mItems.add(item);
    }
}
