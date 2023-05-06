package model;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.ArrayList;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class PersistenceTest {

	private StoreManager storeManager;

	@BeforeEach
	void setupStage1() {
		storeManager = new StoreManager();
	}

	@BeforeEach
	void setupStage2() {
		storeManager = new StoreManager();
		storeManager.addProduct("Sword", "It is a sword with a sharp edge",110.99,90,"Weapons");
		ArrayList<String> products = new ArrayList<>();
		products.add("Sword");
		products.add("Sword");
		products.add("Sword");
		products.add("Sword");
		products.add("Sword");
		products.add("Sword");
		products.add("Sword");
		products.add("Sword");
		products.add("Sword");
		products.add("Sword");
		storeManager.addOrder("Rodrigo",products);
	}

	@Test
	void exportDataTest() {
		// Arrange
		setupStage1();
		ArrayList<String> products = new ArrayList<>();
		products.add("Pollo");
		storeManager.addProduct("Pollo", "It's chicken", 10, 10, "Food");
		storeManager.addOrder("Pedro", products);

		// Act
		storeManager.exportData("ExampleDataTest");

		// Assert
		File file = new File("data/ExampleDataTest.json");
		assertTrue(file.exists(), "The file should exist");
	}

	@Test
	void importDataTestNoExistence() {
		// Arrange
		setupStage1();

		// Act
		boolean importResult = storeManager.importData("JanethFiles");
		int productListSize = storeManager.getProductList().size();

		// Assert
		assertFalse(importResult, "Importing the same data twice should return false");
		assertEquals(0, productListSize, "Product list should be empty after failed import");
	}

	@Test
	void importDataTest() {
		// Arrange
		setupStage1();

		// Act
		boolean importResult = storeManager.importData("Created");
		int productListSize = storeManager.getProductList().size();

		// Assert
		assertTrue(importResult, "Importing the data  should return true");
		assertEquals(0, productListSize, "Product list should be empty after failed import");
	}


}
