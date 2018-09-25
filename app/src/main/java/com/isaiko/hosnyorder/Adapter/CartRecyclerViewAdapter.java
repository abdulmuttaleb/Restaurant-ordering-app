package com.isaiko.hosnyorder.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.isaiko.hosnyorder.Activity.MenuActivity;
import com.isaiko.hosnyorder.Fragments.CartFragment;
import com.isaiko.hosnyorder.Fragments.dialog.AddToCartDialog;
import com.isaiko.hosnyorder.Model.Cart;
import com.isaiko.hosnyorder.Model.Item;
import com.isaiko.hosnyorder.Model.OrderItem;
import com.isaiko.hosnyorder.R;
import com.isaiko.hosnyorder.Viewer.CartItemViewHolder;
import com.isaiko.hosnyorder.Viewer.MenuItemViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static com.isaiko.hosnyorder.Fragments.CartFragment.updateCartTotal;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartItemViewHolder>{
    Context context;
    public List<OrderItem> itemsList;

    public CartRecyclerViewAdapter(Context context, List<OrderItem> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartItemViewHolder holder, int position) {
        final OrderItem item = itemsList.get(position);
        holder.itemNameTextView.setText(item.getmOrderItem().getmItemName());
        holder.itemQuantityTextView.setText(String.valueOf(item.getmOrderItemQuantity()));
        holder.itemIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemQuantity = item.getmOrderItemQuantity();
                itemQuantity += 1;
                item.setmOrderItemQuantity(itemQuantity);
                Cart.getInstance().getmItems().put(item.getmOrderItem().getmItemKey(),item);
                FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getUid()).setValue(Cart.getInstance());
                holder.itemQuantityTextView.setText(String.valueOf(itemQuantity));
                updateCartTotal();
                notifyDataSetChanged();
            }
        });
        holder.itemDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemQuantity = item.getmOrderItemQuantity();
                if(itemQuantity != 1) {
                    itemQuantity -= 1;
                    item.setmOrderItemQuantity(itemQuantity);
                    Cart.getInstance().getmItems().put(item.getmOrderItem().getmItemKey(), item);
                    FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getUid()).setValue(Cart.getInstance());
                    holder.itemQuantityTextView.setText(String.valueOf(itemQuantity));
                    updateCartTotal();
                    notifyDataSetChanged();
                }
            }
        });

        holder.deleteItemImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Remove")
                        .setMessage("Are you sure you want to delete item from your cart?")
                        .setPositiveButton(R.string.label_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Cart.getInstance().getmItems().remove(item.getmOrderItem().getmItemKey());
                                FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getUid()).setValue(Cart.getInstance());
                                itemsList.clear();
                                itemsList.addAll(Cart.getInstance().getCartItemsList());
                                updateCartTotal();
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.itemPriceTextView.setText(String.valueOf(item.getmOrderPrice()));
        Picasso.with(context).load(item.getmOrderItem().getmItemImageUri())
                .centerCrop()
                .fit()
                .into(holder.itemPicImageView);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_cart_item,parent,false);
        return new CartItemViewHolder(view);
    }

    public void notifyCartAdapter(){
        this.notifyDataSetChanged();
    }
}
