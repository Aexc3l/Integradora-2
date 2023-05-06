package model;

import com.google.gson.Gson;
import java.io.*;
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
        searchEngine = new SearchEngine(productList);
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
                orderedProducts.add(product);
                if (getProductByName(productName) == null){
                    throw new Exception("Product not found: " + productName);
                }
            } catch (Exception e) {
                System.out.println("Product not found: " + productName);
            }
        }


        Order order = new Order(buyerName, orderedProducts);
        orderList.add(order);

    }

    public String searchProductsbyName(String value) {
        List<Product> matchingProducts = searchEngine.binarySearchOfProductUsingStringValue(value, "name");
        return matchingProducts.toString();
    }


    public  String searchProductsbyCategory(String value){
        List<Product> matchingProducts = searchEngine.binarySearchOfProductUsingStringValue(value, "category");
        return "";
    }

    public String searchProductsbyPrice(double value) {
        return "";
    }

    public String searchProductsbyAmount(int value) {
        return "";
    }

    public String searchProductsbyPurchasedAmount(int value) {
        return null;
    }


    public void exportData(String fileName) {
        try {
            dataManager.exportData(fileName,storeData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean importData(String fileName) {
        try {
            this.storeData = dataManager.importData(fileName, storeData);
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

    public ArrayList<Product> getProductList() {
        return productList;
    }

    private void updateProductQuantities(
            ArrayList<Product> orderedProducts,
            int option,
            int increase
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

    private Product getProductByName(String name) throws Exception {
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
            throw new Exception();
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
}
