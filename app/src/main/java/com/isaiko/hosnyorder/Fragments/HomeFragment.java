package com.isaiko.hosnyorder.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.isaiko.hosnyorder.Adapter.CategoryRecyclerViewAdapter;
import com.isaiko.hosnyorder.Model.UI.CategoryUI;
import com.isaiko.hosnyorder.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.rv_category)
    RecyclerView categoryRecyclerView;
    CategoryRecyclerViewAdapter categoryAdapter;
    List<CategoryUI> categoryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);
        populateCategoryView();
        return view;
    }

    private void populateCategoryView(){
        categoryList = new ArrayList<>();
        CategoryUI grillsCategory = new CategoryUI("Grills",R.drawable.grilled, R.color.colorWhite);
        CategoryUI ma7shyCategory = new CategoryUI("Ma7shy",R.drawable.ma7shy, R.color.colorWhite);
        CategoryUI poultryCategory = new CategoryUI("Poultry",R.drawable.grilled_poultry, R.color.colorWhite);
        CategoryUI soupsCategory = new CategoryUI("Soups",R.drawable.soup, R.color.colorWhite);
        CategoryUI chefDishesCategory = new CategoryUI("Chef's Dishes",R.drawable.chef_dishes, R.color.colorWhite);
        CategoryUI pastriesCategory = new CategoryUI("Pastries",R.drawable.pastries, R.color.colorWhite);
        categoryList.add(grillsCategory);
        categoryList.add(ma7shyCategory);
        categoryList.add(poultryCategory);
        categoryList.add(soupsCategory);
        categoryList.add(chefDishesCategory);
        categoryList.add(pastriesCategory);
        categoryAdapter = new CategoryRecyclerViewAdapter(getActivity().getApplicationContext(),categoryList);
        GridLayoutManager layoutManager
                = new GridLayoutManager(getActivity().getApplicationContext(),2);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);

    }
}
