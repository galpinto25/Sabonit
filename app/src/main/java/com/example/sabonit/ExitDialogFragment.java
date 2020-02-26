package com.example.sabonit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class ExitDialogFragment extends DialogFragment implements
        View.OnClickListener {

    public ExitDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ExitDialogFragment newInstance() {
        ExitDialogFragment frag = new ExitDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View view = Objects.requireNonNull(getActivity()).
                getLayoutInflater().inflate(R.layout.exit_message, null);
        builder.setView(view);
        if (view != null) {
            Button exitButton = view.findViewById(R.id.exit_button);
            exitButton.setOnClickListener(this);
            Button stayButton = view.findViewById(R.id.stay_button);
            stayButton.setOnClickListener(this);
        }
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_button:
                Activity callingActivity = getActivity();
                if (callingActivity != null) {
                    callingActivity.finishAffinity();
                    System.exit(0);
                }
                break;
            case R.id.stay_button:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}
