package model;

import java.util.ArrayList;

public class StoreData {

    private ArrayList<Product> products;
    private ArrayList<Order> orders;

    public StoreData(ArrayList<Product> products, ArrayList<Order> orders) {
        this.products = products;
        this.orders = orders;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
