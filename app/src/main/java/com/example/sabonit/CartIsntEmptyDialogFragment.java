package com.example.sabonit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;

public class CartIsntEmptyDialogFragment extends DialogFragment {

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

}
