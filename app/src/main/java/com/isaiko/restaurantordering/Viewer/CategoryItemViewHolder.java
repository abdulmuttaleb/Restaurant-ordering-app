package com.isaiko.restaurantordering.Viewer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isaiko.restaurantordering.R;

public class CategoryItemViewHolder extends RecyclerView.ViewHolder {
    public ImageView categoryColorCircle,categoryImage;
    public TextView categoryTextView;
    public CategoryItemViewHolder(View itemView) {
        super(itemView);
        categoryColorCircle = itemView.findViewById(R.id.iv_category_circle);
        categoryImage = itemView.findViewById(R.id.iv_category_icon);
        categoryTextView = itemView.findViewById(R.id.tv_category_text);
    }
}
