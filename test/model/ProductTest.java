package model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class ProductTest {

    Product p = new Product("Apple", "Red fruit", 1.50, 10, "Fruits");

	@Test
    public void testGetName() {
        assertEquals("Apple", p.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Red fruit", p.getDescription());
    }

    @Test
    public void testGetPrice() {
        assertEquals(1.50, p.getPrice());
    }

    @Test
    public void testGetQuantity() {
        assertEquals(10, p.getQuantity());
    }

    @Test
    public void testGetCategory() {
        assertEquals("Fruits", p.getCategory());
    }

    @Test
    public void testGetTimesPurchased() {
        assertEquals(0, p.getTimesPurchased());
    }

    @Test
    public void testSetPrice() {
        p.setPrice(2.00);
        assertEquals(2.00, p.getPrice());
    }

    @Test
    public void testSetQuantity() {
        p.setQuantity(20);
        assertEquals(20, p.getQuantity());
    }

    @Test
    public void testSetCategory() {
        p.setCategory("Fruits and Vegetables");
        assertEquals("Fruits and Vegetables", p.getCategory());
    }

    @Test
    public void testSetTimesPurchased() {
        p.setTimesPurchased(5);
        assertEquals(5, p.getTimesPurchased());
    }

    @Test
    public void testIncreaseQuantity() {
        p.increaseQuantity(5);
        assertEquals(15, p.getQuantity());
    }

    @Test
    public void testDecreaseQuantity() {
        p.decreaseQuantity(5);
        assertEquals(5, p.getQuantity());
    }

    @Test
    public void testIncreaseTimesPurchased() {
        p.increaseTimesPurchased();
        assertEquals(1, p.getTimesPurchased());
    }
	
}
