package com.isaiko.hosnyorder.Model;

public class Promotion {
    String promotionName, promotionFrom, promotionTo,promotionImageUri;
    long promotionMilli;

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
        return promotionMilli;
    }

    public void setPromotionMilli(long promotionMilli) {
        this.promotionMilli = promotionMilli;
    }

    public String getPromotionImageUri() {
        return promotionImageUri;
    }

    public void setPromotionImageUri(String promotionImageUri) {
        this.promotionImageUri = promotionImageUri;
    }
}
