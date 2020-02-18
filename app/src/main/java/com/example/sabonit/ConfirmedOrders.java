package com.example.sabonit;

import java.util.ArrayList;

public class ConfirmedOrders {

    /* ********* Attributes: ********* */
    // Product to order from the Sabonit.
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

    public void addCart(Cart cart)
    {
        carts.add(cart);
    }

}
