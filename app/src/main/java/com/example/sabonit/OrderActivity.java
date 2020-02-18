package com.example.sabonit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

}
