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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ProductActivity extends AppCompatActivity {

    TextView departmentTitle;
    TextView productName;
    TextView productDescription;
    TextView litersTitle;
    ImageView productImage;
    String department;
    //this is our db that contains all the information of the app
    private FirebaseFirestore db;
    private Product currentProduct;
    private double currentProductLiters;
    private int initialProgress;
    private SeekBar literSeekBar;
    private RadioGroup radioSmellGroup;
    private int bottleColor = R.style.ColorRoses;
    private double oldLitters = 0;

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
        radioSmellGroup = findViewById(R.id.smell_options);
        radioSmellGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeSmell();
            }
        });
        Bundle bundle = getIntent().getExtras();
        db = FirebaseFirestore.getInstance();
        // default value when initializing the seek bar
        initialProgress = 0;
        if (bundle != null)
        {
            department = bundle.getString("Department");
            departmentTitle.setText(department + " Department");
        }
        currentProduct = null;
        updateCurrentProduct(department);
        literSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                double numLiters = progress / 10.0;
                litersTitle.setText(String.valueOf(numLiters) + "L");
                fillBottle(numLiters, bottleColor);
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
        literSeekBar.setProgress(initialProgress);
        fillBottle(0, bottleColor);
    }

    private void fillBottle(double curLitersInSeekBar, int color) {
        Drawable drawable;
        final ContextThemeWrapper wrapper = new ContextThemeWrapper(this, color);
        if (curLitersInSeekBar < 0.3) {
              drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_empty, wrapper.getTheme());
        } else if (curLitersInSeekBar < 1) {
              drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_1, wrapper.getTheme());
        } else if (curLitersInSeekBar < 2) {
              drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_2, wrapper.getTheme());
        } else if (curLitersInSeekBar < 3) {
              drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_3, wrapper.getTheme());
        } else if (curLitersInSeekBar < 4) {
              drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_4, wrapper.getTheme());
        } else if (curLitersInSeekBar < 5) {
              drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_5, wrapper.getTheme());
        } else if (curLitersInSeekBar < 6) {
              drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_6, wrapper.getTheme());
        } else if (curLitersInSeekBar < 7) {
              drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_7, wrapper.getTheme());
        } else if (curLitersInSeekBar < 8) {
              drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_8, wrapper.getTheme());
        } else if (curLitersInSeekBar < 9) {
              drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_9, wrapper.getTheme());
        } else {
              drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_basic_bottle_10, wrapper.getTheme());
        }
        productImage.setImageDrawable(drawable);
    }

    /**
     * update the initial amount of liter to be whats written in the db
     */
    private void updateInitialProgress()
    {
        Account currentAccount = Account.getCurrentAccount();
        Cart currentCart = currentAccount.getCart();
        for (Order currentOrder:currentCart.getOrdersList())
        {
            if (currentOrder.getProduct().getDepartment().equals(department))
            {
                currentProduct = currentOrder.getProduct();
                currentProductLiters = currentOrder.getLiters();
                updateSmellRadioButton(currentProduct.getSmell());
                initialProgress = (int) (currentOrder.getLiters() * 10.0);
                break;
            }
        }
        literSeekBar.setProgress(initialProgress);
        updateCheckBoxNewBottle();
    }

    private void updateSmellRadioButton(String smell) {
        RadioButton radioButtonRoses = findViewById(R.id.radioButtonRoses);
        RadioButton radioButtonAqua = findViewById(R.id.radioButtonAqua);
        RadioButton radioButtonApple = findViewById(R.id.radioButtonApple);
        radioButtonRoses.setChecked(false);
        radioButtonAqua.setChecked(false);
        radioButtonApple.setChecked(false);
        switch (smell) {
            case "Roses":
                radioButtonRoses.setChecked(true);
                break;
            case "Aqua":
                radioButtonAqua.setChecked(true);
                break;
            case "Apple":
                radioButtonApple.setChecked(true);
                break;
        }
    }

    private void displayProduct() {
        productName.setText(currentProduct.getFullName());
        productDescription.setText(currentProduct.getDescription());
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
    private void updateCurrentProduct(String productDepartment)
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
                        currentProduct = document.toObject(Product.class);
                    }
                    displayProduct();
                } else {
                    Log.d("", "Error getting documents: ", task.getException());
                }
                updateInitialProgress();
            }

        });
    }

    private void updateCheckBoxNewBottle() {
        if (currentProduct.isNewBottle())
        {
            updateNewBottle(true);
        }
    }

    public void goToCart(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void addProductToCart(View view) {
        Account account = Account.getCurrentAccount();
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        account.getCart().addProductToCart(currentProduct, currentProductLiters);
        db.collection("Accounts").document(uid).update("cart", account.getCart());
        goToCart(view);
    }

    public void onCheckboxClicked(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();
        updateNewBottle(checked);
    }

    private void updateNewBottle(boolean checked)
    {
        currentProduct.setNewBottle(checked);
        if (checked)
        {
            CheckBox checkBox = findViewById(R.id.checkbox_newBottle);
            checkBox.setChecked(true);
            literSeekBar.setVisibility(View.INVISIBLE);
            litersTitle.setVisibility(View.INVISIBLE);
            oldLitters = currentProductLiters;
            currentProductLiters = literSeekBar.getMax() / 10.0;
        }
        else
        {
            CheckBox checkBox = findViewById(R.id.checkbox_newBottle);
            checkBox.setChecked(false);
            literSeekBar.setVisibility(View.VISIBLE);
            litersTitle.setVisibility(View.VISIBLE);
            currentProductLiters = oldLitters;
        }
        fillBottle(currentProductLiters, bottleColor);
        litersTitle.setText(String.valueOf(currentProductLiters) + "L");
        literSeekBar.setProgress((int) (currentProductLiters * 10));
    }

    public void changeSmell() {
        // get selected radio button from radioGroup
        int selectedId = radioSmellGroup.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.radioButtonRoses:
                bottleColor = R.style.ColorRoses;
                currentProduct.setSmell("Roses");
                break;
            case R.id.radioButtonAqua:
                bottleColor = R.style.ColorAqua;
                currentProduct.setSmell("Aqua");
                break;
            case R.id.radioButtonApple:
                bottleColor = R.style.ColorApple;
                currentProduct.setSmell("Apple");
                break;
        }
        fillBottle(currentProductLiters, bottleColor);
    }

}
