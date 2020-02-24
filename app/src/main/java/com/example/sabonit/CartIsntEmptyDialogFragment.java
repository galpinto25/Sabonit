package com.example.sabonit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class CartIsntEmptyDialogFragment extends DialogFragment implements
        android.view.View.OnClickListener {

    private FirebaseFirestore db;


    public CartIsntEmptyDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static CartIsntEmptyDialogFragment newInstance() {
        CartIsntEmptyDialogFragment frag = new CartIsntEmptyDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.your_cart_isnt_empty, container);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View view = Objects.requireNonNull(getActivity()).
                getLayoutInflater().inflate(R.layout.your_cart_isnt_empty, null);
        builder.setView(view);
        if (view != null) {
            Button goToCartButton = view.findViewById(R.id.go_to_cart_button);
            goToCartButton.setOnClickListener(this);
            Button CancelOrderButton = view.findViewById(R.id.cancel_order_button);
            CancelOrderButton.setOnClickListener(this);
        }
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_order_button:
//                CartActivity callingActivity = (CartActivity) getActivity();
//                if (callingActivity != null) {
//                    callingActivity.orderItems();
//                }
                Account accountToAdd = Account.getCurrentAccount();
                accountToAdd.setCart(new Cart());
                String uid = accountToAdd.getUID();
                db.collection("Accounts").document(uid).set(accountToAdd);
                dismiss();
                break;
            case R.id.go_to_cart_button:
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        dismiss();
    }

}
