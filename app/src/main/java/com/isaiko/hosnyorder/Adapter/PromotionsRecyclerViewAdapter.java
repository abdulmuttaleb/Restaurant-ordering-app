 package com.isaiko.hosnyorder.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isaiko.hosnyorder.Model.Order;
import com.isaiko.hosnyorder.Model.Promotion;
import com.isaiko.hosnyorder.R;
import com.isaiko.hosnyorder.Viewer.PastOrderViewHolder;
import com.isaiko.hosnyorder.Viewer.PromotionViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

 public class PromotionsRecyclerViewAdapter extends RecyclerView.Adapter<PromotionViewHolder>{
     Context context;
     List<Promotion> promotionList;

     public PromotionsRecyclerViewAdapter(Context context, List<Promotion> promotionList) {
         this.context = context;
         this.promotionList = promotionList;
     }

     @Override
     public void onBindViewHolder(@NonNull PromotionViewHolder holder, int position) {
         final Promotion promotion = promotionList.get(position);
         holder.nameTextView.setText(promotion.getPromotionName());
         holder.fromTextView.setText(promotion.getPromotionFrom());
         holder.toTextView.setText(promotion.getPromotionTo());
     }

     @Override
     public int getItemCount() {
         return promotionList.size();
     }

     @NonNull
     @Override
     public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater inflater = LayoutInflater.from(context);
         View view = inflater.inflate(R.layout.item_promotion,parent,false);
         return new PromotionViewHolder(view);
     }

 }
