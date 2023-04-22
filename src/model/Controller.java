package model;

import com.google.gson.Gson;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Controller {

    private final ArrayList<Product> productList;
    private final ArrayList<Order> orderList;

    private final Gson gson = new Gson();

    //https://mvnrepository.com/artifact/com.google.code.gson/gson/2.10.1

    public Controller() {
        productList = new ArrayList<>();
        orderList = new ArrayList<>();
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

    public void addOrder(String buyerName, ArrayList<String> productNames) {
        ArrayList<Product> orderedProducts = new ArrayList<>();

        //Read the product array, check that the name exists, and add the product to the order product array

        double totalPrice = calculateTotalPrice(orderedProducts);
        Order order = new Order(buyerName, orderedProducts, totalPrice);
        orderList.add(order);

        updateProductQuantities(orderedProducts);
    }

    private Product getProductByName(String name) {
        for (Product product : productList) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    private double calculateTotalPrice(ArrayList<Product> products) {
        double totalPrice = 0;
        for (Product pr : products) {
            totalPrice += searchProductValue(pr.getName());
        }
        return totalPrice;
    }

    private double searchProductValue(String name) {
        for (Product pr : productList) {
            if (pr.getName().equals(name)) {
                return pr.getPrice();
            }
        }
        return 0;
    }

    /*Search engines to implement Binary Search
     * binarySearchProductsByName
     * binarySearchProductsByPrice
     * binarySearchProductsByCategory
     * binarySearchProductsByTimesPurchased
     * binarySearchOrdersByBuyerName
     * binarySearchOrdersByPrice*/

    private void updateProductQuantities(ArrayList<Product> orderedProducts) {
        //Every time a product is found in an order, the available quantity of that product is updated (Decreases)
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void exportData(String fileName) {
        File projectDir = new File(System.getProperty("user.dir"));
        File dataDirectory = new File(projectDir + "/data");
        File newFile = new File(dataDirectory + "/" + fileName + ".json");

        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
            System.out.println(dataDirectory.exists());
        }

        StoreData exportData = new StoreData();
        exportData.setProducts(productList);
        exportData.setOrders(orderList);

        String json = gson.toJson(exportData);

        try {
            FileOutputStream fos = new FileOutputStream(newFile);
            fos.write(json.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean importData(String fileName) {
        try {
            File projectDir = new File(System.getProperty("user.dir"));
            File dataDirectory = new File(projectDir + "/data");
            File file = new File(dataDirectory + "/" + fileName + ".json");

            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            StoreData data = gson.fromJson(reader, StoreData.class);

            for (Product product : data.getProducts()) {
                productList.add(
                        new Product(
                                product.getName(),
                                product.getDescription(),
                                product.getPrice(),
                                product.getQuantity(),
                                product.getCategory()
                        )
                );
            }

            for (Order order : data.getOrders()) {
                ArrayList<Product> orderedProducts = new ArrayList<>();
                for (Product productData : order.getProductList()) {
                    Product product = getProductByName(productData.getName());
                    if (product != null) {
                        orderedProducts.add(product);
                    }
                }
                double totalPrice = calculateTotalPrice(orderedProducts);
                Order newOrder = new Order(
                        order.getBuyerName(),
                        orderedProducts,
                        totalPrice
                );
                orderList.add(newOrder);
                updateProductQuantities(orderedProducts);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
