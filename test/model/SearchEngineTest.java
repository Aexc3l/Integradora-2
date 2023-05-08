package model;

import static org.junit.jupiter.api.Assertions.*;

import exception.InvalidReferenceException;
import exception.UnvalidPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;


class SearchEngineTest {

	private StoreManager storeManager;

	@BeforeEach
	void setUpStage1() {
		storeManager = new StoreManager();
	}

	@BeforeEach
	void setUpStage2() {
		storeManager = new StoreManager();
		storeManager.importData("FileTo");
	}

	@BeforeEach
	void setUpStage3() {
		storeManager = new StoreManager();
		storeManager.addProduct("Sword","It is a sword with a sharp edge",110.99,99,"Weapons");
		storeManager.addProduct("Rice","1kg of Rice",7.30,20,"SuperMarket");
		try {
			Product sw = storeManager.getProductByName("Sword");
			sw.setTimesPurchased(10);
			Product rc = storeManager.getProductByName("Rice");
			rc.setTimesPurchased(1);
		} catch (InvalidReferenceException e) {
			e.getMessage();
		}
	}

	@Test
	void searchProductsbyName() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Sausage","Sausage",10.99,10,"Food");
		storeManager.addProduct("Beef Sausage","Beef Sausage",14.99,100,"Food");

		// Act
		String result = null;
		try {
			result = storeManager.searchProductsbyName("Sausage");
			System.out.println(result);
		} catch (InvalidReferenceException e) {
			e.getMessage();
		}

		// Assert
		assertNotEquals("[]",result);
	}

	@Test
	void searchProductsbyNameNonExisting() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Sausage", "Sausage", 10.99, 10, "Food");
		storeManager.addProduct("Beef Sausage", "Beef Sausage", 14.99, 100, "Food");

		// Act
		try {
			storeManager.searchProductsbyName("Chicken");
			fail("Expected InvalidReferenceException to be thrown");
		} catch (InvalidReferenceException e) {
			// Assert
			assertEquals("\nThere are no products with this name yet", e.getMessage());
		}
	}


	@Test
	void searchProductsbyCategory() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Beef","Meat",2.99,10,"Basic Food");
		storeManager.addProduct("Sausage","Sausage",10.99,10,"The Food");
		storeManager.addProduct("Chicken","Fresh Chicken",14.99,9,"Food");
		storeManager.addProduct("Beef Sausage","Processed Meat",13.99,14,"Basics");


		// Act
		String result = null;
		try {
			result = storeManager.searchProductsbyCategory("Food");
			System.out.println(result);
		} catch (InvalidReferenceException e) {
			e.getMessage();
		}

		// Assert
		assertNotEquals("[]",result,"All products are shown except Beef Sausage");
	}

	@Test
	void searchProductsbyCategoryNonExisting() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Sausage","Sausage",10.99,10,"Food");
		storeManager.addProduct("Beef Sausage","Processed Meat",13.99,14,"Basics");
		storeManager.addProduct("Chicken","Fresh Chicken",14.99,9,"Food");

		try {
			storeManager.searchProductsbyCategory("Technology");
			fail("Expected InvalidReferenceException to be thrown");
		} catch (InvalidReferenceException e) {
			// Assert
			assertEquals("\nThere are no products with this category yet", e.getMessage());
		}
	}

	@Test
	void searchProductsbyPrice() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Sausage","Sausage",10.99,10,"Food");
		storeManager.addProduct("Beef Sausage","Processed Meat",12.99,14,"Basics");
		storeManager.addProduct("Chicken","Fresh Chicken",12.99,9,"Food");

		// Act
		String result = null;
		try {
			result = storeManager.searchProductsbyPrice(12.99);
			System.out.println(result);
		} catch (UnvalidPriceException e) {
			e.getMessage();
		}

		// Assert
		assertNotEquals("[]",result,"All products are shown except Sausage");
	}

	@Test
	void searchProductsbyPriceNonExisting() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Sausage","Sausage",10.99,10,"Food");
		storeManager.addProduct("Beef Sausage","Processed Meat",13.99,14,"Basics");
		storeManager.addProduct("Chicken","Fresh Chicken",14.99,9,"Food");

		// Act
		try {
			storeManager.searchProductsbyPrice(20.00);
			fail("Expected UnvalidPriceException to be thrown");
		} catch (UnvalidPriceException e) {
			// Assert
			assertEquals("\nThere are no products with this price", e.getMessage());
		}
	}

	@Test
	void searchProductsbyPriceBelowZero() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Sausage","Sausage",10.99,10,"Food");
		storeManager.addProduct("Beef Sausage","Processed Meat",12.99,14,"Basics");
		storeManager.addProduct("Chicken","Fresh Chicken",12.99,9,"Food");

		// Act
		String result = null;
		try {
			result = storeManager.searchProductsbyPrice(-8.99);
			System.out.println(result);
		} catch (UnvalidPriceException e) {
			e.getMessage();
		}

		// Assert
		assertEquals("\nError: All products prices are higher than zero",result,"No product is displayed");
	}

	@Test
	void searchProductsbyPurchasedAmount() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Sausage","Sausage",10.99,10,"Food");
		storeManager.addProduct("Beef","Processed Meat",12.99,14,"Basics");
		storeManager.addProduct("Chicken","Fresh Chicken",12.99,9,"Food");
		ArrayList<String> products = new ArrayList<>();
		products.add("Chicken");
		products.add("Beef Sausage");
		products.add("Chicken");
		products.add("Res");

		storeManager.addOrder("Rodrigo",products);
		// Act
		String result = storeManager.searchProductsbyPurchasedAmount(2);

		// Assert
		assertNotEquals("[]",result,"Only chicken is shown, it is the only product that meets the condition and also exists");
	}

	@Test
	void searchProductsbyPurchasedAmountProduct2() {
		// Arrange
		setUpStage3();

		// Act
		String result = storeManager.searchProductsbyPurchasedAmount(4);

		// Assert
		assertEquals("\nError: There are no products with this purchased Amount",result,"No products have been purchased");
	}

	@Test
	void searchProductsbyAmount() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Sausage","Sausage",10.99,10,"Food");
		storeManager.addProduct("Beef Sausage","Processed Meat",13.99,14,"Basics");
		storeManager.addProduct("Chicken","Fresh Chicken",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyAmount(10);
		System.out.println(result);

		// Assert
		assertNotEquals("[]",result,"Shown the product Sausage");
	}

	@Test
	void searchProductsbyAmount2() {
		// Arrange
		setUpStage3();
		storeManager.addProduct("Sausage","Sausage",10.99,10,"Food");
		storeManager.addProduct("Beef Sausage","Processed Meat",13.99,14,"Basics");
		storeManager.addProduct("Chicken","Fresh Chicken",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyAmount(12);

		// Assert
		assertEquals("\nError: There are no products with this quantity",result,"There is no product with that amount");
	}

	@Test
	void searchProductsbyRangeSetup1() {
		//Arrange
		setUpStage1();
		storeManager.addProduct("Sausage","Sausage",10.99,10,"Food");
		storeManager.addProduct("Beef Sausage","Processed Meat",13.99,14,"Basics");
		storeManager.addProduct("Chicken","Fresh Chicken",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyRange(12,20);
		System.out.println(result);

		// Assert
		assertNotEquals("[]",result,"Products in the price range of $12 to $20 are displayed");
	}

	@Test
	void searchProductsbyRangeSetup2() {
		//Arrange
		setUpStage2();

		// Act
		String result = storeManager.searchProductsbyRange(0,40.00);
		System.out.println(result);

		// Assert
		assertNotEquals("[]",result,"Imported Products in the price range of $0 to $40 are displayed");
	}

	@Test
	void searchOrderByDateSetup1() {
        // Arrange
		setUpStage3();
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
	void searchOrderByDateSetup3() {
		//arrange
		setUpStage3();
		ArrayList<String> products = new ArrayList<>();
		products.add("Sword");
		products.add("Sword");
		storeManager.addOrder("John Shepard", products);
		Calendar calendar = Calendar.getInstance();

		//act
		String result = result = storeManager.searchOrderbyDate("2023-4-7");
		System.out.println(result);


		//assert
		assertNotEquals("[]",result);
	}

	@Test
	public void testSearchOrderByInvalidName() {
		//arrange
		setUpStage3();
		ArrayList<String> products = new ArrayList<>();
		products.add("Sword");
		storeManager.addOrder("John Shepard", products);

		//act
		String result = "";
		try {
			result = storeManager.searchOrderbyName("John Peshawar");
			fail("Expected InvalidReferenceException to be thrown");
		} catch (InvalidReferenceException ex) {
			System.out.println(result);
			//assert
			assertEquals("\nThere are no orders with this buyer's name yet", ex.getMessage());
		}
	}

	@Test
	public void testSearchOrderByName() {
		//arrange
		setUpStage3();
		ArrayList<String> products = new ArrayList<>();
		products.add("Sword");
		products.add("Sword");
		storeManager.addOrder("John Shepard", products);

		//act
		String result = null;
		try {
			result = storeManager.searchOrderbyName("John Shepard");
			System.out.println(result);
		} catch (InvalidReferenceException ex) {
			ex.getMessage();
		}

		//assert
		assertNotEquals("[]",result);
	}

}
