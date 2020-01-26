package com.example.sabonit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

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
        super.onBackPressed();
    }

    public void printCartDescription() {
        Account account = Account.getCurrentAccount();
        StringBuilder ordersDescription = new StringBuilder();
        for (Order order: account.getCart().getOrdersList()) {
            Product product = order.getProduct();
            ordersDescription.append(product.getFullName()).append(" - ").
                    append(product.getPricePerLiter() * order.getLiters()).append("NIS\n");
        }
        cartDescription.setText(ordersDescription);
    }

}
