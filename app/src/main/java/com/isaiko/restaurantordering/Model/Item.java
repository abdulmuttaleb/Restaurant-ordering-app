package com.isaiko.restaurantordering.Model;

import java.io.Serializable;

public class Item implements Serializable {
    private String mItemName, mItemCategory, itemMenu;
    private float mItemPrice;
    private String mItemKey;
    private String mItemImageUri;
    public Item() {
    }

    public Item(String mItemName, String mItemCategory, float mItemPrice, String mItemImageUri) {
        this.mItemName = mItemName;
        this.mItemCategory = mItemCategory;
        this.mItemPrice = mItemPrice;
        this.mItemImageUri = mItemImageUri;
    }


    public String getmItemName() {
        return mItemName;
    }

    public void setmItemName(String mItemName) {
        this.mItemName = mItemName;
    }

    public String getmItemCategory() {
        return mItemCategory;
    }

    public void setmItemCategory(String mItemCategory) {
        this.mItemCategory = mItemCategory;
    }

    public float getmItemPrice() {
        return mItemPrice;
    }

    public void setmItemPrice(float mItemPrice) {
        this.mItemPrice = mItemPrice;
    }

    public String getmItemKey() {
        return mItemKey;
    }

    public void setmItemKey(String mItemKey) {
        this.mItemKey = mItemKey;
    }

    public String getmItemImageUri() {
        return mItemImageUri;
    }

    public void setmItemImageUri(String mItemImageUri) {
        this.mItemImageUri = mItemImageUri;
    }

    public String getItemMenu() {
        return itemMenu;
    }

    public void setItemMenu(String itemMenu) {
        this.itemMenu = itemMenu;
    }
}