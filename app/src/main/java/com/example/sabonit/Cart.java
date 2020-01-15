/* ********* Imports: ********* */
package com.example.sabonit;

/**
 * This class represents the user's cart. The cart holds all the orders of the users and enables a
 * comfortable access to handle them.
 */
public class Cart
{
    /* ********* Attributes: ********* */
    // This list holds all the orders which did not got to the consumer.
    private Order[] ordersList;


    /* ********* Constructors: ********* */
    /**
     * Default constructor, initializes an empty orders list.
     */
    public Cart() {
        this.ordersList = new Order[] {};
    }

    /* ********* Functions: ********* */
    /**
     * Helper function for finding index of order of a specific product.
     * @param product - String, full name of the product to find it's order index
     * @return - index of the order of the specified product if exists, else -1
     */
    private int findProductIndex(String product)
    {
        for (int i=0; ; i++)
            if (this.ordersList[i].getProduct().getFullName().equals(product))
                return i;
        return -1;
    }

    /**
     * @param product - product to find it's order in the orders list
     * @return - order of the product from the orders list if exists, else null
     */
    public Order getProductOrder(Product product)
    {
        int index = findProductIndex(product.getFullName());
        if (index >= 0) return ordersList[index];
        else return null;
    }

    /**
     * Adds the amount of liters of the specified product to the orders list. If there is an order
     * of this product already, adds the amount of the liters to the current amount in this order.
     * @param product - product to add to the orders list
     * @param liters - the amount to order from the product (in liters)
     */
    public void addProductToCart(Product product, double liters)
    {
        int index = findProductIndex(product.getFullName());
        if (index >= 0) ordersList[index].addLiters(liters);
        else this.ordersList += new Order(product, liters);
    }
}
