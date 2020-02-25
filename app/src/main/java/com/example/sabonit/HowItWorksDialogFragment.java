package com.example.sabonit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class HowItWorksDialogFragment extends DialogFragment  implements
        android.view.View.OnClickListener {

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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View view = Objects.requireNonNull(getActivity()).
                getLayoutInflater().inflate(R.layout.how_it_works, null);
        builder.setView(view);
        if (view != null) {
            ImageView refillImage = view.findViewById(R.id.gif_image);
            Glide.with(this)
                    .load("https://www.functionofbeauty.com/images/bottle-filling-up.gif")
                    .into(refillImage);
            Button gotItButton = view.findViewById(R.id.got_it_button);
            gotItButton.setOnClickListener(this);
        }
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.got_it_button) {
            dismiss();
        }
        dismiss();
    }

}
