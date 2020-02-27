/* ********* Imports: ********* */
package com.example.sabonit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.res.ResourcesCompat;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Objects;
import maes.tech.intentanim.CustomIntent;

public class ProductActivity extends AppCompatActivity
{
    TextView productName;
    TextView productDescription;
    TextView litersTitle;
    TextView departmentTitle;
    ImageView productImage;
    String department;

    // Pointer of the database
    private FirebaseFirestore db;

    /* ********* Attributes: ********* */
    private Product currentProduct;
    private double currentProductLiters;
    private int initialProgress;
    private SeekBar literSeekBar;
    private RadioGroup radioSmellGroup;
    private int bottleColor = R.style.ColorRoses;
    private double oldLitters = 0;

    /* ********* Functions: ********* */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        initialProgress = 1;
        if (bundle != null)
        {
            department = bundle.getString("Department");
            departmentTitle.setText(department + " Department");
        }
        currentProduct = null;
        updateCurrentProduct(department);
        literSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                double numLiters = progress / 10.0;
                litersTitle.setText(String.valueOf(numLiters) + "L");
                fillBottle(numLiters, bottleColor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                currentProductLiters = seekBar.getProgress() / 10.0;
            }
        });
        literSeekBar.setProgress(initialProgress);
//        fillBottle(0, bottleColor);
    }

    private void fillBottle(double curLitersInSeekBar, int color)
    {
        Drawable productDrawable;
        final ContextThemeWrapper wrapper = new ContextThemeWrapper(this, color);
        if (department.equals("Face & Body Wash") ){
            if (curLitersInSeekBar < 0.3) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_body_soap_new_1, wrapper.getTheme());
            } else if (curLitersInSeekBar < 1) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_body_soap_new_2, wrapper.getTheme());
            } else if (curLitersInSeekBar < 2) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_body_soap_new_3, wrapper.getTheme());
            } else if (curLitersInSeekBar < 3) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_body_soap_new_4, wrapper.getTheme());
            } else if (curLitersInSeekBar < 4) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_body_soap_new_5, wrapper.getTheme());
            } else if (curLitersInSeekBar < 5) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_body_soap_new_6, wrapper.getTheme());
            } else if (curLitersInSeekBar < 6) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_body_soap_new_7, wrapper.getTheme());
            } else if (curLitersInSeekBar < 7) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_body_soap_new_8, wrapper.getTheme());
            } else if (curLitersInSeekBar < 8) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_body_soap_new_9, wrapper.getTheme());
            } else if (curLitersInSeekBar < 9) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_body_soap_new_10, wrapper.getTheme());
            } else {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_body_soap_new_11, wrapper.getTheme());
            }
        }
        else if (department.equals("Hand Soap") ){
            if (curLitersInSeekBar < 0.3) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hand_soap_new, wrapper.getTheme());
            } else if (curLitersInSeekBar < 1) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hand_soap_new_1, wrapper.getTheme());
            } else if (curLitersInSeekBar < 2) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hand_soap_new_2, wrapper.getTheme());
            } else if (curLitersInSeekBar < 3) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hand_soap_new_3, wrapper.getTheme());
            } else if (curLitersInSeekBar < 4) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hand_soap_new_4, wrapper.getTheme());
            } else if (curLitersInSeekBar < 5) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hand_soap_new_5, wrapper.getTheme());
            } else if (curLitersInSeekBar < 6) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hand_soap_new_6, wrapper.getTheme());
            } else if (curLitersInSeekBar < 7) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hand_soap_new_7, wrapper.getTheme());
            } else if (curLitersInSeekBar < 8) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hand_soap_new_8, wrapper.getTheme());
            } else if (curLitersInSeekBar < 9) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hand_soap_new_9, wrapper.getTheme());
            } else {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hand_soap_new_10, wrapper.getTheme());
            }
        }
        else if (department.equals("House Cleaning") ){
            if (curLitersInSeekBar < 0.3) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_house_cleaning_new, wrapper.getTheme());
            } else if (curLitersInSeekBar < 1) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_house_cleaning_new_1, wrapper.getTheme());
            } else if (curLitersInSeekBar < 2) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_house_cleaning_new_2, wrapper.getTheme());
            } else if (curLitersInSeekBar < 3) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_house_cleaning_new_3, wrapper.getTheme());
            } else if (curLitersInSeekBar < 4) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_house_cleaning_new_4, wrapper.getTheme());
            } else if (curLitersInSeekBar < 5) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_house_cleaning_new_5, wrapper.getTheme());
            } else if (curLitersInSeekBar < 6) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_house_cleaning_new_6, wrapper.getTheme());
            } else if (curLitersInSeekBar < 7) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_house_cleaning_new_7, wrapper.getTheme());
            } else if (curLitersInSeekBar < 8) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_house_cleaning_new_8, wrapper.getTheme());
            } else if (curLitersInSeekBar < 9) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_house_cleaning_new_9, wrapper.getTheme());
            } else {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_house_cleaning_new_10, wrapper.getTheme());
            }
        }
        else if (department.equals("Laundry") ){
             if (curLitersInSeekBar < 1) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_laundry_new_1, wrapper.getTheme());
            } else if (curLitersInSeekBar < 2) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_laundry_new_2, wrapper.getTheme());
            } else if (curLitersInSeekBar < 3) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_laundry_new_3, wrapper.getTheme());
            } else if (curLitersInSeekBar < 4) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_laundry_new_4, wrapper.getTheme());
            } else if (curLitersInSeekBar < 5) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_laundry_new_5, wrapper.getTheme());
            } else if (curLitersInSeekBar < 6) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_laundry_new_6, wrapper.getTheme());
            } else if (curLitersInSeekBar < 7) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_laundry_new_7, wrapper.getTheme());
            } else if (curLitersInSeekBar < 8) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_laundry_new_8, wrapper.getTheme());
            } else if (curLitersInSeekBar < 9) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_laundry_new_9, wrapper.getTheme());
            } else {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_laundry_new_10, wrapper.getTheme());
            }
        }
        else {
            if (curLitersInSeekBar < 1) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dishwashing_new_1, wrapper.getTheme());
            } else if (curLitersInSeekBar < 2) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dishwashing_new_2, wrapper.getTheme());
            } else if (curLitersInSeekBar < 3) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dishwashing_new_3, wrapper.getTheme());
            } else if (curLitersInSeekBar < 4) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dishwashing_new_4, wrapper.getTheme());
            } else if (curLitersInSeekBar < 5) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dishwashing_new_5, wrapper.getTheme());
            } else if (curLitersInSeekBar < 6) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dishwashing_new_6, wrapper.getTheme());
            } else if (curLitersInSeekBar < 7) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dishwashing_new_7, wrapper.getTheme());
            } else if (curLitersInSeekBar < 8) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dishwashing_new_8, wrapper.getTheme());
            } else if (curLitersInSeekBar < 9) {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dishwashing_new_9, wrapper.getTheme());
            } else {
                productDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dishwashing_new_10, wrapper.getTheme());
            }
        }
        productImage.setImageDrawable(productDrawable);
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

    private void updateSmellRadioButton(String smell)
    {
        switch (smell) {
            case "Roses":
                radioSmellGroup.check(R.id.radioButtonRoses);
                break;
            case "Aqua":
                radioSmellGroup.check(R.id.radioButtonAqua);
                break;
            case "Apple":
                radioSmellGroup.check(R.id.radioButtonApple);
                break;
        }
    }

    private void displayProduct()
    {
        productName.setText(currentProduct.getFullName());
        productDescription.setText(currentProduct.getDescription());
    }

    public void backToCategories(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
        CustomIntent.customType(this, "right-to-left");
    }

    public void logout(View view)
    {
        Intent intent = new Intent(this, UserLoginActivity.class);
        intent.putExtra("Logout", 0);
        startActivity(intent);
    }

    /**
     * get as an input CANT_ORDER_EMPTY_CART product department name and add products to the products array
     * @param productDepartment the department name of the product
     */
    private void updateCurrentProduct(String productDepartment)
    {
        // Create CANT_ORDER_EMPTY_CART reference to the products table
        CollectionReference productsTableRef = db.collection("Products");

        // Create CANT_ORDER_EMPTY_CART query against the collection
        productsTableRef.whereEqualTo("department", productDepartment).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
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

    private void updateCheckBoxNewBottle()
    {
        if (currentProduct.isNewBottle())
        {
            updateNewBottle(true);
        }
    }

    public void goToCart(View view)
    {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void addProductToCart(View view)
    {
        if(currentProductLiters == 0)
        {
            Context context = getApplicationContext();
            CharSequence text = "You can't add 0 liters!";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
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

    @SuppressLint("SetTextI18n")
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

    public void changeSmell()
    {
        // get selected radio button from radioGroup
        int selectedId = radioSmellGroup.getCheckedRadioButtonId();
        switch (selectedId)
        {
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
