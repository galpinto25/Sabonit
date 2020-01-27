/* ********* Imports: ********* */
package com.example.sabonit;

/**
 * Orders holds all the relevant information for products orders and manages it.
 */
public class Order
{
    /* ********* Attributes: ********* */
    // Product to order from the Sabonit.
    private Product product;
    // Amount of litersTitle of product in the total product order.
    private double liters;


    /* ********* Constructors: ********* */
    /**
     * Default constructor.
     */
    public Order() {
    }

    /**
     *  Creates an order of the specified product in the given amount.
     * @param product - type of product to order
     * @param liters - amount of product to order (in shekels to liter)
     */
    public Order(Product product, double liters) {
        this.product = product;
        this.liters = liters;
    }


    /* ********* Functions: ********* */
    /**
     * @param liters - amount of product to add to this order.
     * @return the new amount of litersTitle to order (non-negative).
     */
    public double setLiters(double liters)
    {
        if (liters == 0)
            return 0;
        this.liters = liters;
        return this.liters;
    }

    public void setNewBottle(boolean isNewBottle)
    {
        this.product.setNewBottle(isNewBottle);
    }

    public Product getProduct() {
        return product;
    }

    public double getLiters() {
        return liters;
    }
}
