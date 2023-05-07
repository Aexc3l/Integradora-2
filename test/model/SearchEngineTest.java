package model;

import static org.junit.jupiter.api.Assertions.*;

import exception.InvalidReferenceException;
import exception.UnvalidPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


class SearchEngineTest {

	private StoreManager storeManager;

	@BeforeEach
	void setUpStage1() {
		storeManager = new StoreManager();
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
			assertEquals("\nThere are no products with this name yet", e.getMessage());
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
			assertEquals("\nThere are no products with this category yet", e.getMessage());
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
			assertEquals("\nThere are no products with this price", e.getMessage());
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
		assertEquals("\nError: All products prices are higher than zero",result,"No se muestra ningun producto");
	}

	@Test
	void searchProductsbyPurchasedAmount() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Res","Carne Procesada",12.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",12.99,9,"Food");
		ArrayList<String> products = new ArrayList<>();
		products.add("Pollo");
		products.add("Salchicha de Res");
		products.add("Pollo");
		products.add("Res");
		products.add("Res");

		storeManager.addOrder("Rodrigo",products);
		// Act
		String result = storeManager.searchProductsbyPurchasedAmount(2);

		// Assert
		assertEquals("[Product{name='Res', description='Carne Procesada', price=12.99, quantity=12, category='Basics', timesPurchased=2}, Product{name='Pollo', description='Pollo Fresco', price=12.99, quantity=7, category='Food', timesPurchased=2}]",result,"Se muestran todos los productos menos Salchicha");
	}

	@Test
	void searchProductsbyPurchasedAmountProduct2() {
		// Arrange
		setUpStage3();

		// Act
		String result = storeManager.searchProductsbyPurchasedAmount(4);

		// Assert
		assertEquals("\nError: There are no products with this purchased Amount",result,"No se ha agregado ningun producto a una orden");
	}

	@Test
	void searchProductsbyAmount() {
		// Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,9,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,10,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,10,"Food");

		// Act
		String result = storeManager.searchProductsbyAmount(10);

		// Assert
		assertEquals("[Product{name='Salchicha de Res', description='Carne Procesada', price=13.99, quantity=10, category='Basics', timesPurchased=0}, Product{name='Pollo', description='Pollo Fresco', price=14.99, quantity=10, category='Food', timesPurchased=0}]",result,"No hay ningun producto con esa cantidad");
	}

	@Test
	void searchProductsbyAmount2() {
		// Arrange
		setUpStage3();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyAmount(12);

		// Assert
		assertEquals("\nError: There are no products with this quantity",result,"No hay ningun producto con esa cantidad");
	}

	@Test
	void searchProductsbyRangesetup1() {
		/*/ Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyRange(12,50);
		System.out.println(result);

		// Assert
		assertEquals("[Product{name='Salchicha', description='Carne Embutida', price=10.99, quantity=10, category='Food', timesPurchased=0}, Product{name='Salchicha de Res', description='Carne Procesada', price=13.99, quantity=14, category='Basics', timesPurchased=0}]",result,"No hay ningun producto con esa cantidad");
		*/
	}

	@Test
	void searchProductsbyRangesetup2() {
		/*/ Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyRange(10,20);
		System.out.println(result);

		// Assert
		assertEquals("[Product{name='Salchicha', description='Carne Embutida', price=10.99, quantity=10, category='Food', timesPurchased=0}, Product{name='Salchicha de Res', description='Carne Procesada', price=13.99, quantity=14, category='Basics', timesPurchased=0}]",result,"No hay ningun producto con esa cantidad");
		*/
	}

	@Test
	void searchProductbyNamesetup1() {
		/*/ Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyAmount(12);
		System.out.println(result);

		// Assert
		assertEquals("",result,"No hay ningun producto con esa cantidad");*/
	}

	@Test
	void searchOrderbyDatesetup1() {
		/*/ Arrange
		setUpStage1();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyAmount(12);
		System.out.println(result);

		// Assert
		assertNotEquals("",result,"No hay ningun producto con esa cantidad");*/
	}

	@Test
	void searchOrderbyDatesetup2() {
		/*/ Arrange
		setUpStage3();
		storeManager.addProduct("Salchicha","Carne Embutida",10.99,10,"Food");
		storeManager.addProduct("Salchicha de Res","Carne Procesada",13.99,14,"Basics");
		storeManager.addProduct("Pollo","Pollo Fresco",14.99,9,"Food");

		// Act
		String result = storeManager.searchProductsbyRange(1.99,12.00);
		System.out.println(result);

		// Assert
		assertNotEquals("",result,"No hay ningun producto con esa cantidad");*/
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
			result = storeManager.searchOrderbyName("John Peshard");
			fail("Expected InvalidReferenceException to be thrown");
		} catch (InvalidReferenceException ex) {
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
		storeManager.addOrder("John Shepard", products);

		//act
		String result = null;
		try {
			result = storeManager.searchOrderbyName("John Shepard");

		} catch (InvalidReferenceException ex) {
			ex.getMessage();
		}

		//assert
		assertNotEquals("",result);
	}
}
