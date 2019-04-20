package com.isaiko.restaurantordering.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.isaiko.restaurantordering.Activity.ProfileSettingsActivity;
import com.isaiko.restaurantordering.Adapter.CategoryRecyclerViewAdapter;
import com.isaiko.restaurantordering.Model.UI.CategoryUI;
import com.isaiko.restaurantordering.Model.User;
import com.isaiko.restaurantordering.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.rv_category)
    RecyclerView categoryRecyclerView;
    @BindView(R.id.tv_current_branch)
    TextView currentBranchTextView;
    CategoryRecyclerViewAdapter categoryAdapter;
    List<CategoryUI> categoryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);

        if(User.getInstance().getUserSelectedBranch()==null){
            String string = "Current Branch: Please select a branch from user settings";
            currentBranchTextView.setText(string);
        }else if(User.getInstance().getUserSelectedBranch().isEmpty()) {
            String string = "Current Branch: Please select a branch from user settings";
            currentBranchTextView.setText(string);
        }else{
            String string = "Current Branch: "+User.getInstance().getUserSelectedBranch();
            currentBranchTextView.setText(string);
        }

        if(User.getInstance().getmUserAddress() == null){
            Toast.makeText(getActivity(), "Address isn't set!", Toast.LENGTH_SHORT).show();
            Intent profileSettings = new Intent(getActivity().getApplicationContext(), ProfileSettingsActivity.class);
            startActivity(profileSettings);
        }
        populateCategoryView();
        return view;
    }

    private void populateCategoryView(){
        categoryList = new ArrayList<>();
        CategoryUI grillsCategory = new CategoryUI(getResources().getString(R.string.home_category_grills),R.drawable.grilled, R.color.colorWhite);
        CategoryUI ma7shyCategory = new CategoryUI(getResources().getString(R.string.home_category_ma7ashy),R.drawable.ma7shy, R.color.colorWhite);
        CategoryUI poultryCategory = new CategoryUI(getResources().getString(R.string.home_category_poultry),R.drawable.grilled_poultry, R.color.colorWhite);
        CategoryUI soupsCategory = new CategoryUI(getResources().getString(R.string.home_category_soups),R.drawable.soup, R.color.colorWhite);
        CategoryUI chefDishesCategory = new CategoryUI(getResources().getString(R.string.home_category_chef_dishes),R.drawable.chef_dishes, R.color.colorWhite);
        CategoryUI pastriesCategory = new CategoryUI(getResources().getString(R.string.home_category_pastries),R.drawable.pastries, R.color.colorWhite);
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
