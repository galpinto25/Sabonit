/* ********* Imports: ********* */
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
import java.util.Objects;

/**
 * This class represents an exit message, which extends DialogFragment class. The message asks the
 * user if he sure that he want to leave the application.
 */
public class ExitDialogFragment extends DialogFragment implements
        View.OnClickListener
{
    /* ********* Constructors: ********* */
    /**
     * Default constructor.
     */
    public ExitDialogFragment()
    {}

    /* ********* Functions: ********* */
    /**
     * Returns the fragment of the exit message dialog.
     */
    public static ExitDialogFragment newInstance()
    {
        ExitDialogFragment fragment = new ExitDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates the exit message dialog and creates onClickListeners to its button.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
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

    /**
     * Implements the onClickListeners of the exit or stay buttons of exit message.
     */
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
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
