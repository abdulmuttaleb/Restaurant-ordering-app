package com.isaiko.hosnyorder.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isaiko.hosnyorder.Model.User;
import com.isaiko.hosnyorder.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity{

    @BindView(R.id.til_username)
    TextInputLayout usernameTextInputLayout;
    @BindView(R.id.til_mail)
    TextInputLayout mailTextInputLayout;
    @BindView(R.id.til_password)
    TextInputLayout passwordTextInputLayout;
    @BindView(R.id.til_confirm_password)
    TextInputLayout confirmPasswordTextInputLayout;
    @BindView(R.id.et_username)
    TextInputEditText usernameEditText;
    @BindView(R.id.et_mail)
    TextInputEditText mailEditText;
    @BindView(R.id.et_password)
    TextInputEditText passwordEditText;
    @BindView(R.id.et_confirm_password)
    TextInputEditText confirmPasswordEditText;
    @BindView(R.id.btn_signup)
    Button signupButton;
    @BindView(R.id.tv_login)
    TextView loginTextView;

    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabaseRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mUsersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    @OnClick(R.id.btn_signup)
    public void SignUp(){
        if(validateInputFields()) {
            showProgressDialog();
            mAuth.createUserWithEmailAndPassword(mailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                String uid = user.getUid();
                                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                                writeNewUser(uid, usernameEditText.getText().toString(), user.getEmail());
                                //Navigate to main activity after successful login
                                Intent intent = new Intent(SignUpActivity.this, LandingActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            hideProgressDialog();
                        }
                    });
        }
    }

    @OnClick(R.id.tv_login)
    public void ActivityLogIn(){
        Intent loginIntent = new Intent(getApplicationContext(), LandingActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }
    private boolean validateInputFields(){
        boolean result = true;
        if(TextUtils.isEmpty(usernameEditText.getText())){
            usernameTextInputLayout.setError("please type a username!");
            usernameTextInputLayout.setErrorEnabled(true);
            result = false;
        }else{
            usernameTextInputLayout.setErrorEnabled(false);
        }
        if(TextUtils.isEmpty(mailEditText.getText())){
            mailTextInputLayout.setError("please type a mail!");
            mailTextInputLayout.setErrorEnabled(true);
            result = false;
        }else{
            mailTextInputLayout.setErrorEnabled(false);
        }
        if(TextUtils.isEmpty(passwordEditText.getText())){
            passwordTextInputLayout.setError("please type a password!");
            passwordTextInputLayout.setErrorEnabled(true);
            result = false;
        }else{
            passwordTextInputLayout.setErrorEnabled(false);
        }
        if(TextUtils.isEmpty(confirmPasswordEditText.getText())){
            confirmPasswordTextInputLayout.setError("please confirm your password!");
            confirmPasswordTextInputLayout.setErrorEnabled(true);
            result = false;
        }else{
            if(!confirmPasswordEditText.getText().toString().equals(passwordEditText.getText().toString())){
                confirmPasswordTextInputLayout.setError("doesn't match first password");
                confirmPasswordTextInputLayout.setErrorEnabled(true);
                result = false;
            }else{
                confirmPasswordTextInputLayout.setErrorEnabled(false);
            }
        }
        return result;
    }

    private void writeNewUser(String userId, String name, String email) {
        String profilePicUri = "";
        User newUser = new User(name, email, profilePicUri);
        mUsersDatabaseRef.child(userId).setValue(newUser);
        Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT);
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
