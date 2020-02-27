/* ********* Imports: ********* */
package com.example.sabonit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Objects;

/**
 * This class represents a message about an order that was not finished, which extends
 * DialogFragment class. The message asks the user if wants to finish the last order or to nullify
 * it.
 */
public class CartIsntEmptyDialogFragment extends DialogFragment implements
        android.view.View.OnClickListener
{
    /* ********* Constructors: ********* */
    /**
     * Default constructor.
     */
    public CartIsntEmptyDialogFragment()
    {}

    /* ********* Functions: ********* */
    /**
     * Returns the fragment of the "your cart is not empty" message dialog.
     */
    public static CartIsntEmptyDialogFragment newInstance()
    {
        CartIsntEmptyDialogFragment fragment = new CartIsntEmptyDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates the "your cart is not empty" message dialog and creates onClickListeners to its
     * buttons.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View view = Objects.requireNonNull(getActivity()).
                getLayoutInflater().inflate(R.layout.your_cart_isnt_empty, null);
        builder.setView(view);
        if (view != null)
        {
            Button goToCartButton = view.findViewById(R.id.go_to_cart_button);
            goToCartButton.setOnClickListener(this);
            Button CancelOrderButton = view.findViewById(R.id.cancel_order_button);
            CancelOrderButton.setOnClickListener(this);
        }
        return builder.create();
    }

    /**
     * Implements the onClickListeners of the 'cancel order'/'go to cart' buttons of "your cart is
     * not empty" message.
     */
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.cancel_order_button:
                Account currentAccount = Account.getCurrentAccount();
                currentAccount.toEmptyCart();
                currentAccount.updateAccountInDB();
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
