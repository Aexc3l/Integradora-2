package model;

import java.util.*;
public class Order {

    private String buyerName;
    private ArrayList<Product> productList;
    private double totalPrice;
    private Calendar date;

    public Order(String buyerName, ArrayList<Product> productList, double totalPrice) {
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
        return totalPrice; //Para que se de el precio total, se deben sumar los precios de cada producto
    }

    public Calendar getDate() {
        return date;
    }

}
