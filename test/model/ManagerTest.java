package model;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
public class ManagerTest{
    StoreManager store = new StoreManager();
    @Test
    public void testAddProductValid() {
        //Se agrega un producto a la lista 
        String name = "Celular";
        String description = "Alta gama, pantalla 4k bro";
        double price = 1000;
        int quantity = 10;
        String category = "Electronics";
        
        
        store.addProduct(name, description, price, quantity, category);
        //se verifica si los valores son correctos
        assertEquals(store.getProductList().size(), 1);
        assertEquals(store.getProductList().get(0).getName(), name);
        assertEquals(store.getProductList().get(0).getDescription(), description);
        assertEquals(store.getProductList().get(0).getPrice(), price, 0.01);
        assertEquals(store.getProductList().get(0).getQuantity(), quantity);
        assertEquals(store.getProductList().get(0).getCategory(), category);
    }
    @Test
    public void testAddProductInvalid() {
        //Se agrega un producto a la lista con valores invalidos
        String name = "";
        String description = "";
        double price = -1.0;
        int quantity = -1;
        String category = "";
        
        
        store.addProduct(name, description, price, quantity, category);
        //se "verifican" los valores
        assertEquals(store.getProductList().size(), 0);
    }
    @Test
    public void testRemoveProductGood() {
        // Se agrega un producto a la lista
        String name = "Pan";
        String description = "Pan con pan";
        double price = 10.0;
        int quantity = 5;
        String category = "Panaderia";
        store.addProduct(name, description, price, quantity, category);

        // Se llama al método removeProduct para eliminar el producto agregado
        boolean result = store.removeProduct(name);

        // Se espera que la eliminación sea exitosa y que el método devuelva true
        assertTrue(result);
        assertEquals(0, store.getProductList().size()); 
        // Se espera que la lista de productos esté vacía después de la eliminación
    }
    @Test
    public void testRemoveProductFail() {
        // Se intenta eliminar un producto que no existe en la lista
        String name = "Nada";
        boolean result = store.removeProduct(name);

        // Se espera que la eliminación falle y que el método devuelva false
        assertFalse(result);
        assertEquals(0, store.getProductList().size()); 
        // Se espera que la lista de productos siga vacía después de la eliminación
    }
    @Test
    public void testAddOrderValid() {
        // crear productos para la orden
        Product product1 = new Product("Camiseta", "azul ", 25, 2, "Ropa");
        Product product2 = new Product("Pantalon", "negro", 30, 1, "Ropa");
        ArrayList<String> productNames = new ArrayList<>();
        productNames.add("Camiseta");
        productNames.add("Pantalon");

        // crear la orden
        store.addOrder("Sebastian", productNames);

        // verificar si la orden fue añadida a la lista
        assertEquals(1, store.getOrderList().size());

        // mirar si la orden tiene los productos con todo correcto
        Order order = store.getOrderList().get(0);
        assertEquals("John Doe", order.getBuyerName());
        assertEquals(2, order.getProductList().size());
        assertTrue(order.getProductList().contains(product1));
        assertTrue(order.getProductList().contains(product2));
        assertEquals(70.0, order.getTotalPrice(), 0.001);

        // ver si la cantidad de productos se actualizó como debe
        assertEquals(1, product1.getQuantity());
        assertEquals(0, product2.getQuantity());
    }
    @Test
    public void testRemoveOrderExist() {
        // Crear una orden y agregarla a la lista
        Order order = new Order("Comprador 1", new ArrayList<Product>());
        store.addOrder("Comprador 1", new ArrayList<String>());

        // Eliminar la orden
        boolean result = store.removeOrder(order.getBuyerName());

        // Verificar que la orden fue eliminada y que el método devuelve true
        assertTrue(result);
        assertFalse(store.contains(order));
    }
    @Test
    public void testRemoveOrderNonExist() {
        // Crear una orden y agregarla a la lista
        Order order = new Order("Comprador 1", new ArrayList<Product>());
        store.addOrder(order.getBuyerName(),order.getOrderList());

        // Eliminar una orden inexistente
        boolean result = store.removeOrder(order.getBuyerName() + 1);

        // Verificar que la orden no fue eliminada y que el método devuelve false
        assertFalse(result);
        assertTrue(store.contains(order));
    }

}