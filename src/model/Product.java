package model;

public class Product {

    private final String name;
    private final String description;
    private double price;
    private int quantity;
    private String category;
    private int timesPurchased;

    public Product(
            String name,
            String description,
            double price,
            int quantity,
            String category
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.timesPurchased = 0;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public int getTimesPurchased() {
        return timesPurchased;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public void increaseTimesPurchased() {
        this.timesPurchased++;
    }

    public void decreaseTimesPurchased() {
        this.timesPurchased--;
    }
}
