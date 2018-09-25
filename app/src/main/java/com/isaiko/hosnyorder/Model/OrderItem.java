package com.isaiko.hosnyorder.Model;

public class OrderItem {

    private int mOrderItemQuantity;
    private String mOrderItemUnitWeight;
    private Item mOrderItem;
    private float mOrderPrice, priceFactor;
    public OrderItem() {
    }

    public OrderItem(Item mOrderItem,int mOrderItemQuantity, String mOrderItemUnitWeight) {

        this.mOrderItemQuantity = mOrderItemQuantity;
        this.mOrderItem = mOrderItem;
        this.mOrderItemUnitWeight = mOrderItemUnitWeight;
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

    public String getmOrderItemUnitWeight() {
        return mOrderItemUnitWeight;
    }

    public void setmOrderItemUnitWeight(String mOrderItemUnitWeight) {
        this.mOrderItemUnitWeight = mOrderItemUnitWeight;
    }

    public float getmOrderPrice() {
        calculateOrderPrice();
        return mOrderPrice;
    }

    public void setmOrderPrice(float mOrderPrice) {
        this.mOrderPrice = mOrderPrice;
    }

    private void calculateOrderPrice(){

        switch(mOrderItemUnitWeight){
            case "⅓ Kg":
                priceFactor = (float)3/8;
                break;
            case "½ Kg":
                priceFactor = (float)1/2;
                break;
            case "¾ Kg":
                priceFactor = (float)3/4;
                break;
            case "1 Kg":
                priceFactor = (float)1;
                break;
        }
       mOrderPrice =  mOrderItem.getmItemPrice()* priceFactor * mOrderItemQuantity;
    }
}
