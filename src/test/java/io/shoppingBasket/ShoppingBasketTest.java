package io.shoppingBasket;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import org.junit.Test;

public class ShoppingBasketTest {

	/*
	 * Constructor creates an object
	 */
	@Test
	public void testShoppingBasket() {
		ShoppingBasket basket = new ShoppingBasket();
		
		assertEquals(0, basket.numProducts);
		assertEquals(0, basket.basketTotal, 0);
		assertEquals(0, basket.numItems);
	}

	/*
	 * Add product by name, price, and quantity
	 */
	@Test
	public void testAddProductStringFloatInt() {
		String[] name = { "Test1", "Test2" };
		float[] value = { 1.00F, 2.00F };
		int[] quantity = { 1, 2 };
		ShoppingBasket basket = new ShoppingBasket();
		/*
		 * add one product with a name, price, and quantity. calculate total
		 * price, number of items, number of products, and basket price.
		 */
		basket.AddProduct(name[0], value[0], quantity[0]);

		AbstractListModel<OrderItem> list = basket.getList();
		assertEquals(1, list.getSize());
		OrderItem item = list.getElementAt(0);

		assertEquals(name[0], item.ProductName);
		assertEquals(value[0], item.LatestPrice, 0);
		assertEquals(quantity[0], item.Quantity);
		assertEquals(1.00F, item.TotalOrder, 0);
		assertEquals(1, basket.numItems);
		assertEquals(1, basket.numProducts);
		assertEquals(1.00F, basket.basketTotal, 0);

		/*
		 * add product. check product contains correct values
		 */
		basket.AddProduct(name[1], value[1], quantity[1]);

		assertEquals(2, list.getSize());

		for (int i = 0; i < list.getSize(); i++) {

			item = list.getElementAt(i);

			assertEquals(name[i], item.ProductName);
			assertEquals(value[i], item.LatestPrice, 0);
			assertEquals(quantity[i], item.Quantity);
		}

		/*
		 * add first product again with different quantity to its original
		 */
		basket.AddProduct(name[0], value[0], quantity[1]);
		item = list.getElementAt(0);

		assertEquals(name[0], item.ProductName);
		assertEquals(value[0], item.LatestPrice, 0);
		assertEquals(3, item.Quantity);
		assertEquals(3.00F, item.TotalOrder, 0);
		assertEquals(5, basket.numItems);
		assertEquals(2, basket.numProducts);
		assertEquals(7.00F, basket.basketTotal, 0);
	}

	/*
	 * Add product by name and price
	 */
	@Test
	public void testAddProductStringFloat() {
		String[] name = { "Test1", "Test2" };
		float[] value = { 1.00F, 2.00F };
		;
		ShoppingBasket basket = new ShoppingBasket();
		/*
		 * one product is added with a name, price. calculate quantity, total
		 * price, number of items, number of products, and basket price.
		 */
		basket.AddProduct(name[0], value[0]);

		AbstractListModel<OrderItem> list = basket.getList();
		assertEquals(1, list.getSize());
		OrderItem item = list.getElementAt(0);

		assertEquals(name[0], item.ProductName);
		assertEquals(value[0], item.LatestPrice, 0);
		assertEquals(1, item.Quantity);
		assertEquals(1.00F, item.TotalOrder, 0);
		assertEquals(1, basket.numItems);
		assertEquals(1, basket.numProducts);
		assertEquals(1.00F, basket.basketTotal, 0);

		/*
		 * one product is added to existing list
		 */
		basket.AddProduct(name[1], value[1]);

		assertEquals(2, list.getSize());

		for (int i = 0; i < list.getSize(); i++) {

			item = list.getElementAt(i);

			assertEquals(name[i], item.ProductName);
			assertEquals(value[i], item.LatestPrice, 0);
			assertEquals(1, item.Quantity);
		}
		/*
		 * first product is added again, incrementing quantity
		 */
		basket.AddProduct(name[0], value[0]);
		item = list.getElementAt(0);
		assertEquals(name[0], item.ProductName);
		assertEquals(value[0], item.LatestPrice, 0);
		assertEquals(2, item.Quantity);
		assertEquals(2.00F, item.TotalOrder, 0);
		assertEquals(3, basket.numItems);
		assertEquals(2, basket.numProducts);
		assertEquals(4.00F, basket.basketTotal, 0);

		/*
		 * second product is added again, with a different price to its original
		 */
		basket.AddProduct(name[1], value[0]);
		item = list.getElementAt(1);
		assertEquals(name[1], item.ProductName);
		assertEquals(value[0], item.LatestPrice, 0);
		assertEquals(2, item.Quantity);
		assertEquals(2.00F, item.TotalOrder, 0);
		assertEquals(4, basket.numItems);
		assertEquals(2, basket.numProducts);
		assertEquals(4.00F, basket.basketTotal, 0);
	}

	/*
	 * Remove product by name and quantity
	 */
	@Test
	public void testRemoveProductStringInt() {
		String[] name = { "Test1", "Test2" };
		float[] value = { 1.00F, 2.00F };
		int[] quantity = { 1, 3 };
		ShoppingBasket basket = new ShoppingBasket();

		DefaultListModel<OrderItem> list = (DefaultListModel<OrderItem>) basket.getList();
		basket.numItems = 0;
		for (int i = 0; i < name.length; i++) {
			list.addElement(new OrderItem(name[i], value[i], quantity[i]));
			basket.numItems += quantity[i];
		}
		assertEquals(2, list.getSize());

		/*
		 * reduce second product's quantity by 1. check price total, number of
		 * items, number of products, and basket total is also reduced by 1.
		 */
		basket.RemoveProduct(name[1], 1);
		assertEquals(2, list.getElementAt(1).Quantity);
		assertEquals(4.00F, list.getElementAt(1).TotalOrder, 0);
		assertEquals(3, basket.numItems);
		assertEquals(2, basket.numProducts);
		assertEquals(5.00F, basket.basketTotal, 0);

		/*
		 * remove second product's remaining quantity. check product is removed
		 * entirely from list. check number of items, number of products, and
		 * basket total are reduced.
		 */
		basket.RemoveProduct(name[1], 2);
		assertEquals(1, list.size());
		assertEquals(1, basket.numItems);
		assertEquals(1, basket.numProducts);
		assertEquals(1.00F, basket.basketTotal, 0);

		/*
		 * remove more items than first product contains. check quantity is set
		 * to 0 and product is removed from list. check number of items, number
		 * of products, and basket total are set to 0.
		 */
		basket.RemoveProduct(name[0], 2);
		assertEquals(0, list.size());
		assertEquals(0, basket.numItems);
		assertEquals(0, basket.numProducts);
		assertEquals(0, basket.basketTotal, 0);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveProductStringIntWithException() {
		String[] name = { "Test1", "Test2" };
		float[] value = { 1.00F, 2.00F };
		int[] quantity = { 1, 3 };
		ShoppingBasket basket = new ShoppingBasket();

		DefaultListModel<OrderItem> list = (DefaultListModel<OrderItem>) basket.getList();
		basket.numItems = 0;
		for (int i = 0; i < name.length; i++) {
			list.addElement(new OrderItem(name[i], value[i], quantity[i]));
			basket.numItems += quantity[i];
		}

		//Remove by false product name
		basket.RemoveProduct("Unknown", 1);
	}

	/*
	 * Remove all products that match a specified name
	 */
	@Test
	public void testRemoveProductString() {
		String[] name = { "Test1", "Test2" };
		float[] value = { 1.00F, 2.00F };
		int[] quantity = { 1, 2 };
		ShoppingBasket basket = new ShoppingBasket();

		DefaultListModel<OrderItem> list = (DefaultListModel<OrderItem>) basket.getList();
		basket.numItems = 0;
		for (int i = 0; i < name.length; i++) {
			list.addElement(new OrderItem(name[i], value[i], quantity[i]));
			basket.numItems += quantity[i];
		}
		assertEquals(2, list.getSize());
		/*
		 * remove second product. check list size, number of items, number of
		 * products, and basket total are reduced
		 */
		basket.RemoveProduct(name[1]);
		assertEquals(1, list.size());
		assertEquals(1, basket.numItems);
		assertEquals(1, basket.numProducts);
		assertEquals(1.00F, basket.basketTotal, 0);

	}

	/*
	 * Remove all products from basket
	 */
	@Test
	public void testClearBasket() {
		String[] name = { "Test1", "Test2" };
		float[] value = { 1.00F, 2.00F };
		int[] quantity = { 1, 2 };
		ShoppingBasket basket = new ShoppingBasket();

		DefaultListModel<OrderItem> list = (DefaultListModel<OrderItem>) basket.getList();
		basket.numItems = 0;
		for (int i = 0; i < name.length; i++) {
			list.addElement(new OrderItem(name[i], value[i], quantity[i]));
			basket.numItems += quantity[i];
		}
		assertEquals(2, list.getSize());

		basket.ClearBasket();
		assertEquals(0, list.getSize());
		assertEquals(0, list.size());
		assertEquals(0, basket.numItems);
		assertEquals(0, basket.numProducts);
		assertEquals(0, basket.basketTotal, 0);
	}

	/*
	 * Calculate the current price of a product
	 */
	@Test
	public void testCurrentPriceString() {
		String[] name = { "Test1", "Test2" };
		float[] value = { 1.00F, 2.00F };
		int[] quantity = { 1, 3 };
		ShoppingBasket basket = new ShoppingBasket();
		
		DefaultListModel<OrderItem> list = (DefaultListModel<OrderItem>) basket.getList();	
		for (int i = 0; i < name.length; i++) {
			list.addElement(new OrderItem(name[i], value[i], quantity[i]));
		}
		assertEquals(2, list.getSize());
		
		// check for current price by existing product name
		assertEquals(1.00F, basket.CurrentPrice("Test1"), 0);
		assertEquals(2.00F, basket.CurrentPrice("Test2"), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCurrentPriceStringWithException() {
		String[] name = { "Test1", "Test2" };
		float[] value = { 1.00F, 2.00F };
		int[] quantity = { 1, 3 };
		ShoppingBasket basket = new ShoppingBasket();
		
		DefaultListModel<OrderItem> list = (DefaultListModel<OrderItem>) basket.getList();	
		for (int i = 0; i < name.length; i++) {
			list.addElement(new OrderItem(name[i], value[i], quantity[i]));
		}
		//get current price from invalid product
		basket.CurrentPrice("missing");
	}

	/*
	 * Return true if specified product already exists in basket
	 */
	@Test
	public void testIsProductInBasket() {
		String[] name = { "Test1", "Test2" };
		float[] value = { 1.00F, 2.00F };
		int[] quantity = { 1, 3 };
		ShoppingBasket basket = new ShoppingBasket();
		
		DefaultListModel<OrderItem> list = (DefaultListModel<OrderItem>) basket.getList();	
		for (int i = 0; i < name.length; i++) {
			list.addElement(new OrderItem(name[i], value[i], quantity[i]));
		}
		assertEquals(2, list.getSize());
		//Check for existing product
		assertTrue(basket.IsProductInBasket("Test1"));
		//Check for missing product
		assertFalse(basket.IsProductInBasket("Unknown"));
	}

	/*
	 * Write to specified file in correct format
	 */
	@SuppressWarnings("resource")
	@Test
	public void testSaveBasket() throws IOException {
		String filename = "File.txt";
		ShoppingBasket basket = new ShoppingBasket();

		assertTrue(basket.SaveBasket(filename));
		assertFalse(basket.SaveBasket(".."));
		File file = new File(filename);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
		Date date = new Date();
		//check file is created
		assertTrue(file.exists());
		BufferedReader bfr = new BufferedReader(new FileReader(file));
		//check format
		assertEquals("Basket Receipt", bfr.readLine());
		assertEquals("Date/Time: " + dateFormat.format(date), bfr.readLine());
		assertEquals("", bfr.readLine());
		assertEquals("Description                     Qty       Price      Total Price", bfr.readLine());
		assertEquals("", bfr.readLine());

		basket.AddProduct("Test", 1.00F);
		assertTrue(basket.SaveBasket(filename));
		assertTrue(file.exists());
		bfr = new BufferedReader(new FileReader(file));
		assertEquals("Basket Receipt", bfr.readLine());
		assertEquals("Date/Time: " + dateFormat.format(date), bfr.readLine());
		assertEquals("", bfr.readLine());
		assertEquals("Description                     Qty       Price      Total Price", bfr.readLine());
		assertEquals("- Test                          1         £1.00      £1.00", bfr.readLine());
		assertEquals("", bfr.readLine());
		assertEquals("Total Items: 1", bfr.readLine());
		assertEquals("Order Total: £1.00", bfr.readLine());
		bfr.close();
	}
	
	/*
	 * Get the total number of items in basket
	 */
	@Test
	public void testGetNumItems() {
		ShoppingBasket basket = new ShoppingBasket();
		basket.numItems = 10;
		assertEquals("10", basket.getNumItems());
	}
	
	/*
	 * Get the total price of the basket
	 */
	@Test
	public void testGetBasketTotal() {
		ShoppingBasket basket = new ShoppingBasket();
		basket.basketTotal = 10.00F;
		assertEquals("£10.00", basket.getBasketTotal());
	}
	
	/*
	 * get the model list
	 */
	@Test
	public void testGetList(){
		ShoppingBasket basket = new ShoppingBasket();
		//Check for an empty array
		assertEquals("[]", basket.getList().toString());
	}

}
