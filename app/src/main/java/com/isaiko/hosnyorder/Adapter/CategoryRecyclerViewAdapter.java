package com.isaiko.hosnyorder.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.isaiko.hosnyorder.Model.UI.CategoryUI;
import com.isaiko.hosnyorder.R;
import com.isaiko.hosnyorder.Viewer.CategoryItemViewHolder;

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
        holder.categoryColorCircle.setColorFilter(context.getResources().getColor(category.getIconTint()));
        holder.categoryImage.setImageResource(category.getIconResourceId());
        holder.categoryTextView.setText(category.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, category.getName()+" was chosen!", Toast.LENGTH_SHORT).show();
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
