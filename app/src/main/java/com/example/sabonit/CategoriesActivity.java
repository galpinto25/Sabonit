package com.example.sabonit;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class CategoriesActivity extends AppCompatActivity {

    // todo: All the next code suppose to move to the relevant place (another activity)
    //this is our db that contains all the information of the app
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        db = FirebaseFirestore.getInstance();
    }

    public void intentDepartment(View view) {
        Intent intent = new Intent(this, ProductActivity.class);
        switch (view.getId()) {
            case R.id.face_body_wash:
                intent.putExtra("Department", "Face & Body Wash\n");
                break;
            case R.id.hand_soap:
                intent.putExtra("Department", "Hand Soap ");
                break;
            case R.id.house_cleaning:
                intent.putExtra("Department", "House Cleaning ");
                break;
            case R.id.laundry:
                intent.putExtra("Department", "Laundry ");
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    /**
     * Writes new account in the db, in Accounts table.
     * @param userId - or ID from google account or an ID that the user write
     * @param accountToAdd - the user's new account to add to the db
     */
    private void writeNewAccount(String userId, Account accountToAdd)
    {
        db.collection("Accounts").document(userId).set(accountToAdd);
    }

    private void findProductByName(String productDepartment,String productName)
    {
        Product requestedProduct;
        // Create a reference to the products table
        CollectionReference productsTableRef = db.collection("Products");

        // Create a query against the collection
        productsTableRef.whereEqualTo("department", productDepartment).
                whereEqualTo("name", productName).get()

                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                            {
                                Log.d("product from query", document.getId() + " => " + document.getData());
//                                requestedProduct = document.toObject(Product.class);
                            }
                        } else {
                            Log.d("", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
