package com.example.sabonit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class UserLoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private boolean loginState = false;
    private FirebaseFirestore db;
    TextView helloTitle;
    ImageView profileImage;
    Button continueLogin;
    String name;
    String uid;

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
        db = FirebaseFirestore.getInstance();
        if (bundle == null)
        {
            createSignInIntent();
        }

        ImageView refillImage = findViewById(R.id.userlogin_gif_image);
        Glide.with(this)
                .load("https://gifimage.net/wp-content/uploads/2018/06/water-drops-gif-11.gif")
                .into(refillImage);

        Animation animation = new TranslateAnimation(-500, 1500,0, 0);
        animation.setDuration(10000);
        animation.setRepeatCount(5);
        animation.setFillAfter(true);
        refillImage.startAnimation(animation);
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
                    name = user.getDisplayName();
                    String email = user.getEmail();
                    Picasso.get().load(user.getPhotoUrl()).into(profileImage);
                    // Check if user's email is verified
                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getIdToken() instead.
                    uid = user.getUid();
                    updateAccount();
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

    private void updateAccount()
    {
        DocumentReference accountsRef = db.collection("Accounts").document(uid);
        accountsRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("SearchAccountIdSucess", "DocumentSnapshot data: " + document.getData());
                        setAccountData(document);
                    } else {
                        Log.d("SearchAccountIdFailed", "No such document");
                        writeNewAccount();
                    }
                    displayMessageIfCartIsEmpty();
                } else {
                    Log.d("SearchInTheDBFailed", "get failed with ", task.getException());
                }
            }
        });
    }


    private void setAccountData(DocumentSnapshot document)
    {
        Account currentAccount = document.toObject(Account.class);
        Account.setCurrentAccount(currentAccount);
    }

    private void writeNewAccount()
    {
        Account accountToAdd = new Account(name, uid);
        db.collection("Accounts").document(uid).set(accountToAdd);
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

    private void showHowItWorksDialog() {
        FragmentManager fm = getSupportFragmentManager();
        HowItWorksDialogFragment howItWorksDialogFragment = HowItWorksDialogFragment.newInstance();
        howItWorksDialogFragment.show(fm, "how_it_works");
    }

    public void popHowItWorks(View view) {
        showHowItWorksDialog();
    }

    private void displayMessageIfCartIsEmpty()
    {
        if (! Account.getCurrentAccount().getCart().isCartEmpty())
        {
            FragmentManager fm = getSupportFragmentManager();
            CartIsntEmptyDialogFragment cartIsntEmptyDialogFragment = CartIsntEmptyDialogFragment.newInstance();
            cartIsntEmptyDialogFragment.show(fm, "cart_isnt_empty");
        }

    }

}
