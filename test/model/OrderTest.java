package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Calendar;
class OrderTest {

  @Test
    public void testGetBuyerName() {
        ArrayList<Product> productList = new ArrayList<>();
        Product product1 = new Product("Product 1", "Description 1", 10.0, 1, "Category 1");
        productList.add(product1);
        Order order = new Order("Buyer Name", productList);
        assertEquals("Buyer Name", order.getBuyerName());
    }

    @Test
    public void testSetBuyerName() {
        ArrayList<Product> productList = new ArrayList<>();
        Product product1 = new Product("Product 1", "Description 1", 10.0, 1, "Category 1");
        productList.add(product1);
        Order order = new Order("Buyer Name", productList);
        order.setBuyerName("New Buyer Name");
        assertEquals("New Buyer Name", order.getBuyerName());
    }

    @Test
    public void testGetProductList() {
        ArrayList<Product> productList = new ArrayList<>();
        Product product1 = new Product("Product 1", "Description 1", 10.0, 1, "Category 1");
        Product product2 = new Product("Product 2", "Description 2", 20.0, 2, "Category 2");
        productList.add(product1);
        productList.add(product2);
        Order order = new Order("Buyer Name", productList);
        assertEquals(productList, order.getProductList());
    }

    @Test
    public void testGetTotalPrice() {
        ArrayList<Product> productList = new ArrayList<>();
        Product product1 = new Product("Product 1", "Description 1", 10.0, 1, "Category 1");
        Product product2 = new Product("Product 2", "Description 2", 20.0, 2, "Category 2");
        productList.add(product1);
        productList.add(product2);
        Order order = new Order("Buyer Name", productList);
        assertEquals(50.0, order.getTotalPrice(), 0.001);
    }

    @Test
    public void testGetDate() {
        ArrayList<Product> productList = new ArrayList<>();
        Product product1 = new Product("Product 1", "Description 1", 10.0, 1, "Category 1");
        productList.add(product1);
        Order order = new Order("Buyer Name", productList);
        Calendar calendar = Calendar.getInstance();
        assertEquals(calendar.get(Calendar.YEAR), order.getDate().get(Calendar.YEAR));
        assertEquals(calendar.get(Calendar.MONTH), order.getDate().get(Calendar.MONTH));
        assertEquals(calendar.get(Calendar.DATE), order.getDate().get(Calendar.DATE));
    }
	
}
