package com.isaiko.hosnyorder.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isaiko.hosnyorder.Model.User;
import com.isaiko.hosnyorder.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileSettingsActivity extends AppCompatActivity {

    @BindView(R.id.ll_phone_number)
    LinearLayout phoneNumberLayout;
    @BindView(R.id.ll_city)
    LinearLayout cityLayout;
    @BindView(R.id.ll_area)
    LinearLayout areaLayout;
    @BindView(R.id.ll_building)
    LinearLayout buildingLayout;
    @BindView(R.id.ll_floor)
    LinearLayout floorLayout;
    @BindView(R.id.ll_apart_num)
    LinearLayout apartmentLayout;
    @BindView(R.id.tv_username)
    TextView usernameTextView;
    @BindView(R.id.tv_mail)
    TextView mailTextView;
    @BindView(R.id.tv_phone_number)
    TextView phoneNumberTextView;
    @BindView(R.id.tv_city)
    TextView cityTextView;
    @BindView(R.id.tv_area)
    TextView areaTextView;
    @BindView(R.id.tv_building)
    TextView buildingTextView;
    @BindView(R.id.tv_floor)
    TextView floorTextView;
    @BindView(R.id.tv_apart_number)
    TextView apartmentTextView;
    @BindView(R.id.tv_branch)
    TextView branchTextView;
    DatabaseReference userDatabaseRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        ButterKnife.bind(this);
        final User currentUser = User.getInstance();
        initValues(currentUser);
        userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());

        phoneNumberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeDialog(getResources().getString(R.string.label_change_number),userDatabaseRef.child("mPhoneNumber"),true);
            }
        });

        cityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeDialog(getResources().getString(R.string.label_change_city),userDatabaseRef.child("mUserAddress").child("mCity"),false);
            }
        });

        areaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeDialog(getResources().getString(R.string.label_change_area),userDatabaseRef.child("mUserAddress").child("mArea"),false);
            }
        });

        buildingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeDialog(getResources().getString(R.string.label_change_building),userDatabaseRef.child("mUserAddress").child("mBuilding"),true);
            }
        });

        floorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeDialog(getResources().getString(R.string.label_change_floor),userDatabaseRef.child("mUserAddress").child("mFloor"),true);
            }
        });

        apartmentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeDialog(getResources().getString(R.string.label_change_apart_number),userDatabaseRef.child("mUserAddress").child("mApartmentNumber"),true);
            }
        });

        branchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ProfileSettingsActivity.this);
                builder.setTitle("Choose Branch")
                        .setSingleChoiceItems(R.array.branches,-1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.label_change), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListView lw = ((android.app.AlertDialog)dialog).getListView();
                                int position = lw.getCheckedItemPosition();
                                String selectedBranch = lw.getItemAtPosition(position).toString();
                                userDatabaseRef.child("userSelectedBranch").setValue(selectedBranch);
                                Toast.makeText(ProfileSettingsActivity.this, "Branch was changed succcessfully!", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton(getResources().getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                android.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
       userDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User retrievedUser = dataSnapshot.getValue(User.class);
                User.setCurrentUser(retrievedUser);
                initValues(retrievedUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void initValues(User currentUser){
        usernameTextView.setText(currentUser.getmUserName());
        mailTextView.setText(currentUser.getmMail());
        phoneNumberTextView.setText(String.valueOf(currentUser.getmPhoneNumber()));
        if(currentUser.getmUserAddress()!=null) {
            cityTextView.setText(currentUser.getmUserAddress().getmCity());
            areaTextView.setText(currentUser.getmUserAddress().getmArea());
            buildingTextView.setText(String.valueOf(currentUser.getmUserAddress().getmBuilding()));
            floorTextView.setText(String.valueOf(currentUser.getmUserAddress().getmFloor()));
            apartmentTextView.setText(String.valueOf(currentUser.getmUserAddress().getmApartmentNumber()));
        }
        if(currentUser.getUserSelectedBranch()!=null){
            branchTextView.setText(currentUser.getUserSelectedBranch());
        }
    }
    private void showChangeDialog(String title, final DatabaseReference ref, final boolean number){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        if(number){
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        alertDialog.setView(input);
        alertDialog.setPositiveButton(getResources().getString(R.string.label_change), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!TextUtils.isEmpty(input.getText().toString()))
                {
                    ref.setValue(input.getText().toString());
                    Toast.makeText(ProfileSettingsActivity.this, getResources().getString(R.string.label_changed_successfully), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ProfileSettingsActivity.this,  getResources().getString(R.string.label_invalid_input), Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton(getResources().getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }

}
