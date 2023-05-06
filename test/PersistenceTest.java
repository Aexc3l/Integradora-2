package test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.ArrayList;

import model.Order;
import model.Product;
import model.StoreManager;
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
		storeManager.exportData("ExportDataTest");

		// Assert
		File file = new File("data/ExportDataTest.json");
		assertTrue(file.exists(), "The file should exist");
	}

	@Test
	void importDataTest() {
		// Arrange
		setupStage1();

		// Act
		storeManager.importData("ExampleDataTest");

		// Assert
		assertNotNull(storeManager.getProductList());
	}


}
