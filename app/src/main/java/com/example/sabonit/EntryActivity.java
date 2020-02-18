package com.example.sabonit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * This class responsible on the activity that shown when the application is start.
 */
public class EntryActivity extends AppCompatActivity
{
    /**
     * Displays the entry activity for a short time defined by SPLASH_DISPLAY_LENGTH, and then call
     * CategoriesActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        ImageView refillImage = findViewById(R.id.entry_gif_image);
        Glide.with(this)
                .load("https://gifimage.net/wp-content/uploads/2018/06/water-drops-gif-11.gif")
                .into(refillImage);

        // New Handler to start the Menu-Activity and close this Splash-Screen after short time.
        int SPLASH_DISPLAY_LENGTH = 3500;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Create an Intent that will start the order-types Activity
                Intent intent = new Intent(EntryActivity.this, UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
