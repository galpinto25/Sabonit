/* ********* Imports: ********* */
package com.example.sabonit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import maes.tech.intentanim.CustomIntent;

/**
 * This class represents the screen of categories
 */
public class CategoriesActivity extends AppCompatActivity
{

    /* ********* Functions: ********* */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //TODO get current account, from it take the cart, from carts take order list,
        // if in order list there is product name "laundry" for example. make it gray
        // (exist in the xmls), the gray symbol is still clickable like earlier - the user will edit the exit product
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
    }

    @Override
    public void onBackPressed()
    {
        showExitDialog();
    }

    /**
     * onClick function for the department that the user choose
     */
    public void intentDepartment(View view)
    {
        Intent intent = new Intent(this, ProductActivity.class);
        switch (view.getId())
        {
            case R.id.face_body_wash:
                intent.putExtra("Department", "Face & Body Wash");
                break;
            case R.id.hand_soap:
                intent.putExtra("Department", "Hand Soap");
                break;
            case R.id.house_cleaning:
                intent.putExtra("Department", "House Cleaning");
                break;
            case R.id.laundry:
                intent.putExtra("Department", "Laundry");
                break;
            case R.id.dishwashing_liquid:
                intent.putExtra("Department", "Dishwashing Liquid");
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

    private void changeCartButtonToRed()
    { //TODO
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
    }

    /**
     * onClick function when user wants the how it works dialog
     */
    public void popHowItWorks(View view)
    {
        showHowItWorksDialog();
    }

}
