package com.example.sabonit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class OrderMessageDialogFragment extends DialogFragment implements
        android.view.View.OnClickListener {

    public OrderMessageDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static OrderMessageDialogFragment newInstance() {
        OrderMessageDialogFragment frag = new OrderMessageDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_message, container);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View view = Objects.requireNonNull(getActivity()).
                getLayoutInflater().inflate(R.layout.order_message, null);
        builder.setView(view);
        if (view != null) {
            Button yesButton = view.findViewById(R.id.yes_button);
            yesButton.setOnClickListener(this);
            Button noButton = view.findViewById(R.id.no_button);
            noButton.setOnClickListener(this);
        }
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.yes_button:
                CartActivity callingActivity = (CartActivity) getActivity();
                if (callingActivity != null) {
                    callingActivity.orderItems();
                }
                break;
            case R.id.no_button:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}
