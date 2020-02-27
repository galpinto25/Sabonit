/* ********* Imports: ********* */
package com.example.sabonit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import java.util.Objects;

/**
 * This class represents an order message, which extends DialogFragment class. The message asks the
 * user if he is sure that he wants to order, because it is impossible to cancel CANT_ORDER_EMPTY_CART confirmed order.
 */
public class OrderMessageDialogFragment extends DialogFragment implements
        android.view.View.OnClickListener
{
    /* ********* Constructors: ********* */
    /**
     * Default constructor.
     */
    public OrderMessageDialogFragment()
    {}

    /* ********* Functions: ********* */
    /**
     * Returns the fragment of the order message dialog.
     */
    public static OrderMessageDialogFragment newInstance()
    {
        OrderMessageDialogFragment fragment = new OrderMessageDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates the order message dialog and creates onClickListeners to its buttons.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View view = Objects.requireNonNull(getActivity()).
                getLayoutInflater().inflate(R.layout.order_message, null);
        builder.setView(view);
        if (view != null)
        {
            Button continueButton = view.findViewById(R.id.continue_button);
            continueButton.setOnClickListener(this);
            Button goBackButton = view.findViewById(R.id.go_back_button);
            goBackButton.setOnClickListener(this);
        }
        return builder.create();
    }

    /**
     * Implements the onClickListeners of the 'continue'/'go back' buttons of order message.
     */
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.continue_button:
                CartActivity callingActivity = (CartActivity) getActivity();
                if (callingActivity != null)
                {
                    callingActivity.orderItems();
                }
                break;
            case R.id.go_back_button:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}
