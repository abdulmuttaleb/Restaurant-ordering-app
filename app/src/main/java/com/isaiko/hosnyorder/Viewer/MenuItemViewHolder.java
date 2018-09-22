package com.isaiko.hosnyorder.Viewer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isaiko.hosnyorder.R;

public class MenuItemViewHolder extends RecyclerView.ViewHolder {
    public TextView itemNameTextView, itemPriceTextView;
    public ImageView itemAddToCart,itemPicImageView;
    public MenuItemViewHolder(View itemView) {
        super(itemView);
        itemNameTextView = itemView.findViewById(R.id.tv_item_name);
        itemPriceTextView = itemView.findViewById(R.id.tv_item_price);
        itemAddToCart = itemView.findViewById(R.id.iv_add_to_cart);
        itemPicImageView = itemView.findViewById(R.id.iv_item_image);
    }
}
