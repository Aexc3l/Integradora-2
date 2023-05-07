package model;

import static org.junit.jupiter.api.Assertions.*;

import exception.InvalidReferenceException;
import exception.UnvalidPriceException;
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

	@BeforeEach
	void setUpStage2() {
		storeManager = new StoreManager();
		storeManager.addProduct("Salchicha","Salchicha",10.99,10,"Food");
	}

	@Test
	void searchProductsbyName() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Salchicha",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Salchicha de Res",14.99,100,"Food");

		// Act
		String result = null;
		try {
			result = storeManager.searchProductsbyName("Salchicha");
		} catch (InvalidReferenceException e) {
			e.getMessage();
		}

		// Assert
		assertNotEquals("",result);
	}

	@Test
	void searchProductsbyNameNonExisting() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha", "Salchicha", 10.99, 10, "Food");
		storeManager.addProduct("Salchicha de Res", "Salchicha de Res", 14.99, 100, "Food");

		// Act
		try {
			storeManager.searchProductsbyName("Pollo");
			fail("Expected InvalidReferenceException to be thrown");
		} catch (InvalidReferenceException e) {
			// Assert
			assertEquals("There are no products with this name yet", e.getMessage());
		}
	}


	@Test
	void searchProductsbyCategory() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Res","Carne",2.99,10,"Basic Food");
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"The Food");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");


		// Act
		String result = null;
		try {
			result = storeManager.searchProductsbyCategory("Food");
		} catch (InvalidReferenceException e) {
			e.getMessage();
		}

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

		try {
			storeManager.searchProductsbyCategory("Technology");
			fail("Expected InvalidReferenceException to be thrown");
		} catch (InvalidReferenceException e) {
			// Assert
			assertEquals("There are no products with this category yet", e.getMessage());
		}
	}

	@Test
	void searchProductsbyPrice() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",12.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",12.99,9,"Food");

		// Act
		String result = null;
		try {
			result = storeManager.searchProductsbyPrice(12.99);
		} catch (UnvalidPriceException e) {
			e.getMessage();
		}

		// Assert
		assertEquals("[Product{name='Salchicha de Res', description='Carne Procesada', price=12.99, quantity=14, category='Basics', timesPurchased=0}, Product{name='Pollo', description='Pollo Fresco', price=12.99, quantity=9, category='Food', timesPurchased=0}]",result,"Se muestran todos los productos menos Salchicha");
	}

	@Test
	void searchProductsbyPriceNonExisting() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act

		try {
			storeManager.searchProductsbyPrice(20.00);
			fail("Expected UnvalidPriceException to be thrown");
		} catch (UnvalidPriceException e) {
			// Assert
			assertEquals("There are no products with this price", e.getMessage());
		}
	}

	@Test
	void searchProductsbyPriceBelowZero() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",12.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",12.99,9,"Food");

		// Act
		String result = null;
		try {
			result = storeManager.searchProductsbyPrice(-8.99);
		} catch (UnvalidPriceException e) {
			e.getMessage();
		}

		// Assert
		assertEquals("Error: All products prices are higher than zero",result,"No se muestra ningun producto");
	}

	@Test
	void searchProductsbyPurchasedAmount() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",12.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",12.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyPurchasedAmount(129);
		System.out.println(result);

		// Assert
		assertEquals("",result,"Se muestran todos los productos menos Salchicha");
	}

	@Test
	void searchProductsbyPurchasedAmountProduct2() {
		// Arrange
		setUpStage2();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyPurchasedAmount(12);
		System.out.println(result);

		// Assert
		assertNotEquals("",result,"No hay ningun producto con esa cantidad");
	}

	@Test
	void searchProductsbyAmount() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyAmount(12);
		System.out.println(result);

		// Assert
		assertNotEquals("",result,"No hay ningun producto con esa cantidad");
	}

	@Test
	void searchProductsbyAmount2() {
		// Arrange
		setUpStage2();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyAmount(12);
		System.out.println(result);

		// Assert
		assertNotEquals("",result,"No hay ningun producto con esa cantidad");
	}

	@Test
	void searchProductsbyRangesetup1() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyAmount(12);
		System.out.println(result);

		// Assert
		assertNotEquals("",result,"No hay ningun producto con esa cantidad");
	}

	@Test
	void searchProductsbyRangesetup2() {
		// Arrange
		setUpStage2();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyRange(1.99,12.00);
		System.out.println(result);

		// Assert
		assertNotEquals("",result,"No hay ningun producto con esa cantidad");
	}

	@Test
	void searchOrderbyNamesetup1() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyAmount(12);
		System.out.println(result);

		// Assert
		assertNotEquals("",result,"No hay ningun producto con esa cantidad");
	}

	@Test
	void searchOrderbyDatesetup1() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyAmount(12);
		System.out.println(result);

		// Assert
		assertNotEquals("",result,"No hay ningun producto con esa cantidad");
	}

	@Test
	void searchOrderbyDatesetup2() {
		// Arrange
		setUpStage2();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyRange(1.99,12.00);
		System.out.println(result);

		// Assert
		assertNotEquals("",result,"No hay ningun producto con esa cantidad");
	}
}
