package model;

import com.google.gson.Gson;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class StoreManager {

    private ArrayList<Product> productList;
    private ArrayList<Order> orderList;
    private Gson gson = new Gson();
    private StoreData storeData;

    private DataManager dataManager;

    //https://mvnrepository.com/artifact/com.google.code.gson/gson/2.10.1

    public StoreManager() {
        productList = new ArrayList<>();
        orderList = new ArrayList<>();
        storeData = new StoreData(productList, orderList);
        dataManager = new DataManager();
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

        updateProductQuantities(orderedProducts, 1, 0);
    }

    /*Search engines to implement Binary Search
     * binarySearchProductsByName
     * binarySearchProductsByPrice
     * binarySearchProductsByCategory
     * binarySearchProductsByTimesPurchased
     * binarySearchOrdersByBuyerName
     * binarySearchOrdersByPrice*/

    public void exportData(String fileName) {
        try {
            dataManager.exportData(fileName,storeData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean importData(String fileName) {
        return true;
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
}
