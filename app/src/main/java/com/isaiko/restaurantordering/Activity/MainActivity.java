package com.isaiko.restaurantordering.Activity;

import android.Manifest;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isaiko.restaurantordering.Fragments.CartFragment;
import com.isaiko.restaurantordering.Fragments.ContactUsFragment;
import com.isaiko.restaurantordering.Fragments.HomeFragment;
import com.isaiko.restaurantordering.Fragments.PastOrdersFragment;
import com.isaiko.restaurantordering.Fragments.ProfileFragment;
import com.isaiko.restaurantordering.Fragments.PromotionsFragment;
import com.isaiko.restaurantordering.Fragments.SettingsFragment;
import com.isaiko.restaurantordering.Model.Cart;
import com.isaiko.restaurantordering.Model.User;
import com.isaiko.restaurantordering.R;
import com.isaiko.restaurantordering.Utils.CircleTransform;
import com.isaiko.restaurantordering.Utils.LocaleHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ProgressDialog mProgressDialog;

    @BindView(R.id.drawer_main)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nv_main)
    NavigationView mNavigationView;
    @BindView(R.id.frame_main)
    FrameLayout mainFrame;
    View navigationDrawerHeader;
    public static TextView headerUsernameTextView, headerMailTextView,headerCartNotification;
    ImageView headerProfileImageView;
    ImageView headerCartImageView;
    private ActionBarDrawerToggle mToggle;
    DatabaseReference mUsersDatabaseRef;
    DatabaseReference mCartDatabaseRef;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        mNavigationView.setNavigationItemSelectedListener(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if(user.isEmailVerified()) {
                mAuth = FirebaseAuth.getInstance();
                mUsersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
                mCartDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Cart").child(mAuth.getUid());
                mUsersDatabaseRef.child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User retrievedUser = dataSnapshot.getValue(User.class);
                        User.setCurrentUser(retrievedUser);
                        mCartDatabaseRef.addValueEventListener(new ValueEventListener() {
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
                                    headerCartNotification.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        initNavigationDrawer();
                        if(savedInstanceState == null){
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,new HomeFragment()).commit();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }else
            {
                Toast.makeText(this, "Please verify your email address!", Toast.LENGTH_SHORT).show();
                openLandingActivity();
            }
        }else{
            //Open Landing Activity
            openLandingActivity();
        }
    }

    private void openLandingActivity(){
        Intent landingIntent = new Intent(this, LandingActivity.class);
        landingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        landingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        landingIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(landingIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,new HomeFragment()).addToBackStack(null).commit();
                closeDrawer();
                return true;
            case R.id.action_promotions:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,new PromotionsFragment()).addToBackStack(null).commit();
                closeDrawer();
                return true;
            case R.id.action_past_orders:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,new PastOrdersFragment()).addToBackStack(null).commit();
                closeDrawer();
                return true;
            case R.id.action_contact_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,new ContactUsFragment()).addToBackStack(null).commit();
                closeDrawer();
                return true;
            case R.id.action_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,new SettingsFragment()).addToBackStack(null).commit();
                closeDrawer();
                return true;
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                User.getInstance().clearUser();
                openLandingActivity();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fm = getFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    private void initNavigationDrawer(){
        navigationDrawerHeader = mNavigationView.findViewById(R.id.nv_header);
        headerUsernameTextView = navigationDrawerHeader.findViewById(R.id.tv_username);
        headerMailTextView = navigationDrawerHeader.findViewById(R.id.tv_email);
        headerProfileImageView = navigationDrawerHeader.findViewById(R.id.iv_profile_picture);
        headerCartImageView = navigationDrawerHeader.findViewById(R.id.iv_cart);
        headerCartNotification = navigationDrawerHeader.findViewById(R.id.tv_cart_notification);
        headerCartNotification.setVisibility(View.GONE);
        headerUsernameTextView.setText(User.getInstance().getmUserName());
        headerMailTextView.setText(User.getInstance().getmMail());
        if(User.getInstance().getmProfilePicture()!=null && !User.getInstance().getmProfilePicture().equals("")) {
            Picasso.with(getApplicationContext()).load(User.getInstance().getmProfilePicture())
                    .transform(new CircleTransform()).placeholder(R.drawable.ic_avatar_placeholder)
                    .into(headerProfileImageView);
        }

        headerProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mDrawerLayout,"Profile clicked",Snackbar.LENGTH_SHORT).show();
                closeDrawer();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,new ProfileFragment()).addToBackStack(null).commit();
            }
        });

        headerCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,new CartFragment()).addToBackStack(null).commit();
            }
        });

        if(Cart.getInstance()!=null){
            if(Cart.getInstance().getmItems().size()>0) {
                headerCartNotification.setVisibility(View.VISIBLE);
                headerCartNotification.setText(String.valueOf(Cart.getInstance().getmItems().size()));
            }
        }
    }
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getResources().getString(R.string.label_loading));
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void closeDrawer(){
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    public void callClick(View view){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
        }else{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + ((TextView) view).getText().toString()));
            startActivity(intent);
        }
    }
}
