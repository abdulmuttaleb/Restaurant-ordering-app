package com.isaiko.restaurantordering.Fragments;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isaiko.restaurantordering.Adapter.CartRecyclerViewAdapter;
import com.isaiko.restaurantordering.Fragments.dialog.CheckoutDialog;
import com.isaiko.restaurantordering.Model.Cart;
import com.isaiko.restaurantordering.Model.Order;
import com.isaiko.restaurantordering.Model.User;
import com.isaiko.restaurantordering.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.isaiko.restaurantordering.Activity.MainActivity.headerCartNotification;

public class CartFragment extends Fragment {
    CartRecyclerViewAdapter cartAdapter;
    @BindView(R.id.rv_cart)
    RecyclerView cartRecyclerView;
    @BindView(R.id.btn_clear_cart)
    Button clearCartButton;
    @BindView(R.id.tv_cart_total)
    TextView cartTotalTextView;
    @BindView(R.id.tv_order_time)
    TextView orderTimeTextView;
    @BindView(R.id.btn_checkout)
    Button checkoutButton;
    @BindView(R.id.spinner_order_type)
    Spinner orderTypeSpinner;
    String selectedTime;
    static View mainView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cart,container,false);
        mainView = view;
        ButterKnife.bind(this,view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        cartRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(cartRecyclerView.getContext(),
                layoutManager.getOrientation());
        cartRecyclerView.addItemDecoration(dividerItemDecoration);
        cartAdapter = new CartRecyclerViewAdapter(getActivity(), Cart.getInstance().getCartItemsList());
        cartRecyclerView.setAdapter(cartAdapter);
        FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Cart retrievedCart = dataSnapshot.getValue(Cart.class);
                    Cart.setCurrentCart(retrievedCart);
                    if(Cart.getInstance().getmItems().size()>0) {
                        headerCartNotification.setVisibility(View.VISIBLE);
                        headerCartNotification.setText(String.valueOf(Cart.getInstance().getmItems().size()));
                    }
                }else{
                    cartAdapter.itemsList.clear();
                    cartAdapter.notifyDataSetChanged();
                    Cart.getInstance().clearCurrentCart();
                    cartTotalTextView.setText(String.valueOf(Cart.getInstance().getCartValue()));
                    headerCartNotification.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        clearCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Remove")
                        .setMessage("Are you sure you want to clear your cart?")
                        .setPositiveButton(R.string.label_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Cart.getInstance().getmItems().clear();
                                FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getUid()).setValue(Cart.getInstance());
                                cartAdapter.itemsList.clear();
                                cartAdapter.notifyDataSetChanged();
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

        cartTotalTextView.setText(String.valueOf(Cart.getInstance().getCartValue()));

        orderTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY,selectedHour);
                        calendar.set(Calendar.MINUTE,selectedMinute);
                        String date = new SimpleDateFormat("hh:mm a").format(calendar.getTime());
                        selectedTime = date;
                        orderTimeTextView.setText(selectedTime);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForms()){
                    Order newOrder = new Order();
                    newOrder.setmOrderItems(Cart.getInstance().getCartItemsList());
                    newOrder.setmOrderType(orderTypeSpinner.getSelectedItem().toString());
                    newOrder.setmOrderTime(selectedTime);
                    newOrder.setmOrderTotalToPay(Cart.getInstance().getCartValue());
                    newOrder.setOrderUserId(FirebaseAuth.getInstance().getUid());
                    newOrder.setItemBranch(User.getInstance().getUserSelectedBranch());
                    CheckoutDialog dialog = new CheckoutDialog();
                    Bundle args = new Bundle();
                    args.putSerializable("passedOrder",newOrder);
                    dialog.setArguments(args);
                    dialog.show(getActivity().getSupportFragmentManager(),"Checkout Dialog");
                }
            }
        });
        return view;
    }

    private boolean validateForms(){

        if(Cart.getInstance().getCartItemsList().isEmpty()){
            Snackbar.make(mainView,"Empty Cart",Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(selectedTime) && selectedTime == null){
            Snackbar.make(mainView,"Select a proper time",Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static void updateCartTotal(){
        TextView priceTextView = mainView.findViewById(R.id.tv_cart_total);
        priceTextView.setText(String.valueOf(Cart.getInstance().getCartValue()));
    }
}
