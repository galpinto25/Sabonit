/* ********* Imports: ********* */
package com.example.sabonit;

/**
 * This class represents a user's account, which holds all the data of the user. The operations of
 * the users with the database are based on the data stored in the account. Therefore the attribute
 * phoneNumber needs to be unique, as a primary key. If a phone number stored in the google account
 * and no phone number given as an input to the constructor, the phone number in the google account
 * will be taken.
 */
public class Account
{

    /* ********* Attributes: ********* */
    // User's name, may can be extracted from the google account
    private String name;
    // User's cart, empty when initialized
    private Cart cart;
    //User's UID of the google account, unique value
    private String UID;
    // The account of the user that is logged in
    private static Account currentAccount;

    private ConfirmedOrders confirmedOrders;

    /* ********* Constructors: ********* */
    /**
     * Default constructor.
     */
    public Account()
    {}

    /**
     * When signing up, the account is initialized for the user's data handling.
     * @param name - user's name.
     * @param uid - user's UID.
     */
    Account(String name, String uid)
    {
        this.name = name;
        this.cart = new Cart();
        this.UID = uid;
        currentAccount = this;
        confirmedOrders = new ConfirmedOrders();
    }

    /* ********* Getters & Setters: ********* */

    /**
     * Returns the current account.
     */
    static Account getCurrentAccount()
    {
        return currentAccount;
    }

    /**
     * Sets the current account.
     * @param currentAccount a given account.
     */
    static void setCurrentAccount(Account currentAccount)
    {
        Account.currentAccount = currentAccount;
    }

    /**
     * Returns the name of account.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the account.
     * @param name a given name.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the cart of the account.
     */
    public Cart getCart()
    {
        return cart;
    }

    /**
     * Sets the cart of the account.
     * @param cart a given cart.
     */
    public void setCart(Cart cart)
    {
        this.cart = cart;
    }

    /**
     * Returns the UID of the account.
     */
    String getUID()
    {
        return UID;
    }

    /**
     * Sets the confirmed orders of the account.
     * @param UID given UID.
     */
    public void setUID(String UID)
    {
        this.UID = UID;
    }

    /**
     * Returns the confirmed orders of the account.
     */
    ConfirmedOrders getConfirmedOrders()
    {
        return confirmedOrders;
    }

    /**
     * Sets the confirmed orders of the account.
     * @param confirmedOrders given confirmed orders.
     */
    public void setConfirmedOrders(ConfirmedOrders confirmedOrders)
    {
        this.confirmedOrders = confirmedOrders;
    }

}
