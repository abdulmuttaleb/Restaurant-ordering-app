package com.isaiko.restaurantordering.Fragments.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.isaiko.restaurantordering.Model.Cart;
import com.isaiko.restaurantordering.Model.Item;
import com.isaiko.restaurantordering.Model.OrderItem;
import com.isaiko.restaurantordering.R;

public class AddToCartDialog extends DialogFragment {
    Item passedItem;
    TextView itemNameTextView;
    //itemFinalPriceTextView;
    EditText itemOrderQuantityEditText;
    Spinner itemWeightSpinner;
    ArrayAdapter<String> weightsAdapter;
    DatabaseReference userCartReference;
    FirebaseAuth mAuth;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        passedItem = (Item)getArguments().getSerializable("passedItem");
        final String itemType = getArguments().getString("itemType");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_order,null);
        itemNameTextView = view.findViewById(R.id.tv_item_name);
        //itemFinalPriceTextView = view.findViewById(R.id.tv_final_price);
        itemOrderQuantityEditText = view.findViewById(R.id.et_number_of_orders);
        itemWeightSpinner = view.findViewById(R.id.spinner_weight);
        weightsAdapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner_item,getResources().getStringArray(R.array.order_weights)){
            @Override
            public boolean isEnabled(int position) {
                if(itemType.equals("Mahashy")||itemType.equals("Soups")||itemType.equals("Poultry")){
                    if(position == 0 || position == 1 || position == 2){
                        return false;
                    }else{
                        return true;
                    }
                }else{
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(itemType.equals("Mahashy")||itemType.equals("Soups")||itemType.equals("Poultry")){
                    if(position == 0 || position == 1 || position == 2){
                        tv.setTextColor(Color.GRAY);
                    }else{
                        tv.setTextColor(Color.BLACK);
                    }
                }else{
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        itemWeightSpinner.setAdapter(weightsAdapter);
        itemWeightSpinner.setSelection(3);
        mAuth = FirebaseAuth.getInstance();
        userCartReference = FirebaseDatabase.getInstance().getReference().child("Cart").child(mAuth.getUid());
        itemNameTextView.setText(passedItem.getmItemName());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Add to cart")
                .setPositiveButton(R.string.label_confirm,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                if(validateForms()) {
                                    OrderItem newOrderItem = new OrderItem(passedItem,
                                            Integer.valueOf(itemOrderQuantityEditText.getText().toString()),
                                            itemWeightSpinner.getSelectedItem().toString());
                                    Cart.getInstance().insertItem(newOrderItem);
                                    userCartReference.setValue(Cart.getInstance());
                                    Toast.makeText(getActivity(), passedItem.getmItemName()+" was added successfully to your cart!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
        return builder.create();
    }
    private boolean validateForms(){
        if(TextUtils.isEmpty(itemOrderQuantityEditText.getText().toString())){
            itemOrderQuantityEditText.setError("Can't be empty");
            return false;
        }

        return true;
    }

}
