package com.isaiko.restaurantordering.Viewer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isaiko.restaurantordering.R;

public class CartItemViewHolder extends RecyclerView.ViewHolder {
    public TextView itemNameTextView, itemPriceTextView,itemQuantityTextView;
    public ImageView itemIncrease,itemDecrease,itemPicImageView,deleteItemImageView;
    public CartItemViewHolder(View itemView) {
        super(itemView);
        itemNameTextView = itemView.findViewById(R.id.tv_item_name);
        itemPriceTextView = itemView.findViewById(R.id.tv_item_price);
        itemPicImageView = itemView.findViewById(R.id.iv_item_image);
        itemQuantityTextView = itemView.findViewById(R.id.tv_item_quantity);
        itemIncrease = itemView.findViewById(R.id.iv_increase);
        itemDecrease = itemView.findViewById(R.id.iv_decrease);
        deleteItemImageView = itemView.findViewById(R.id.iv_delete_item);
    }
}
