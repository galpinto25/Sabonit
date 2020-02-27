/* ********* Imports: ********* */
package com.example.sabonit;

import java.util.ArrayList;

public class ConfirmedOrders
{
    /* ********* Attributes: ********* */
    // Carts that the user ordered from the Sabonit.
    private ArrayList<Cart> carts;

    /* ********* Constructors: ********* */
    /**
     * Default constructor.
     */
    public ConfirmedOrders() {
        this.carts = new ArrayList<Cart>();
    }

    /* ********* Functions: ********* */
    public ArrayList<Cart> getCarts()
    {
        return carts;
    }

    public void setCarts(ArrayList<Cart> carts)
    {
        this.carts = carts;
    }

    /**
     * Add the given cart to the confirmed orders list.
     * @param cart - the cart that the user confirmed it's order
     */
    public void addCart(Cart cart)
    {
        carts.add(cart);
    }

}
