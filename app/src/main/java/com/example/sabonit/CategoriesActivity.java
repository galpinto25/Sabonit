/* ********* Imports: ********* */
package com.example.sabonit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

/**
 * This class represents the screen of categories
 */
public class CategoriesActivity extends AppCompatActivity
{

    /* ********* Attributes: ********* */
    private ImageView cartButton;
    private ImageView infoButton;

    /* ********* Functions: ********* */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        cartButton = findViewById(R.id.cart);
        infoButton = findViewById(R.id.info);
        Account currentAccount = Account.getCurrentAccount();
        Cart cart = currentAccount.getCart();
        ArrayList<Order> orders = cart.getOrdersList();
        updateOrderedDepartments(orders);
    }

    @Override
    public void onBackPressed()
    {
        showExitDialog();
    }

    /**
     * Updates the departments that the current account ordered from as gray (in order
     * to indicate to the user that a product was ordered from some department).
     * @param orders the orders of the current account.
     */
    private void updateOrderedDepartments(ArrayList<Order> orders)
    {
        for (Order order: orders)
        {
            switch (order.getProduct().getDepartment())
            {
                case "Laundry":
                    ImageButton laundry = findViewById(R.id.laundry);
                    laundry.setImageResource(R.drawable.ic_laundry_gray);
                    break;
                case "Hand Soap":
                    ImageButton handSoap = findViewById(R.id.hand_soap);
                    handSoap.setImageResource(R.drawable.handsoap_image_gray);
                    break;
                case "Dishwashing Liquid":
                    ImageButton dishwashingLiquid = findViewById(R.id.dishwashing_liquid);
                    dishwashingLiquid.setImageResource(R.drawable.ic_dish_soap_gray);
                    break;
                case "Face & Body Wash":
                    ImageButton faceBodyWash = findViewById(R.id.face_body_wash);
                    faceBodyWash.setImageResource(R.drawable.ic_face_body_wash_gray);
                    break;
                case "House Cleaning":
                    ImageButton houseCleaning = findViewById(R.id.house_cleaning);
                    houseCleaning.setImageResource(R.drawable.ic_house_clean_gray);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * onClick function for the department that the user choose
     */
    public void intentDepartment(View view)
    {
        Intent intent = new Intent(this, ProductActivity.class);
        switch (view.getId())
        {
            case R.id.laundry:
                intent.putExtra("Department", "Laundry");
                break;
            case R.id.hand_soap:
                intent.putExtra("Department", "Hand Soap");
                break;
            case R.id.dishwashing_liquid:
                intent.putExtra("Department", "Dishwashing Liquid");
                break;
            case R.id.face_body_wash:
                intent.putExtra("Department", "Face & Body Wash");
                break;
            case R.id.house_cleaning:
                intent.putExtra("Department", "House Cleaning");
                break;
            default:
                break;
        }
        startActivity(intent);
        CustomIntent.customType(this, "left-to-right");
    }

    /**
     * onClick function when the user choose to go to cart
     */
    public void goToCart(View view)
    {
        changeCartButtonToRed();
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    /**
     * Changes the color of the cart button to red.
     */
    private void changeCartButtonToRed()
    {
        cartButton.setImageResource(R.drawable.ic_cart_red);
    }

    /**
     * Changes the color of the info button to red.
     */
    private void changeInfoButtonToRed()
    {
        infoButton.setImageResource(R.drawable.ic_info_red);
    }

    /**
     * Changes the color of the info button to black.
     */
    void changeInfoButtonToBlack()
    {
        infoButton.setImageResource(R.drawable.ic_info);
    }

    /**
     * onClick function when user click backpressed of the device to exit
     */
    private void showExitDialog()
    {
        FragmentManager fm = getSupportFragmentManager();
        ExitDialogFragment exitDialogFragment = ExitDialogFragment.newInstance();
        exitDialogFragment.show(fm, "exit_message");
    }

    /**
     * onClick function helper when user wants the how it works dialog
     */
    private void showHowItWorksDialog()
    {
        FragmentManager fm = getSupportFragmentManager();
        HowItWorksDialogFragment howItWorksDialogFragment = HowItWorksDialogFragment.newInstance();
        howItWorksDialogFragment.show(fm, "how_it_works");
        changeInfoButtonToRed();
    }

    /**
     * onClick function when user wants the how it works dialog
     */
    public void popHowItWorks(View view)
    {
        showHowItWorksDialog();
    }

}
