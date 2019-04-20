package com.isaiko.restaurantordering.Model;

public class Promotion {
    String promotionName, promotionFrom, promotionTo, promotionKey;
    long promotionToMilli;

    public Promotion() {
    }

    public Promotion(String promotionName, String promotionFrom, String promotionTo) {
        this.promotionName = promotionName;
        this.promotionFrom = promotionFrom;
        this.promotionTo = promotionTo;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionFrom() {
        return promotionFrom;
    }

    public void setPromotionFrom(String promotionFrom) {
        this.promotionFrom = promotionFrom;
    }

    public String getPromotionTo() {
        return promotionTo;
    }

    public void setPromotionTo(String promotionTo) {
        this.promotionTo = promotionTo;
    }

    public long getPromotionMilli() {
        return promotionToMilli;
    }

    public void setPromotionMilli(long promotionMilli) {
        this.promotionToMilli = promotionMilli;
    }

    public String getPromotionKey() {
        return promotionKey;
    }

    public void setPromotionKey(String promotionKey) {
        this.promotionKey = promotionKey;
    }
}
