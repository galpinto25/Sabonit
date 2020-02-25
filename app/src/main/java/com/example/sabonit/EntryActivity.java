/* ********* Imports: ********* */
package com.example.sabonit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

/**
 * This class is responsible on the activity that shown when the application is start.
 */
public class EntryActivity extends AppCompatActivity
{

    /* ********* Attributes: ********* */
    // Fix entry activity display time length
    int SPLASH_DISPLAY_LENGTH = 3500;

    /**
     * Displays the entry activity for a short time defined by SPLASH_DISPLAY_LENGTH, and then call
     * UserLoginActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        // Graphics - displays the rain drop gif
        displayGif();
        // Short delay and calling user login activity
        goToUserLoginActivity();
    }

    private void goToUserLoginActivity()
    {
        // New Handler to start the account activity and close this Splash-Screen after short time.
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Create an Intent that will start the user login Activity
                Intent intent = new Intent(EntryActivity.this,
                        UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    /**
     * Handles the gif displaying on the screen.
     */
    protected void displayGif()
    {
        ImageView refillImage = findViewById(R.id.entry_gif_image);
        Glide.with(this)
                .load("https://gifimage.net/wp-content/uploads/2018/06/" +
                        "water-drops-gif-11.gif")
                .into(refillImage);
    }

}
