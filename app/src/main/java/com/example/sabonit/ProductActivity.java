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

/**
 *  This class represents the screen of product
 */
public class ProductActivity extends AppCompatActivity
{

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
    private String department;

    TextView productName, productDescription, litersTitle, departmentTitle;
    ImageView productImage;

    /* ********* Functions: ********* */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        defineButtons();
        defineRadio();
        db = FirebaseFirestore.getInstance();
        // default value when initializing the seek bar
        initialProgress = 1;
        defineDepartment();
        defineSeekBar();
    }

    /**
     * helper function, define department variable
     */
    private void defineDepartment()
    {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            department = bundle.getString("Department");
            departmentTitle.setText(department + " Department");
        }
        currentProduct = null;
        updateCurrentProduct(department);
    }

    /**
     * helper function, define seekBar variable
     */
    private void defineSeekBar()
    {
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
    }

    /**
     * helper function, define radio variable
     */
    private void defineRadio()
    {
        radioSmellGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeSmell();
            }
        });
    }

    /**
     * helper function, define radio variable
     */
    private void defineButtons()
    {
        departmentTitle = findViewById(R.id.cart_title);
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.description);
        litersTitle = findViewById(R.id.liters);
        productImage = findViewById(R.id.product_image);
        literSeekBar = findViewById(R.id.liter_seek_bar);
        radioSmellGroup = findViewById(R.id.smell_options);
    }

    /**
     * draw fill bottle accroding to the current liters
     * @param curLitersInSeekBar current liters
     * @param color of the bottle
     */
    private void fillBottle(double curLitersInSeekBar, int color)
    {
        Drawable productDrawable;
        final ContextThemeWrapper wrapper = new ContextThemeWrapper(this, color);

        switch (department)
        {
            case "Face & Body Wash":
                productDrawable = fillBottleHelper(Utils.bodyXMLS, curLitersInSeekBar, wrapper);
                break;
            case "Hand Soap":
                productDrawable = fillBottleHelper(Utils.handsXMLS, curLitersInSeekBar, wrapper);
                break;
            case "House Cleaning":
                productDrawable = fillBottleHelper(Utils.houseCleaningXMLS, curLitersInSeekBar, wrapper);
                break;
            case "Laundry":
                productDrawable = fillBottleHelper(Utils.laundryXMLS, curLitersInSeekBar, wrapper);
                break;
            default:
                productDrawable = fillBottleHelper(Utils.dishwashingXMLS, curLitersInSeekBar, wrapper);
        }
        productImage.setImageDrawable(productDrawable);
    }

    /**
     * helper funtion to the above
     * @param xmls all the bottles fill that possible
     * @param curLitersInSeekBar current liters of the products
     * @param wrapper of the seekbar
     * @return
     */
    private Drawable fillBottleHelper(int[] xmls, double curLitersInSeekBar, ContextThemeWrapper wrapper)
    {
        for (int i = 1; i <= 10; i++)
        {
            if (curLitersInSeekBar < i)
            {
                return ResourcesCompat.getDrawable(getResources(), xmls[i - 1], wrapper.getTheme());
            }
        }
        return ResourcesCompat.getDrawable(getResources(), xmls[xmls.length - 1], wrapper.getTheme());
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

    /**
     * after user choose smell, update the radio button
     * @param smell the smell that the user chose
     */
    private void updateSmellRadioButton(String smell)
    {
        switch (smell)
        {
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

    /**
     * drawing the product
     */
    private void displayProduct()
    {
        productName.setText(currentProduct.getFullName());
        productDescription.setText(currentProduct.getDescription());
    }

    /**
     * onClick function on back button
     */
    public void backToCategories(View view)
    {
        onBackPressed();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
        CustomIntent.customType(this, "right-to-left");
    }

    /**
     * check if the user chose this department earlier
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

    /**
     * onClick function helper when user click on checkBox of new bottle
     */
    private void updateCheckBoxNewBottle()
    {
        if (currentProduct.isNewBottle())
        {
            updateNewBottle(true);
        }
    }

    /**
     * onClick function when user click on checkBox of new bottle
     */
    public void onCheckboxClicked(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();
        updateNewBottle(checked);
    }

    /**
     * onClick function when user click on cart button
     */
    public void goToCart(View view)
    {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    /**
     * onClick function when user click on add to cart button
     */
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
        Account currentAccount = Account.getCurrentAccount();
        String uid = currentAccount.getUid();
        currentAccount.getCart().addProductToCart(currentProduct, currentProductLiters);
        db.collection("Accounts").document(uid).update("cart", currentAccount.getCart());
        goToCart(view);
    }

    /**
     * update checkBox and product when choose new bottle or cancel it
     * @param checked if the checkbox is checked or not
     */
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

    /**
     * onClick function helper when user change the smell
     */
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
