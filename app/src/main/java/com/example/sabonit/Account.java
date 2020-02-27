/* ********* Imports: ********* */
package com.example.sabonit;

import com.google.firebase.firestore.FirebaseFirestore;

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
    //User's uid of the google account, unique value
    private String uid;
    // The account of the user that is logged in
    private static Account currentAccount;
    // Orders that was confirmed by the user
    private ConfirmedOrders confirmedOrders;
    // Pointer of the database
    private FirebaseFirestore db;

    /* ********* Constructors: ********* */
    /**
     * Default constructor
     */
    public Account()
    {
        db = FirebaseFirestore.getInstance();

    }

    /**
     * When signing up, the account is initialized for the user's data handling.
     * @param name - user's name.
     * @param uid - user's uid.
     */
    public Account(String name, String uid)
    {
        this.name = name;
        this.cart = new Cart();
        this.uid = uid;
        currentAccount = this;
        confirmedOrders = new ConfirmedOrders();
        db = FirebaseFirestore.getInstance();
    }

    /* ********* Getters & Setters: ********* */

    /**
     * Returns the current account.
     */
    public static Account getCurrentAccount()
    {
        return currentAccount;
    }

    /**
     * Sets the current account.
     * @param currentAccount a given account.
     */
    public static void setCurrentAccount(Account currentAccount)
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
     * Returns the uid of the account.
     */
    public String getUid()
    {
        return uid;
    }

    /**
     * Sets the confirmed orders of the account.
     * @param uid given uid.
     */
    public void setUid(String uid)
    {
        this.uid = uid;
    }

    /**
     * Returns the confirmed orders of the account.
     */
    public ConfirmedOrders getConfirmedOrders()
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

    /* ********* Functions: ********* */
    /**
     * Empty the cart of the user.
     */
    public void toEmptyCart()
    {
        this.cart = new Cart();
    }

    /**
     * Write the account in the database.
     */
    public void updateAccountInDB()
    {
        db.collection("Accounts").document(uid).set(currentAccount);
    }

}
