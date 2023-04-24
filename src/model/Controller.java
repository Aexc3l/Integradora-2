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
            } catch (Exception e) {
                System.out.println("Product not found: " + productName);
            }
        }

        double totalPrice = calculateTotalPrice(orderedProducts);
        Order order = new Order(buyerName, orderedProducts, totalPrice);
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
                    Product product = null;
                    try {
                        product = getProductByName(productData.getName());
                    } catch (Exception e) {
                        throw new RuntimeException(e); //Replace with Custom Exception
                    }
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
                updateProductQuantities(orderedProducts, 1, 0);
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file does not exists");
            return false;
        }
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
        for (Product product : productList) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        throw new Exception();//Replace with Custom Exception
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
}
