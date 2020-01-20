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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    ImageView productImage;
    String department;
    //this is our db that contains all the information of the app
    private FirebaseFirestore db;
    private ArrayList<Product> products;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        departmentTitle = findViewById(R.id.department_name);
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.description);
        productImage = findViewById(R.id.product_image);
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
    }

    private void showRandomOnScreen() {
        Random r = new Random();
        int indexOfProduct = r.nextInt(products.size());
        productName.setText(products.get(indexOfProduct).getFullName());
        productDescription.setText(products.get(indexOfProduct).getDescription());
        Picasso.get().load(products.get(indexOfProduct).getImageUrl()).into(productImage);
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

}
