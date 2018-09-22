package com.isaiko.hosnyorder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.isaiko.hosnyorder.Activity.MenuActivity;
import com.isaiko.hosnyorder.Model.UI.CategoryUI;
import com.isaiko.hosnyorder.Model.User;
import com.isaiko.hosnyorder.R;
import com.isaiko.hosnyorder.Utils.CircleTransform;
import com.isaiko.hosnyorder.Viewer.CategoryItemViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryItemViewHolder>{
    private Context context;
    List<CategoryUI> categoriesList;

    public CategoryRecyclerViewAdapter(Context context, List<CategoryUI> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder holder, int position) {
        final CategoryUI category = categoriesList.get(position);
        holder.categoryColorCircle.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(category.getIconTint())));
        //holder.categoryImage.setImageResource(category.getIconResourceId());
        Picasso.with(context).load(category.getIconResourceId())
                .transform(new CircleTransform()).placeholder(R.drawable.ic_avatar_placeholder)
                .resize(64,64)
                .into(holder.categoryImage);
        holder.categoryTextView.setText(category.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(context, MenuActivity.class);
                menuIntent.putExtra("category",category.getName());
                menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(menuIntent);
            }
        });
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_main_category, parent, false);
        return new CategoryItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }
}
