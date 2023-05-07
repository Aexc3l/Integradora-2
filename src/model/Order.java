package model;

import java.util.*;

public class Order extends ArrayList<Order> {

    private String buyerName;
    private final ArrayList<Product> productList;
    private double totalPrice;
    private final Calendar date;

    public Order(
            String buyerName,
            ArrayList<Product> productList
    ) {
        this.buyerName = buyerName;
        this.productList = productList;
        this.totalPrice = 0;
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

    public void calculateTotalPrice(ArrayList<Product> products) {
        double totalPrice = 0;
        for (Product pr : products) {
            totalPrice += pr.getPrice();
        }
        this.totalPrice = totalPrice;
    }

    public Calendar getDate() {
        return date;
    }
}
