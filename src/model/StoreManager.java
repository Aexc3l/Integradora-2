package model;

import com.google.gson.Gson;
import exception.InvalidReferenceException;
import exception.UnvalidPriceException;

import java.io.*;
import java.text.*;
import java.util.*;

public class StoreManager {

    private ArrayList<Product> productList;
    private ArrayList<Order> orderList;
    private Gson gson = new Gson();
    private StoreData storeData;

    private SearchEngine searchEngine;

    private DataManager dataManager;

    //https://mvnrepository.com/artifact/com.google.code.gson/gson/2.10.1

    public StoreManager() {
        productList = new ArrayList<>();
        orderList = new ArrayList<>();
        storeData = new StoreData(productList, orderList);
        dataManager = new DataManager();
        searchEngine = new SearchEngine(productList, orderList);
    }

    public void addProduct(
            String name,
            String description,
            double price,
            int quantity,
            String category
    ) {
        Product product = new Product(name, description, price, quantity, category);

        productList.add(product);
    }

    public boolean removeProduct(String name) {
        int i = getProductIndexByName(name);
        if (i != -1) {
            productList.remove(i);
            return true;
        } else {
            return false;
        }
    }

    public void addOrder(String buyerName, ArrayList<String> productNames) {
        ArrayList<Product> orderedProducts = new ArrayList<>();

        for (String productName : productNames) {
            try {
                Product product = getProductByName(productName);
                if (getProductByName(productName) == null){
                    throw new Exception("\nProduct not found: " + productName);
                }else {
                    orderedProducts.add(product);
                }
            } catch (Exception e) {
                System.out.println("\nProduct not found: " + productName);
            }
        }
        updateProductQuantities(orderedProducts,1);
        Order order = new Order(buyerName, orderedProducts);
        orderList.add(order);
    }

    public String searchProductsbyName(String value) throws InvalidReferenceException {
        ArrayList<Product> matchingProducts = searchEngine.searchByProductString(value, "name");
        if (matchingProducts.size() == 0){
            throw new InvalidReferenceException("\nThere are no products with this name yet");
        }
        return matchingProducts.toString();
    }


    public  String searchProductsbyCategory(String value) throws InvalidReferenceException{
        ArrayList<Product> matchingProducts = searchEngine.searchByProductString(value, "category");
        if (matchingProducts.size() == 0){
            throw new InvalidReferenceException("\nThere are no products with this category yet");
        }else {
            return matchingProducts.toString();
        }
    }

    public String searchProductsbyPrice(double value) throws UnvalidPriceException {
        ArrayList<Product> matchingProducts = new ArrayList<>();
        if (value < -1){
            return "\nError: All products prices are higher than zero";
        }else {
            matchingProducts = searchEngine.searchByProductValue(value, "price");
            if (matchingProducts.size() == 0){
                throw new UnvalidPriceException("\nThere are no products with this price");
            }else {
                return matchingProducts.toString();
            }
        }
    }

    public String searchProductsbyPurchasedAmount(int value) {
        if (value <= 0){
            return "Product's Purchased Amount must be higher than zero";
        }else {
            ArrayList<Product> matchingProducts = new ArrayList<>();
            matchingProducts = searchEngine.searchByProductValue((double) value,"timesPurchased");
            if (matchingProducts.size() == 0){
                return "\nError: There are no products with this purchased Amount";
            }else {
                return matchingProducts.toString();
            }
        }
    }

    public String searchProductsbyAmount(int value) {
        if (value <= 0){
            return "Product's Amount must be higher than zero";
        }else {
            ArrayList<Product> matchingProducts = new ArrayList<>();
            matchingProducts = searchEngine.searchByProductValue((double) value,"quantity");
            if (matchingProducts.size() == 0){
                return "\nError: There are no products with this quantity";
            }else {
                return matchingProducts.toString();
            }
        }
    }

    public boolean removeOrder(String name) {
        int i = getOrderIndexByName(name);
        if (i != -1) {
            orderList.remove(i);
            return true;
        } else {
            return false;
        }
    }

    public String searchOrderbyPrice(double value) {
        if (value <= 0){
            return "Order's Total Price must be higher than zero";
        }else {
            ArrayList<Order> matchingOrders = new ArrayList<Order>();
            matchingOrders = searchEngine.searchByOrderValue(value,"totalPrice");
            if (matchingOrders.size() == 0){
                return "\nError: There are no orders with this price";
            }else {
                return matchingOrders.toString();
            }
        }
    }

    public String searchOrderbyName(String value) throws InvalidReferenceException {
        ArrayList<Order> matchingOrders = searchEngine.searchByOrderString(value, "buyerName");
        if (matchingOrders.size() == 0){
            throw new InvalidReferenceException("\nThere are no orders with this buyer's name yet");
        }
        return matchingOrders.toString();
    }


    public String searchOrderbyDate(String date) {
        Calendar calendarDate = turnToCalendar(date);
        List<Order> matchingOrders = searchEngine.searchByOrderDate(calendarDate, "date");
        return matchingOrders.toString();
    }

    public void exportData(String fileName) {
        try {
            storeData.setOrders(orderList);
            storeData.setProducts(productList);
            dataManager.exportData(fileName,storeData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean importData(String fileName) {
        try {
            this.storeData = dataManager.importData(fileName, storeData);
            this.productList = storeData.getProducts();
            this.orderList = storeData.getOrders();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("\nThe file was not found.\n");
            return false;
        }
    }

    private int getProductIndexByName(String name) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private int getOrderIndexByName(String name) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getBuyerName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    private void updateProductQuantities(
            ArrayList<Product> orderedProducts,
            int option
    ) {
        for (Product product : orderedProducts) {
            int index = getProductIndexByName(product.getName());
            if (index != -1) {
                Product p = productList.get(index);
                if (option == 1) {
                    p.decreaseQuantity(1);
                    p.increaseTimesPurchased();
                } else if (option == 2) {
                    p.increaseQuantity(1);
                    p.decreaseTimesPurchased();
                }
            }
        }
    }

    public Product getProductByName(String name) throws InvalidReferenceException {
         Product sendCoincidence = null;
        for (Product product : productList) {
            if (product.getName().equals(name)) {
                sendCoincidence = product;
                return sendCoincidence;
            }
        }
        if (sendCoincidence == null){
          return null;
        }
            throw new InvalidReferenceException("Product does not exist");
    }

    private double searchProductValue(String name) {
        for (Product pr : productList) {
            if (pr.getName().equals(name)) {
                return pr.getPrice();
            }
        }
        return 0;
    }


    public String searchProductsbyRange(double valueMin, double valueMax) {
        return "";
    }
    private static Calendar turnToCalendar(String date) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(date));
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
