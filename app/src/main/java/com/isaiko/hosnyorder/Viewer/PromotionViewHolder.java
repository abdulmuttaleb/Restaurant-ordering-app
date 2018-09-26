package com.isaiko.hosnyorder.Viewer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isaiko.hosnyorder.R;

public class PromotionViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTextView, fromTextView, toTextView;
    public ImageView promotionImage;
    public PromotionViewHolder(View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.tv_promotion_name);
        fromTextView = itemView.findViewById(R.id.tv_promotion_from);
        toTextView = itemView.findViewById(R.id.tv_promotion_to);
        promotionImage = itemView.findViewById(R.id.iv_promotion_image);
    }
}
