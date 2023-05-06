package model;

import static org.junit.jupiter.api.Assertions.*;

import model.Product;
import model.StoreManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class SearchEngineTest {

	private StoreManager storeManager;

	@BeforeEach
	void setUpStage1() {
		storeManager = new StoreManager();
	}

	@Test
	void searchProductsbyName() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Salchicha",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Salchicha de Res",14.99,100,"Food");

		// Act
		String result = storeManager.searchProductsbyName("Salchicha");

		// Assert
		assertNotEquals("",result);
	}
	@Test
	void searchProductsbyNameNonExisting() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Salchicha",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Salchicha de Res",14.99,100,"Food");

		// Act
		String result = storeManager.searchProductsbyName("Arroz");

		// Assert
		assertNotEquals("",result);
	}

	@Test
	void searchProductsbyCategory() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",1.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",12.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyCategory("Food");
		System.out.println(result);

		// Assert
		assertNotEquals("",result,"Se muestran todos los productos menos Salchicha de Res");
	}

	@Test
	void searchProductsbyCategoryNonExisting() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyCategory("Silence");
		System.out.println(result);

		// Assert
		assertNotEquals("",result,"No hay ningun producto con esa cantidad");
	}

	@Test
	void searchProductsbyPrice() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",12.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",12.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyPrice(12.99);
		System.out.println(result);

		// Assert
		assertEquals("Product{name='Salchicha de Res', description='Carne Procesada', price=12.99, quantity=14, category='Basics', timesPurchased=0}, Product{name='Pollo', description='Pollo Fresco', price=12.99, quantity=9, category='Food', timesPurchased=0}",result,"Se muestran todos los productos menos Salchicha");
	}

	@Test
	void searchProductsbyPriceNonExisting() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyPrice(12.99);
		System.out.println(result);

		// Assert
		assertNotEquals("",result,"No hay ningun producto con esa cantidad");
	}

}
