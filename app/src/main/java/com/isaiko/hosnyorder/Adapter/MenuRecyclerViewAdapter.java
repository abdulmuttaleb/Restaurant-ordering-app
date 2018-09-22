package com.isaiko.hosnyorder.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.isaiko.hosnyorder.Model.Item;
import com.isaiko.hosnyorder.R;
import com.isaiko.hosnyorder.Viewer.MenuItemViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuItemViewHolder> implements Filterable{
    Context context;
    List<Item> itemsList;
    List<Item> filteredItemList;

    public MenuRecyclerViewAdapter(Context context, List<Item> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        filteredItemList = itemsList;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        final Item item = filteredItemList.get(position);
        holder.itemNameTextView.setText(item.getmItemName());
        holder.itemPriceTextView.setText(String.valueOf(item.getmItemPrice()));
        holder.itemAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add add_to_card functionality
            }
        });
        Picasso.with(context).load(item.getmItemImageUri())
                .centerCrop()
                .fit()
                .into(holder.itemPicImageView);
    }

    @Override
    public int getItemCount() {
        return filteredItemList.size();
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_menu_item,parent,false);
        return new MenuItemViewHolder(view);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterString = constraint.toString();
                if(filterString.equals("All")){
                    filteredItemList = itemsList;
                }else{
                    List<Item> tempFilteredList = new ArrayList<>();
                    for(Item item : itemsList){
                        if(item.getmItemCategory().equals(filterString)){
                            tempFilteredList.add(item);
                        }
                    }
                    filteredItemList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredItemList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItemList = (ArrayList<Item>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public List<Item> getFilteredItemList() {
        return filteredItemList;
    }

}