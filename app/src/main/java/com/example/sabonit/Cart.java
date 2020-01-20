/* ********* Imports: ********* */
package com.example.sabonit;
import java.util.ArrayList;


/**
 * This class represents the user's cart. The cart holds all the orders of the users and enables a
 * comfortable access to handle them.
 */
public class Cart
{

    /* ********* Attributes: ********* */
    // This list holds all the orders which did not got to the consumer.
    private ArrayList<Order> ordersList;


    /* ********* Constructors: ********* */
    /**
     * Default constructor, initializes an empty orders list.
     */
    public Cart() {
        this.ordersList = new ArrayList<Order>();
    }

    /* ********* Functions: ********* */
    /**
     * Helper function for finding index of order of a specific product.
     * @param product - String, full name of the product to find it's order index
     * @return - index of the order of the specified product if exists, else -1
     */
    private int findProductIndex(String product)
    {
        for (int i=0; i < this.ordersList.size(); i++)
            if (this.ordersList.get(i).getProduct().getFullName().equals(product))
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
        if (index >= 0) return ordersList.get(index);
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
        if (index >= 0) {
            double newAmount = ordersList.get(index).addLiters(liters);
            if (newAmount == 0) popOrderFromCart(index);
        }
        else this.ordersList.add(new Order(product, liters));
    }

    /**
     * Removes the order in the orders list that found in the given index, and pops it as return value.
     * @param orderIndex - index of the order to remove.
     */
    private Order popOrderFromCart(int orderIndex)
    {
        return this.ordersList.remove(orderIndex);
    }

    public ArrayList<Order> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(ArrayList<Order> ordersList) {
        this.ordersList = ordersList;
    }
}
