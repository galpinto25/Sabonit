package com.example.sabonit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {

    TextView departmentTitle;
    TextView productName;
    TextView productDescription;
    TextView litersTitle;
    ImageView productImage;
    String department;
    //this is our db that contains all the information of the app
    private FirebaseFirestore db;
    private ArrayList<Product> products;
    private int currentProductIndex;
    private double currentProductLiters;
    private int initalProgress;
    private SeekBar literSeekBar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        departmentTitle = findViewById(R.id.cart_title);
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.description);
        litersTitle = findViewById(R.id.liters);
        productImage = findViewById(R.id.product_image);
        literSeekBar = findViewById(R.id.liter_seek_bar);
        Bundle bundle = getIntent().getExtras();
        db = FirebaseFirestore.getInstance();
        products = new ArrayList<>();
        initalProgress = 0;
        if (bundle != null)
        {
            department = bundle.getString("Department");
            departmentTitle.setText(department + "Department");
        }
        String dep = department.replace(" ", "").replace("\n", "");
        updateProductsByDepartmentName(dep);
        literSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                double numLiters = progress / 10.0;
                litersTitle.setText(String.valueOf(numLiters) + "L");
                fillBottle(numLiters, R.style.ColorRoses);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                currentProductLiters = seekBar.getProgress() / 10.0;
            }
        });
        literSeekBar.setProgress(initalProgress);
    }

    private void fillBottle(double numLiters, int color) {
        final ContextThemeWrapper wrapper = new ContextThemeWrapper(this, color);
        if (numLiters < 0.3) {
            final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_empty, wrapper.getTheme());
            productImage.setImageDrawable(drawable);
        } else if (numLiters < 1) {
            final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_1, wrapper.getTheme());
            productImage.setImageDrawable(drawable);
        } else if (numLiters < 2) {
            final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_2, wrapper.getTheme());
            productImage.setImageDrawable(drawable);
        } else if (numLiters < 3) {
            final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_3, wrapper.getTheme());
            productImage.setImageDrawable(drawable);
        } else if (numLiters < 4) {
            final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_4, wrapper.getTheme());
            productImage.setImageDrawable(drawable);
        } else if (numLiters < 5) {
            final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_5, wrapper.getTheme());
            productImage.setImageDrawable(drawable);
        } else if (numLiters < 6) {
            final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_6, wrapper.getTheme());
            productImage.setImageDrawable(drawable);
        } else if (numLiters < 7) {
            final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_7, wrapper.getTheme());
            productImage.setImageDrawable(drawable);
        } else if (numLiters < 8) {
            final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_8, wrapper.getTheme());
            productImage.setImageDrawable(drawable);
        } else if (numLiters < 9) {
            final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_9, wrapper.getTheme());
            productImage.setImageDrawable(drawable);
        } else {
            final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_10, wrapper.getTheme());
            productImage.setImageDrawable(drawable);
        }
    }

    /**
     * update the initial ammount of liter to be whats written in the db
     */
    private void updateInitialProgress()
    {
        Account currentAccount = Account.getCurrentAccount();
        Cart currentCart = currentAccount.getCart();
        for (Order currentOrder:currentCart.getOrdersList())
        {
            if (currentOrder.getProduct().getFullName().equals(products.get(currentProductIndex).getFullName()))
            {
                initalProgress = (int) (currentOrder.getLiters() * 10.0);
                break;
            }

        }
        literSeekBar.setProgress(initalProgress);
    }

    private void showRandomOnScreen() {
        Random r = new Random();
        int indexOfProduct = r.nextInt(products.size());
        productName.setText(products.get(indexOfProduct).getFullName());
        productDescription.setText(products.get(indexOfProduct).getDescription());
//        Picasso.get().load(products.get(indexOfProduct).getImageUrl()).into(productImage);
        currentProductIndex = indexOfProduct;
    }

    public void backToCategories(View view) {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        Intent intent = new Intent(this, UserLoginActivity.class);
        intent.putExtra("Logout", 0);
        startActivity(intent);
    }

    /**
     * get as an input a product department name and add products to the products array
     * @param productDepartment the department name of the product
     */
    private void updateProductsByDepartmentName(String productDepartment)
    {
        // Create a reference to the products table
        CollectionReference productsTableRef = db.collection("Products");

        // Create a query against the collection
        productsTableRef.whereEqualTo("department", productDepartment).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            {
                                Log.d("product from query", document.getId() + " => " + document.getData());
                                Product requestedProduct = document.toObject(Product.class);
                                products.add(requestedProduct);
                            }
                            showRandomOnScreen();
                        } else {
                            Log.d("", "Error getting documents: ", task.getException());
                        }
                        updateInitialProgress();
                    }

                });
    }

    public void goToCart(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void addProductToCart(View view) {
        Account account = Account.getCurrentAccount();
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        account.getCart().addProductToCart(products.get(currentProductIndex), currentProductLiters);
        db.collection("Accounts").document(uid).update("cart", account.getCart());
    }

    // todo needs to be implemented
    public void setSeekBar(double liters) {
    }

}
