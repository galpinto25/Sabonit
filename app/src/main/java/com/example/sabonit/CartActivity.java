package com.example.sabonit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class CartActivity extends AppCompatActivity implements CartAdapter.ItemClickListener {

    // class private variables declaration:
    private RecyclerView recyclerView;
    private TextView cartDescription;
    private Cart cart;
    private ArrayList<Order> orders;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartDescription = findViewById(R.id.items_list);
        cart = Account.getCurrentAccount().getCart();
        orders = cart.getOrdersList();
        recyclerView = findViewById(R.id.cart_description);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // specifies a CartAdapter
        updateCartAdapter();
        db = FirebaseFirestore.getInstance();
        // todo needs to implement total price function
        // updateTotalPrice();
    }

    private void updateCartAdapter() {
        CartAdapter adapter = new CartAdapter(this, orders);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(new CartAdapter.ItemClickListener() {
            @Override
            public void onProductEditClick(View view, int position) {
                CartActivity.this.onProductEditClick(view, position);
            }

            @Override
            public void onProductDeleteClick(View view, int position) {
                CartActivity.this.onProductDeleteClick(view, position);
            }
        });
    }

    public void backTo(View view) {
        onBackPressed();
    }

    public void printCartDescription() {
        StringBuilder ordersDescription = new StringBuilder();
        for (Order order: cart.getOrdersList()) {
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

    @Override
    public void onProductEditClick(View view, int position) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("Department", orders.get(position).getProduct().getDepartment());
        startActivity(intent);
    }

    @Override
    public void onProductDeleteClick(View view, int position) {
        cart.getOrdersList().remove(position);
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        db.collection("Accounts").document(uid).update("cart", cart);
        updateCartAdapter();
    }

    public void goToOrder(View view) {
        showHowItWorksDialog();
//        Intent intent = new Intent(this, OrderActivity.class);
//        startActivity(intent);
    }

    private void showHowItWorksDialog() {
        FragmentManager fm = getSupportFragmentManager();
        OrderMessageDialogFragment orderMessageDialogFragment =
                OrderMessageDialogFragment.newInstance();
        orderMessageDialogFragment.show(fm, "order_message");
    }

}
