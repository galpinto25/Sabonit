/* ********* Imports: ********* */
package com.example.sabonit;

/**
 * Products of the Sabonit have to fit to a standard, ensuring they are fit to the format of the
 * application. This is important for inserting them to the database properly and handling them.
 * Every action with the product is done by the application developers. The users can get the
 * information about the products in the ProductActivity.
 *
 * There are two kinds of attributes for every product: primary key attributes and product
 * describing attributes. The primary key is a combination of attributes that are unique, for
 * writing them in the database. The describing attributes are all the other attributes, and they
 * are needed as important information of the product.
 */
public class Product
{
    /* ********* Attributes: ********* */
    // The products are divided to categories, and every category is another department.
    private String department;
    // Price of one liter of the product, in use when computing the price of a given amount.
    private int pricePerLiter;
    // Optional, a short description or anything that not reflected in the product's department and name.
    private String description; //todo last decision
    // The types of the product are different one from the another according to the smell
    private String smell;
    // Indicates if the user wants a new bottle or not
    private boolean isNewBottle;


    /* ********* Constructors: ********* */
    /**
     * Default constructor.
     */
    public Product() {
    }

    /**
     * The constructor gets values for department and name that are primary key, as described above
     * in the class documentation. Also it gets a factor of pricePerLiter for the total price
     * computation of orders.
     * @param department - a general category of the products.
     * @param smell - a specific product smell in the department, probably the manufacturer name.
     * @param pricePerLiter - price of one liter of the product.
     */
    public Product(String department, String smell, int pricePerLiter) {
        this.department = department;
        this.smell = smell;
        this.pricePerLiter = pricePerLiter;
        isNewBottle = false;
    }


    /* ********* Getters & Setters: ********* */
    public String getDescription() {
        return description;
    }

    /**
     * @param description - some information about the product.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public String getFullName() { return this.department + " " + this.smell; }

    public int getPricePerLiter() {
        return pricePerLiter;
    }

    public void setPricePerLiter(int pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }

    public String getSmell() {
        return smell;
    }

    public void setSmell(String smell) {
        this.smell = smell;
    }

    public boolean isNewBottle() {
        return isNewBottle;
    }

    public void setNewBottle(boolean newBottle) {
        isNewBottle = newBottle;
    }
}
