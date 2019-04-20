package com.isaiko.restaurantordering.Fragments.dialog;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.isaiko.restaurantordering.Model.Order;
import com.isaiko.restaurantordering.Model.User;
import com.isaiko.restaurantordering.R;
import com.isaiko.restaurantordering.Utils.States;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckoutDialog extends DialogFragment {
    Order passedOrder;
    EditText orderCommentTextView, addressCommentTextView, alternativeAddress;
    Spinner paymentMethodSpinner;
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
        paymentMethodSpinner = view.findViewById(R.id.spinner_payment_method);

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
                                DialogInterface d1 = dialog;
                                validateForm();
                                passedOrder.setmOrderComments(orderComments);
                                passedOrder.setmOrderAddressComments(addressComments);
                                passedOrder.setmOrderAddress(altAddress);
                                passedOrder.setmOrderStatus(States.ORDER_OPENED);
                                passedOrder.setItemUserName(User.getInstance().getmUserName());
                                passedOrder.setPaymentMethod(paymentMethodSpinner.getSelectedItem().toString());
                                passedOrder.setItemUserPhone(User.getInstance().getmPhoneNumber());
                                String key = ordersReference.push().getKey();
                                passedOrder.setmOrderKey(key);
                                Date c = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
                                String formattedDate = df.format(c);
                                passedOrder.setmOrderOrderedDate(formattedDate);
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
        if(TextUtils.isEmpty(alternativeAddress.getText().toString())){
            altAddress = User.getInstance().getmUserAddress().addressToString();
        }else{
            altAddress = alternativeAddress.getText().toString();
        }
    }


}
