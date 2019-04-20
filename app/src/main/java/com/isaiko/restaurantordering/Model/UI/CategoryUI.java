package com.isaiko.restaurantordering.Model.UI;

public class CategoryUI {
    private int iconResourceId, iconTint;
    private String name;

    public CategoryUI( String name, int iconResourceId ,int iconTint) {
        this.iconResourceId = iconResourceId;
        this.name = name;
        this.iconTint = iconTint;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public String getName() {
        return name;
    }

    public int getIconTint() {
        return iconTint;
    }
}
