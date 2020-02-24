package com.example.sabonit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

public class OrderActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        runConfirmationGif();
        new CountDownTimer(4450, 1000) {
            public void onTick(long millisUntilFinished) {}
            public void onFinish()
            {
                findViewById(R.id.order_gif_image).setVisibility(View.INVISIBLE);
                findViewById(R.id.order_message).setVisibility(View.VISIBLE);
                findViewById(R.id.order_intro).setVisibility(View.VISIBLE);
                findViewById(R.id.order_button).setVisibility(View.VISIBLE);
                findViewById(R.id.drop_gif_image).setVisibility(View.VISIBLE);
                runDropGif();
            }
        }.start();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    private void runConfirmationGif()
    {
        ImageView refillImage = findViewById(R.id.order_gif_image);
        Glide.with(this)
                .load("https://cdn.dribbble.com/users/1690341/screenshots/5705146/" +
                        "tick-animation_complete.gif")
                .into(refillImage);
    }

    private void runDropGif()
    {
        ImageView refillImage = findViewById(R.id.drop_gif_image);
        Glide.with(this)
                .load("https://gifimage.net/wp-content/uploads/2018/06/" +
                        "water-drops-gif-11.gif")
                .into(refillImage);
    }

    public void goToCategories(View view) {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

}
