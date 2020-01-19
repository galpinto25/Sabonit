/* ********* Imports: ********* */
package com.example.sabonit;
import android.content.Context;
import android.telephony.TelephonyManager;


/**
 * This class represents a user's account, which holds all the data of the user. The operations of
 * the users with the database are based on the data stored in the account. Therefore the attribute
 * phoneNumber needs to be unique, as a primary key. If a phone number stored in the google account
 * and no phone number given as an input to the constructor, the phone number in the google account
 * will be taken.
 */
public class Account
{
    /* ********* Constants: ********* */
    // For validity phone number checking
    private static int IMPLICIT_ISRAELI_PHONE_NUMBER_LENGTH = 9;
    private static int ISRAELI_AREA_CODE = 13;
    private static String ISRAELI_AREA_CODE_INFIX = "+972";


    /* ********* Attributes: ********* */
    // Given as an address
    private String location;
    // User's name, may can be extracted from the google acount
    private String name;
    // User's phone number, may can be extracted from the google acount
    private String phoneNumber;
    // User's cart, empty when initialized
    private Cart cart;
    // User's history, empty when initialized
    private History history;


    /* ********* Constructors: ********* */
    /**
     * Default constructor.
     */
    public Account() {
    }

    /**
     * When signing up, the account is initialized for the user's sata handling.
     * @param location - the address that the Sabonit get in the order.
     * @param name - user's name.
     * @param phoneNumber - user's phone number.
     */
    public Account(String location, String name, String phoneNumber) {
        this.location = location;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.cart = new Cart();
        this.history = new History();
    }

    /* ********* Getters & Setters: ********* */

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Cart getCart() {
        return cart;
    }

    public History getHistory() {
        return history;
    }
}

/* // todo - move the next code to the appropriate activity: responsible of extracting relevant data
             of the user and check validity of it.

        this.phoneNumber = getValidPhoneNumber(phoneNumber);
        // todo add a unique check
        if (!isPhoneNumberValid(phoneNumber))
        {
            TelephonyManager tMgr = (TelephonyManager)mAppContext.getSystemService(Context.TELEPHONY_SERVICE);
            String mPhoneNumber = tMgr.getLine1Number();
        } else
            this.phoneNumber = phoneNumber;

    private String getValidPhoneNumber(String phoneNumber, Context appContext)
    {
        // validity check of input - checks if phoneNumber is a String
        TelephonyManager telephonyManager;
        if (!(phoneNumber instanceof String))
            telephonyManager = (TelephonyManager)appContext.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getLine1Number();
        // remove all white spaces and all dashes (and anything that is not a digit)
        phoneNumber = phoneNumber.replaceAll("\\D", "");
        // First condition - for numbers in the shape "0_-___-____"
        // Second condition - for numbers in the shape "+972-__-___-____"
        if (phoneNumber.length() == IMPLICIT_ISRAELI_PHONE_NUMBER_LENGTH && phoneNumber.charAt(0) == 0 ||
            phoneNumber.length() == ISRAELI_AREA_CODE && phoneNumber.substring(0, 4).equals(ISRAELI_AREA_CODE_INFIX))
            return phoneNumber;
    }

 */