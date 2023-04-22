package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    private Controller mercadoLibre;

    @BeforeEach
    void setUp() {
        mercadoLibre = new Controller();
    }


    @Test
    public void testAddProduct(){
        //Arrange
        String name = "ASUS ROG Z130";
        String description = "It's just a product";
        double price = 10.0;
        int quantity = 5;
        String category = "Home Appliances";

        //Act
        mercadoLibre.addProduct(name, description, price, quantity, category);

        //Assert
        ArrayList<Product> productList = mercadoLibre.getProductList();
        assertEquals(1, productList.size());
        assertEquals(name, productList.get(0).getName());
    }

    @Test
    public void testSearchProductValueWithEmptyList() {
        //Arrange

        //Act

        //Assert

    }
}