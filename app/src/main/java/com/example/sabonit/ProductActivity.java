package com.example.sabonit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {

    TextView departmentTitle;
    String department;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        departmentTitle = findViewById(R.id.department_name);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            department = bundle.getString("Department");
            departmentTitle.setText(department + "Department");
        }
    }

    public void backToCategories(View view) {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }
}
