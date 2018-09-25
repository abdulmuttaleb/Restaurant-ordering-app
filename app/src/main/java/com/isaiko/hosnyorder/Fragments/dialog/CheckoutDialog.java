package com.isaiko.hosnyorder.Fragments.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.isaiko.hosnyorder.Adapter.CartRecyclerViewAdapter;
import com.isaiko.hosnyorder.Model.Cart;
import com.isaiko.hosnyorder.Model.Item;
import com.isaiko.hosnyorder.Model.Order;
import com.isaiko.hosnyorder.Model.OrderItem;
import com.isaiko.hosnyorder.Model.User;
import com.isaiko.hosnyorder.R;
import com.isaiko.hosnyorder.Utils.States;

public class CheckoutDialog extends DialogFragment {
    Order passedOrder;
    EditText orderCommentTextView, addressCommentTextView, alternativeAddress;
    String orderComments, addressComments, altAddress;
    DatabaseReference ordersReference,userOrdersReference;
    FirebaseAuth mAuth;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        passedOrder = (Order)getArguments().getSerializable("passedOrder");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_checkout,null);
        orderCommentTextView = view.findViewById(R.id.et_order_comments);
        addressCommentTextView = view.findViewById(R.id.et_address_comments);
        alternativeAddress = view.findViewById(R.id.et_alt_address);
        mAuth = FirebaseAuth.getInstance();
        ordersReference = FirebaseDatabase.getInstance().getReference().child("Orders");
        userOrdersReference = FirebaseDatabase.getInstance().getReference().child("UserOrders");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Submit order")
                .setPositiveButton(R.string.label_confirm,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                validateForm();
                                passedOrder.setmOrderComments(orderComments);
                                passedOrder.setmOrderAddressComments(addressComments);
                                passedOrder.setmOrderAddress(altAddress);
                                passedOrder.setmOrderStatus(States.ORDER_OPENED);
                                String key = ordersReference.push().getKey();
                                passedOrder.setmOrderKey(key);
                                ordersReference.child(key).setValue(passedOrder);
                                userOrdersReference.child(FirebaseAuth.getInstance().getUid()).child(key).setValue(passedOrder)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getUid()).removeValue();
                                    }
                                });
                            }
                        });
        return builder.create();
    }

    private void validateForm(){
        if(TextUtils.isEmpty(orderCommentTextView.getText().toString())){
            orderComments = "";
        }else{
            orderComments = orderCommentTextView.getText().toString();
        }
        if(TextUtils.isEmpty(addressCommentTextView.getText().toString())){
            addressComments = "";
        }else{
            addressComments = addressCommentTextView.getText().toString();
        }
        if(TextUtils.isEmpty(orderCommentTextView.getText().toString())){
            altAddress = User.getInstance().getmUserAddress().addressToString();
        }else{
            altAddress = alternativeAddress.getText().toString();
        }
    }
}
