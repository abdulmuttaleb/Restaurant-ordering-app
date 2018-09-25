package com.isaiko.hosnyorder.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    private HashMap<String,OrderItem> mItems;

    public Cart() {
        mItems = new HashMap<>();
    }
    private static Cart currentUserCart = new Cart();
    public static Cart getInstance(){ return currentUserCart;}
    public static void setCurrentCart(Cart currentCart) {
        Cart.currentUserCart = currentCart;
    }

    public HashMap<String,OrderItem> getmItems() {
        return mItems;
    }

    public void setmItems(HashMap<String,OrderItem> mItems) {
        this.mItems = mItems;
    }

    public void insertItem(OrderItem item){
        mItems.put(item.getmOrderItem().getmItemKey(),item);
    }

    public List<OrderItem> getCartItemsList(){
        return new ArrayList<>(mItems.values());
    }
    public float getCartValue(){
        float price = 0;
        List<OrderItem> items = getCartItemsList();
        for(OrderItem item:items){
            price += item.getmOrderPrice();
        }
        return price;
    }
    public void clearCurrentCart(){
        currentUserCart.getmItems().clear();
    }
}
