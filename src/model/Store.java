package model;
import java.util.*;
public class Store {
    private ArrayList<Product> productList;
    private ArrayList<Order> orderList;

    public Store() {
        productList = new ArrayList<>();
        orderList = new ArrayList<>();
    }

    public void addProduct(String name, String description, double price, int quantity, String category) {

        Product product = new Product(name, description, price, quantity, category);

        productList.add(product);
    }

    public void addOrder(String buyerName, ArrayList<String> productNames) {
        ArrayList<Product> orderedProducts = new ArrayList<>();

        //Lee el arreglo de productos, comprueba que el nombre exista y agrega el producto al arreglo de productos de la orden

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
    /*Buscadores a implementar Binary Search
    * binarySearchProductsByName
    * binarySearchProductsByPrice
    * binarySearchProductsByCategory
    * binarySearchProductsByTimesPurchased
    * binarySearchOrdersByBuyerName
    * binarySearchOrdersByPrice*/


    private void updateProductQuantities(ArrayList<Product> orderedProducts) {
        //Cada vez que se encuentra un producto en un pedido, se actualiza la cantidad disponible de ese producto (Disminuye)
    }
}
