package com.isaiko.hosnyorder.Viewer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isaiko.hosnyorder.R;

public class PastOrderViewHolder extends RecyclerView.ViewHolder {
    public TextView orderDateTextView, orderValueTextView;
    public PastOrderViewHolder(View itemView) {
        super(itemView);
        orderDateTextView = itemView.findViewById(R.id.tv_order_date);
        orderValueTextView = itemView.findViewById(R.id.tv_order_paid);
    }
}
