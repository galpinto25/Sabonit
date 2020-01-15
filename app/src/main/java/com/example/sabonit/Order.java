/* ********* Imports: ********* */
package com.example.sabonit;

/**
 * todo document
 */
public class Order
{
    /* ********* Attributes: ********* */
    // Product to order fromm the Sabonit.
    private Product product;
    // Amount of liters of product in the total product order.
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
     * @param liters - amount of product to order (in liters)
     */
    public Order(Product product, double liters) {
        this.product = product;
        this.liters = liters;
    }


    /* ********* Functions: ********* */
    /**
     * @param liters - amount of product to add to this order.
     * @return the new amount of liters to order (non-negative).
     */
    public double addLiters(double liters)
    {
        if (this.liters + liters <= 0)
            return 0;
        this.liters += liters;
        return this.liters;
    }

    public Product getProduct() {
        return product;
    }

    public double getLiters() {
        return liters;
    }
}
