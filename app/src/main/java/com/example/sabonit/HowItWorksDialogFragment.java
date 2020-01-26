package com.example.sabonit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class HowItWorksDialogFragment extends DialogFragment {

    public HowItWorksDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static HowItWorksDialogFragment newInstance() {
        HowItWorksDialogFragment frag = new HowItWorksDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.how_it_works, container);
        ImageView refillImage = view.findViewById(R.id.gif_image);
        Glide.with(this)
                .load("https://www.functionofbeauty.com/images/bottle-filling-up.gif")
                .into(refillImage);
        return view;
    }

}
