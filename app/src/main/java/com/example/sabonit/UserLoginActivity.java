package com.example.sabonit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class UserLoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private boolean loginState = false;
    TextView helloTitle;
    ImageView profileImage;
    Button continueLogin;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        helloTitle = findViewById(R.id.hello_text);
        profileImage = findViewById(R.id.profile_image);
        continueLogin = findViewById(R.id.continue_or_login);
        continueLogin.setText("Continue");
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
        {
            createSignInIntent();
        }
    }

    public void createSignInIntent() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Picasso.get().load(user.getPhotoUrl()).into(profileImage);
                    // Check if user's email is verified
                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getIdToken() instead.
                    String uid = user.getUid();
                    helloTitle.setText("Welcome, " + name + "!");
                }
                // ...
            } else {
                logOut();
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            if (bundle.getInt("Logout") == 0) {
                getIntent().removeExtra("Logout");
                logOut();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void LogIn() {
        createSignInIntent();
        continueLogin.setText("Continue");
        loginState = false;
    }

    @SuppressLint("SetTextI18n")
    public void logOut() {
        FirebaseAuth.getInstance().signOut();
        helloTitle.setText("You have been logged out");
        continueLogin.setText("Log In");
        loginState = true;
        profileImage.setImageResource(R.drawable.ic_guest_user);
    }

    public void continueOrLogin(View view) {
        if (loginState) {
            LogIn();
        } else {
            Intent intent = new Intent(this, CategoriesActivity.class);
            startActivity(intent);
        }
    }

}
