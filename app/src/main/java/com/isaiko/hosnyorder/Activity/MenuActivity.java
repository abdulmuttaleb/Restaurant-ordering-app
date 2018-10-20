package com.isaiko.hosnyorder.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.isaiko.hosnyorder.Adapter.MenuRecyclerViewAdapter;
import com.isaiko.hosnyorder.Model.Item;
import com.isaiko.hosnyorder.Model.User;
import com.isaiko.hosnyorder.R;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {
    @BindView(R.id.rv_menu_items)
    RecyclerView menuItemsRecyclerView;
    @BindView(R.id.spinner_filter_category)
    Spinner categoriesSpinner;
    ArrayAdapter<String> categoriesAdapter;
    MenuRecyclerViewAdapter menuItemRecyclerViewAdapter;
    Query menuDatabaseRef;
    List<Item> menuItemsList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        if(User.getInstance().getUserSelectedBranch()==null){
            Toast.makeText(this, "Select a branch in profile settings to retrieve a menu", Toast.LENGTH_SHORT).show();
        }else{
            if(User.getInstance().getUserSelectedBranch().equals("Bahary")
                    || User.getInstance().getUserSelectedBranch().equals("Mandara")
                    ||User.getInstance().getUserSelectedBranch().equals("Downtown")){
                menuDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Menu").orderByChild("itemMenu").equalTo("Alexandria");
            }else if(User.getInstance().getUserSelectedBranch().equals("Moqattam")
                    || User.getInstance().getUserSelectedBranch().equals("Masr El Gedida")
                    ||User.getInstance().getUserSelectedBranch().equals("Madinet El Nasr")){
                menuDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Menu").orderByChild("itemMenu").equalTo("Cairo");
            }else if(User.getInstance().getUserSelectedBranch().equals("KSA")){
                menuDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Menu").orderByChild("itemMenu").equalTo("KSA");
            }
            fetchMenuItems();
        }
        menuDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Menu");
        menuItemsList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        menuItemsRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(menuItemsRecyclerView.getContext(),
                layoutManager.getOrientation());
        menuItemsRecyclerView.addItemDecoration(dividerItemDecoration);
        menuItemRecyclerViewAdapter = new MenuRecyclerViewAdapter(this, menuItemsList);
        menuItemsRecyclerView.setAdapter(menuItemRecyclerViewAdapter);
        categoriesAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_item, getResources().getStringArray(R.array.filter_categories)){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        categoriesAdapter.setDropDownViewResource(R.layout.custom_spinner_item);
        categoriesSpinner.setAdapter(categoriesAdapter);
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                     String selectedFilter = parent.getItemAtPosition(position).toString();
                     menuItemRecyclerViewAdapter.getFilter().filter(selectedFilter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    }

    private void fetchMenuItems(){
        menuDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Item> fetchedMenu = new ArrayList<>();
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    Item newItem = child.getValue(Item.class);
                    fetchedMenu.add(newItem);
                }
                menuItemsList.clear();
                menuItemsList.addAll(fetchedMenu);
                menuItemRecyclerViewAdapter.notifyDataSetChanged();
                String passedFilter = getIntent().getExtras().getString("category","All");
                Toast.makeText(MenuActivity.this, passedFilter, Toast.LENGTH_SHORT).show();
                int position = categoriesAdapter.getPosition(passedFilter);
                categoriesSpinner.setSelection(position);
                menuItemRecyclerViewAdapter.getFilter().filter(passedFilter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
