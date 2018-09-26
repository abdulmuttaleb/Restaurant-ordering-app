package com.isaiko.hosnyorder.Fragments;

import android.os.Bundle;
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
import com.isaiko.hosnyorder.Adapter.PromotionsRecyclerViewAdapter;
import com.isaiko.hosnyorder.Model.Promotion;
import com.isaiko.hosnyorder.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PromotionsFragment extends Fragment {
    @BindView(R.id.rv_promotions)
    RecyclerView promotionsRecyclerView;
    PromotionsRecyclerViewAdapter promotionsAdapter;
    DatabaseReference promotionsRef;
    List<Promotion> promotionsList= new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotions,container,false);
        ButterKnife.bind(this,view);
        promotionsRef = FirebaseDatabase.getInstance().getReference().child("Promotions");
        fetchPromotions();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        promotionsRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(promotionsRecyclerView.getContext(),
                layoutManager.getOrientation());
        promotionsRecyclerView.addItemDecoration(dividerItemDecoration);
        promotionsAdapter = new PromotionsRecyclerViewAdapter(getActivity(),promotionsList);
        promotionsRecyclerView.setAdapter(promotionsAdapter);
        return view;
    }

    private void fetchPromotions(){
        promotionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Promotion> tempList = new ArrayList<>();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Promotion promotion = data.getValue(Promotion.class);
                    tempList.add(promotion);
                }
                promotionsList.clear();
                promotionsList.addAll(tempList);
                promotionsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
