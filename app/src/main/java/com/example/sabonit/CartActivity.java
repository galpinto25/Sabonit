/* ********* Imports: ********* */
package com.example.sabonit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents the screen of cart, it contains list of products that user chose.
 * from here user can submit order or add new product or edit exist product.
 */
public class CartActivity extends AppCompatActivity implements CartAdapter.ItemClickListener {

    /* ********* Attributes: ********* */
    private RecyclerView recyclerView;
    private TextView cartDescription;
    private Cart cart;
    private ArrayList<Order> orders;
    private FirebaseFirestore db;
    private Account currentAccount;
    private ImageButton plusButton;

    /* ********* Functions: ********* */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        drawScreen();
        currentAccount = Account.getCurrentAccount();
        cart = currentAccount.getCart();
        orders = cart.getOrdersList();
        updateRecyclerView();
        updateCartAdapter();
        db = FirebaseFirestore.getInstance();
        plusButton = findViewById(R.id.plus_button);
    }

    /**
     * draw items on screen
     */
    private void drawScreen()
    {
        setContentView(R.layout.activity_cart);
        cartDescription = findViewById(R.id.items_list);
        printEmptyCartDescription();
    }

    /**
     * updating the symblos on the screen
     */
    private void updateRecyclerView()
    {
        recyclerView = findViewById(R.id.cart_description);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * draw the view of the cart on the screen
     */
    private void updateCartAdapter()
    {
        CartAdapter adapter = new CartAdapter(this, orders);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(new CartAdapter.ItemClickListener()
        {
            @Override
            public void onProductEditClick(View view, int position)
            {
                CartActivity.this.onProductEditClick(view, position);
            }

            @Override
            public void onProductDeleteClick(View view, int position)
            {
                CartActivity.this.onProductDeleteClick(view, position);
            }
        });
    }

    /**
     * going back to the categories activity to add new product
     */
    public void addNewProductClick(View view)
    {
        onBackPressed();
    }

    @Override
    public void onBackPressed()
    {
        changePlusButtonToRed();
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    /**
     * Changes the color of the plus button to red.
     */
    private void changePlusButtonToRed()
    {
        plusButton.setBackground(getDrawable(R.drawable.ic_plus_red));
    }

    /**
     * print CANT_ORDER_EMPTY_CART message if the cart is empty
     */
    public void printEmptyCartDescription()
    {
        StringBuilder ordersDescription = new StringBuilder(Utils.YOUR_CART_IS_EMPTY);
        cartDescription.setText(ordersDescription);
    }

    @Override
    public void onProductEditClick(View view, int position)
    {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("Department", orders.get(position).getProduct().getDepartment());
        startActivity(intent);
    }

    @Override
    public void onProductDeleteClick(View view, int position)
    {
        cart.getOrdersList().remove(position);
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        db.collection("Accounts").document(uid).update("cart", cart);
        updateCartAdapter();
    }

    /**
     * after click order the user confirmed the products
     */
    public void goToOrder(View view)
    {
        if (cart.isCartEmpty())
        {
            Context context = getApplicationContext();
            CharSequence text = Utils.CANT_ORDER_EMPTY_CART;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {
            showHowItWorksDialog();
        }
    }

    /**
     * when user commit and order we update his order in the db
     */
    public void orderItems()
    {
        if (cart.isCartEmpty())
        {
            Context context = getApplicationContext();
            CharSequence text = Utils.CANT_ORDER_EMPTY_CART;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        currentAccount.getConfirmedOrders().addCart(cart);
        currentAccount.toEmptyCart();
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    /**
     * if user press how it works we display the fit dialog
     */
    private void showHowItWorksDialog()
    {
        FragmentManager fm = getSupportFragmentManager();
        OrderMessageDialogFragment orderMessageDialogFragment =
                OrderMessageDialogFragment.newInstance();
        orderMessageDialogFragment.show(fm, Utils.ORDER_MESSAGE);
    }

}
