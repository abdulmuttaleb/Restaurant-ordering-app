package com.isaiko.restaurantordering.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isaiko.restaurantordering.Model.User;
import com.isaiko.restaurantordering.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LandingActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    private String fbUserName, fbUserEmail;
    LoginButton loginButton;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabaseRef;

    @BindView(R.id.til_username)
    TextInputLayout emailTextInputLayout;
    @BindView(R.id.til_password)
    TextInputLayout passwordTextInputLayout;
    @BindView(R.id.et_username)
    TextInputEditText emailEditText;
    @BindView(R.id.et_password)
    TextInputEditText passwordEditText;
    @BindView(R.id.iv_facebook_login)
    ImageView facebookLoginImageView;
    @BindView(R.id.tv_sign_up)
    TextView signupTextView;
    @BindView(R.id.btn_login)
    Button userLoginButton;
    @BindView(R.id.tv_reset_password)
    TextView resetPasswordTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);

        loginButton = findViewById(R.id.login_button);
        mAuth = FirebaseAuth.getInstance();
        mUsersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));

        facebookLoginImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                setFacebookData(loginResult);
                Toast.makeText(LandingActivity.this, "Success", Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });

        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(registerIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });



        resetPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder resetPasswordDialog = new AlertDialog.Builder(LandingActivity.this);
                resetPasswordDialog.setTitle(R.string.label_reset_password)
                        .setMessage("Enter your mail to receive a link to reset your password!");
                final EditText input = new EditText(LandingActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                resetPasswordDialog.setView(input)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(!input.getText().toString().isEmpty()){
                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                    String emailAddress = input.getText().toString();
                                    auth.sendPasswordResetEmail(emailAddress).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(LandingActivity.this, "Reset password mail was sent to your Email address!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(LandingActivity.this, "Failed to send reset link to your mail!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }else{
                                    Toast.makeText(LandingActivity.this, "Enter a valid email address!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                resetPasswordDialog.create().show();
            }
        });

    }

    @OnClick(R.id.btn_login)
    public void Login(){
        if (validateInputFields()) {
            mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                mUsersDatabaseRef.child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        User retrievedUser = dataSnapshot.getValue(User.class);
                                        User.setCurrentUser(retrievedUser);
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                                Intent intent = new Intent(LandingActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setFacebookData(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            fbUserEmail = response.getJSONObject().getString("email");
                            fbUserName = response.getJSONObject().getString("name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        showProgressDialog();
        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            isUserNew(uid, fbUserEmail, fbUserName);
                            //Assign user values to user singleton
                            mUsersDatabaseRef.child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User retrievedUser = dataSnapshot.getValue(User.class);
                                    User.setCurrentUser(retrievedUser);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                            //Navigate to main activity after successful login
                            Intent intent = new Intent(LandingActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LandingActivity.this, "Login failed!",
                                    Toast.LENGTH_SHORT).show();
                            LoginManager.getInstance().logOut();
                        }
                        hideProgressDialog();
                    }
                });
    }

    private void isUserNew(final String uid, final String email, final String username) {

        mUsersDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child(uid).exists()) {
                    FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                    writeNewUser(uid, username, email);
                    LoginManager.getInstance().logOut();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void writeNewUser(String userId, String name, String email) {
        String profilePicUri = Profile.getCurrentProfile().getProfilePictureUri(200, 200).toString();
        if (profilePicUri == null) {
            profilePicUri = "";
        }
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

    private boolean validateInputFields(){
        boolean result = true;
        if(TextUtils.isEmpty(emailEditText.getText())){
            emailTextInputLayout.setError("Empty username field!");
            emailTextInputLayout.setErrorEnabled(true);
            result = false;
        }else{
            emailTextInputLayout.setErrorEnabled(false);
        }
        if(TextUtils.isEmpty(passwordEditText.getText())){
            passwordTextInputLayout.setError("Empty password field!");
            passwordTextInputLayout.setErrorEnabled(true);
            result = false;
        }else{
            passwordTextInputLayout.setErrorEnabled(false);
        }
        return result;
    }
}
