package com.isaiko.hosnyorder.Fragments;

import android.graphics.Color;
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
        CategoryUI foodCategory = new CategoryUI("Food",R.drawable.ic_food_black_24dp, R.color.foodCategory);
        CategoryUI drinksCategory = new CategoryUI("Hot Drinks",R.drawable.ic_hot_drinks_black_24dp, R.color.hotDrinksCategory);
        CategoryUI coldDrinksCategory = new CategoryUI("Cold Drinks",R.drawable.ic_cold_drinks_black_24dp, R.color.com_facebook_button_background_color_pressed);
        categoryList.add(foodCategory);
        categoryList.add(drinksCategory);
        categoryList.add(coldDrinksCategory);
        categoryAdapter = new CategoryRecyclerViewAdapter(getActivity().getApplicationContext(),categoryList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);

    }
}
