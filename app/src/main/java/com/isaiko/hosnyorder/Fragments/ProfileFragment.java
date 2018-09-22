package com.isaiko.hosnyorder.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.isaiko.hosnyorder.Model.User;
import com.isaiko.hosnyorder.R;
import com.isaiko.hosnyorder.Utils.CircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    @BindView(R.id.iv_profile_picture)
    ImageView profileImageView;
    @BindView(R.id.tv_username)
    TextView usernameTextView;
    @BindView(R.id.tv_phone_number)
    TextView phonenumberTextView;
    @BindView(R.id.tv_city)
    TextView cityTextView;
    @BindView(R.id.tv_address)
    TextView addressTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        ButterKnife.bind(this,view);
        User currentUser = User.getInstance();
        if(currentUser.getmProfilePicture()!=null && !currentUser.getmProfilePicture().equals("")) {
            Picasso.with(getContext()).load(currentUser.getmProfilePicture())
                    .transform(new CircleTransform()).placeholder(R.drawable.ic_avatar_placeholder)
                    .into(profileImageView);
        }
        usernameTextView.setText(currentUser.getmUserName());
        phonenumberTextView.setText(currentUser.getmPhoneNumber());
        cityTextView.setText(currentUser.getmUserAddress().getmCity());
        addressTextView.setText(currentUser.getmUserAddress().addressToString());
        return view;
    }
}
