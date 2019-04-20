package com.isaiko.restaurantordering.Viewer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.isaiko.restaurantordering.R;

public class PromotionViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTextView, fromTextView, toTextView;
    public PromotionViewHolder(View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.tv_promotion_name);
        fromTextView = itemView.findViewById(R.id.tv_promotion_from);
        toTextView = itemView.findViewById(R.id.tv_promotion_to);
    }
}
