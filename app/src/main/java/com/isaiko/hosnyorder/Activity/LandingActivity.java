package com.isaiko.hosnyorder.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.isaiko.hosnyorder.Model.User;
import com.isaiko.hosnyorder.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LandingActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    private String fbUserName, fbUserEmail;
    LoginButton loginButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabaseRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        loginButton = findViewById(R.id.login_button);
        mAuth = FirebaseAuth.getInstance();
        mUsersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
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
}
