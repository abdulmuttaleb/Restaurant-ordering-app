 package com.isaiko.hosnyorder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.isaiko.hosnyorder.Activity.MenuActivity;
import com.isaiko.hosnyorder.Activity.PastOrderContentActivity;
import com.isaiko.hosnyorder.Fragments.dialog.AddToCartDialog;
import com.isaiko.hosnyorder.Model.Item;
import com.isaiko.hosnyorder.Model.Order;
import com.isaiko.hosnyorder.R;
import com.isaiko.hosnyorder.Viewer.MenuItemViewHolder;
import com.isaiko.hosnyorder.Viewer.PastOrderViewHolder;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PastOrderRecyclerViewAdapter extends RecyclerView.Adapter<PastOrderViewHolder>{
    Context context;
    List<Order> itemsList;

    public PastOrderRecyclerViewAdapter(Context context, List<Order> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrderViewHolder holder, int position) {
        final Order item = itemsList.get(position);
        holder.orderValueTextView.setText(String.valueOf(item.getmOrderTotalToPay())+" LE");
        holder.orderDateTextView.setText(item.getmOrderOrderedDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderIntent = new Intent(context, PastOrderContentActivity.class);
                orderIntent.putExtra("orders",(Serializable)item.getmOrderItems());
                orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(orderIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    @NonNull
    @Override
    public PastOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_past_orders,parent,false);
        return new PastOrderViewHolder(view);
    }

}
