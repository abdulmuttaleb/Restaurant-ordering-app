package com.isaiko.hosnyorder.Fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isaiko.hosnyorder.Adapter.PastOrderRecyclerViewAdapter;
import com.isaiko.hosnyorder.Model.Order;
import com.isaiko.hosnyorder.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PastOrdersFragment extends Fragment {
    @BindView(R.id.rv_past_orders)
    RecyclerView pastOrderRecyclerView;
    PastOrderRecyclerViewAdapter pastOrdersAdapter;
    DatabaseReference userOrdersRef;
    List<Order> ordersList= new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_orders,container,false);
        ButterKnife.bind(this,view);
        userOrdersRef = FirebaseDatabase.getInstance().getReference().child("UserOrders").child(FirebaseAuth.getInstance().getUid());
        fetchUserOrders();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        pastOrderRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(pastOrderRecyclerView.getContext(),
                layoutManager.getOrientation());
        pastOrderRecyclerView.addItemDecoration(dividerItemDecoration);
        pastOrdersAdapter = new PastOrderRecyclerViewAdapter(getActivity(),ordersList);
        pastOrderRecyclerView.setAdapter(pastOrdersAdapter);
        return view;
    }

    private void fetchUserOrders(){
        userOrdersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Order> tempList = new ArrayList<>();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Order order = data.getValue(Order.class);
                    tempList.add(order);
                }
                ordersList.clear();
                ordersList.addAll(tempList);
                pastOrdersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
