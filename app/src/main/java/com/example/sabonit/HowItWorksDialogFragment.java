/* ********* Imports: ********* */
package com.example.sabonit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.bumptech.glide.Glide;
import java.util.Objects;

/**
 * This class represents an "how it works" message, which extends DialogFragment class. The message
 * explains the idea of the Sabonit and what the user needs to do for getting the Sabonit service.
 */
public class HowItWorksDialogFragment extends DialogFragment  implements
        android.view.View.OnClickListener
{
    /* ********* Constructors: ********* */
    /**
     * Default constructor.
     */
    public HowItWorksDialogFragment()
    {}

    /* ********* Functions: ********* */
    /**
     * Returns the fragment of the "how it works" message dialog.
     */
    public static HowItWorksDialogFragment newInstance()
    {
        HowItWorksDialogFragment fragment = new HowItWorksDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates the "how it works" message dialog and creates onClickListeners to its button.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View view = Objects.requireNonNull(getActivity()).
                getLayoutInflater().inflate(R.layout.how_it_works, null);
        builder.setView(view);
        if (view != null)
        {
            // handles the gif displaying
            ImageView refillImage = view.findViewById(R.id.gif_image);
            Glide.with(this)
                    .load("https://www.functionofbeauty.com/images/bottle-filling-up.gif")
                    .into(refillImage);
            Button gotItButton = view.findViewById(R.id.got_it_button);
            gotItButton.setOnClickListener(this);
        }
        return builder.create();
    }

    /**
     * Implements the onClickListeners of the 'got it' button of "how it works" message.
     */
    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.got_it_button)
        {
            dismiss();
        }
        dismiss();
    }

}
