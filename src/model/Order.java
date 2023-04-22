package model;

import java.util.*;

public class Order {

    private String buyerName;
    private final ArrayList<Product> productList;
    private final double totalPrice;
    private final Calendar date;

    public Order(
            String buyerName,
            ArrayList<Product> productList,
            double totalPrice
    ) {
        this.buyerName = buyerName;
        this.productList = productList;
        this.totalPrice = totalPrice;
        this.date = Calendar.getInstance();
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public double getTotalPrice() {
        return totalPrice; //For the total price to be given, the prices of each product must be added
    }

    public Calendar getDate() {
        return date;
    }
}
