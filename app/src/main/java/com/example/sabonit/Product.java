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
    String department;
    // The specific name of the product in the department.
    String name;
    // Price of one liter of the product, in use when computing the price of a given amount.
    int price_per_liter;
    // Optional, a short description or anything that not reflected in the product's department and name.
    String description;


    /* ********* Constructors: ********* */
    /**
     * Default constructor.
     */
    public Product() {
    }

    /**
     * The constructor gets values for department and name that are primary key, as described above
     * in the class documentation. Also it gets a factor of price_per_liter for the total price
     * computation of orders.
     * @param department - a general category of the products.
     * @param name - a specific product name in the department, probably the manufacturer name.
     * @param price_per_liter - price of one liter of the product.
     */
    public Product(String department, String name, int price_per_liter) {
        this.department = department;
        this.name = name;
        this.price_per_liter = price_per_liter;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice_per_liter() {
        return price_per_liter;
    }

    public void setPrice_per_liter(int price_per_liter) {
        this.price_per_liter = price_per_liter;
    }
}
