package com.example.sabonit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
        SeekBar literSeekBar = findViewById(R.id.liter_seek_bar);
        Bundle bundle = getIntent().getExtras();
        db = FirebaseFirestore.getInstance();
        products = new ArrayList<>();
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
    }

    private void showRandomOnScreen() {
        Random r = new Random();
        int indexOfProduct = r.nextInt(products.size());
        productName.setText(products.get(indexOfProduct).getFullName());
        productDescription.setText(products.get(indexOfProduct).getDescription());
        Picasso.get().load(products.get(indexOfProduct).getImageUrl()).into(productImage);
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
