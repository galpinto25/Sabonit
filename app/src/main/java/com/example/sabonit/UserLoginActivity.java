/* ********* Imports: ********* */
package com.example.sabonit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the user login screen.
 */
public class UserLoginActivity extends AppCompatActivity
{
    /* ********* Constants: ********* */
    // Arbitrary value for the login launching
    private static final int RC_SIGN_IN = 123;

    // Pointer of the database
    private FirebaseFirestore db;

    /* ********* Attributes: ********* */
    // Indicates if there is no logged in user, for choosing if start the login process
    private boolean loginState = false;
    private TextView helloTitle;
    private ImageView profileImage;
    private Button continueLogin;
    private String name;
    private String uid;
    private ImageView info;
    private ImageView howItWorks;

    /* ********* Functions: ********* */
    /**
     * Displays the user login activity.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        defineButtons();
        Bundle bundle = getIntent().getExtras();
        db = FirebaseFirestore.getInstance();
        info = findViewById(R.id.info);
        howItWorks = findViewById(R.id.how_it_works_title);
        if (bundle == null)
        {
            createSignInIntent();
        }
        doAnimation();
    }

    /**
     * defining the buttons of the function
     */
    @SuppressLint("SetTextI18n")
    private void defineButtons()
    {
        setContentView(R.layout.activity_user_login);
        helloTitle = findViewById(R.id.hello_text);
        profileImage = findViewById(R.id.profile_image);
        continueLogin = findViewById(R.id.continue_or_login);
        continueLogin.setText("Continue");
    }

    /**
     * doing the animation of the drop gif
     */
    private void doAnimation()
    {
        // Presents the water drop gif on the screen and animate it to walk
        ImageView refillImage = findViewById(R.id.userlogin_gif_image);
        Glide.with(this)
                .load("https://gifimage.net/wp-content/uploads/2018/06/" +
                        "water-drops-gif-11.gif")
                .into(refillImage);
        Animation animation = new TranslateAnimation(-500, 1500,0,
                0);
        animation.setDuration(10000);
        animation.setRepeatCount(5);
        animation.setFillAfter(true);
        refillImage.startAnimation(animation);
    }

    /**
     * sign in intent with Google authentication.
     */
    private void createSignInIntent()
    {
        // Chooses authentication providers
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Creates and launches sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    /**
     * Manages the sign in activity - checks whether the sign in succeeded or not.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // Checks whether the request is to sign in
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK)
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null)
            {
                // name, email address, and profile photo Url
                name = user.getDisplayName();
                String email = user.getEmail();
                Picasso.get().load(user.getPhotoUrl()).into(profileImage);
                uid = user.getUid();
                updateAccount();
                helloTitle.setText("Welcome, " + name + "!");
            }
        }
        else
        {
            // If the sign in failed
            logOut();
        }
    }

    /**
     * After the successful sign in this function updates the account in the database, if it exists.
     * else, creates new account in the database.
     */
    private void updateAccount()
    {

        DocumentReference accountsReference = db.collection("Accounts").document(uid);
        accountsReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task)
            {
                if (task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if (Objects.requireNonNull(document).exists())
                    {
                        Log.d("SearchAccountIdSucess", "DocumentSnapshot data: " +
                                document.getData());
                        setAccountData(document);
                    }
                    else
                    {
                        Log.d("SearchAccountIdFailed", "No such document");
                        writeNewAccount();
                    }
                    popCartIsntEmptyDialog();
                }
                else
                {
                    Log.d("SearchInTheDBFailed", "get failed with ", task.getException());
                }
            }
        });
    }

    /**
     * Extracts the required account from the database, and set the current account to be this
     * account.
     */
    private void setAccountData(DocumentSnapshot document)
    {
        Account currentAccount = document.toObject(Account.class);
        Account.setCurrentAccount(currentAccount);
    }

    /**
     * Writes CANT_ORDER_EMPTY_CART new account in the databases, using the name and uid that was extracted earlier in
     * the login process.
     */
    private void writeNewAccount()
    {
        Account accountToAdd = new Account(name, uid);
        db.collection("Accounts").document(uid).set(accountToAdd);
    }

    /**
     * When the user logouts, this function handle the logout process.
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        // checks if we got here by logout
        if (bundle != null && bundle.getInt("Logout") == 0)
        {
            getIntent().removeExtra("Logout");
            logOut();
        }
    }

    /**
     * Manages the log in process, When there is no logged in user.
     */
    @SuppressLint("SetTextI18n")
    private void LogIn()
    {
        createSignInIntent();
        // sets the activity state to new logged in user state
        continueLogin.setText("Continue");
        loginState = false;
    }

    /**
     * Manages the logout process.
     */
    @SuppressLint("SetTextI18n")
    private void logOut()
    {
        FirebaseAuth.getInstance().signOut();
        // sets the activity state to no user state
        helloTitle.setText("You have been logged out");
        continueLogin.setText("Log In");
        loginState = true;
        profileImage.setImageResource(R.drawable.ic_guest_user);
    }

    /**
     * Manages the process of login or continue with the current logged in user.
     */
    public void continueOrLogin(View view)
    {
        // checks if there is no logged in user
        if (loginState)
        {
            LogIn();
        }
        else
        {
            Intent intent = new Intent(this, CategoriesActivity.class);
            startActivity(intent);
        }
    }

    /**
     * helper function to how it works popup
     */
    private void displayHowItWorksDialog()
    {
        // creates the FragmentManager object
        FragmentManager fm = getSupportFragmentManager();
        HowItWorksDialogFragment howItWorksDialogFragment = HowItWorksDialogFragment.newInstance();
        // show the relevant message
        howItWorksDialogFragment.show(fm, "how_it_works");
        changeInfoButtonsToRed();
    }

    /**
     * Displays the "how it works" message.
     */
    public void popHowItWorks(View view)
    {
        displayHowItWorksDialog();
    }

    /**
     * helper function to pop a msg when cart is not empty
     */
    private void displayMessageIfCartIsEmpty()
    {
        // creates the FragmentManager object
        FragmentManager fm = getSupportFragmentManager();
        CartIsntEmptyDialogFragment cartIsntEmptyDialogFragment =
                CartIsntEmptyDialogFragment.newInstance();
        // show the relevant message
        cartIsntEmptyDialogFragment.show(fm, "cart_isnt_empty");
    }

    /**
     * display msg when the cart of the user is not empty so he can choose what to do
     * (order or cancel or edit)
     * The message shown only when the user's cart is not empty.
     */
    private void popCartIsntEmptyDialog()
    {
        // checks if the cart is not empty
        if (! Account.getCurrentAccount().getCart().isCartEmpty())
            displayMessageIfCartIsEmpty();
    }

    /**
     * Clicking on back press closing up the application.
     */
    @Override
    public void onBackPressed()
    {
        this.finishAffinity();
        System.exit(0);
    }

    /**
     * Changes the color of the info and how_it_works buttons to red.
     */
    private void changeInfoButtonsToRed()
    {
        info.setImageResource(R.drawable.ic_info_red);
        howItWorks.setImageResource(R.drawable.ic_howit_red);
    }

    /**
     * Changes the color of the info and how_it_works buttons to black.
     */
    void changeInfoButtonsToBlack()
    {
        info.setImageResource(R.drawable.ic_info);
        howItWorks.setImageResource(R.drawable.ic_howit);
    }

}
