package com.example.sabonit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {

    private TextView cartDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartDescription = findViewById(R.id.items_list);
        printCartDescription();
    }

    public void backTo(View view) {
        onBackPressed();
    }

    public void printCartDescription() {
        Account account = Account.getCurrentAccount();
        StringBuilder ordersDescription = new StringBuilder();
        for (Order order: account.getCart().getOrdersList()) {
            Product product = order.getProduct();
            ordersDescription.append(product.getFullName()).append(" - ").
                    append(product.getPricePerLiter() * order.getLiters()).append("NIS\n");
        }
        if (ordersDescription.length() == 0) {
            ordersDescription = new StringBuilder("Your cart is\n empty...\n\uD83D\uDE44");
        }
        cartDescription.setText(ordersDescription);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

}
