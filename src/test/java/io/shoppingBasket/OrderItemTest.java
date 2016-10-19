package io.shoppingBasket;

import static org.junit.Assert.*;

import javax.swing.AbstractListModel;

import org.junit.Test;

public class OrderItemTest {

	/*
	 * Add items by price and quantity
	 */
	@Test
	public void testAddItemsFloatInt() {
		OrderItem orderItem = new OrderItem("test1", 1.00F, -1);
		// Test order item exists and contains correct values
		assertEquals("test1", orderItem.ProductName);
		assertEquals(1.00F, orderItem.LatestPrice, 0);

		/*
		 * Check Items with negative or zero quantity not added Check total
		 * order remains zero
		 */
		assertEquals(0, orderItem.Quantity);
		assertEquals(0, orderItem.TotalOrder, 0);

		/*
		 * Check added Items with positive quantity Check calculated total order
		 */
		orderItem.AddItems(2.00F, 10);
		assertEquals(10, orderItem.Quantity);
		assertEquals(20.00F, orderItem.TotalOrder, 0);

		/*
		 * Check added Items with positive quantity Check recalculated total
		 * order
		 */

		orderItem.AddItems(3.00F, 15);
		assertEquals(25, orderItem.Quantity);
		assertEquals(75.00F, orderItem.TotalOrder, 0);
	}

	/*
	 * Add items by quantity using existing price
	 */
	@Test
	public void testAddItemsInt() {
		OrderItem orderItem = new OrderItem("test1", 1.00F, -1);
		assertEquals("test1", orderItem.ProductName);
		assertEquals(1.00F, orderItem.LatestPrice, 0);

		assertEquals(0, orderItem.Quantity);
		assertEquals(0, orderItem.TotalOrder, 0);

		orderItem.AddItems(10);
		assertEquals(10, orderItem.Quantity);
		assertEquals(10.00F, orderItem.TotalOrder, 0);

		orderItem.AddItems(15);
		assertEquals(25, orderItem.Quantity);
		assertEquals(25.00F, orderItem.TotalOrder, 0);
	}

	/*
	 * Add a new item at a quantity of 1
	 */
	@Test
	public void testAddItem() {
		OrderItem orderItem = new OrderItem("test1", 1.00F, -1);
		assertEquals("test1", orderItem.ProductName);
		assertEquals(1.00F, orderItem.LatestPrice, 0);

		assertEquals(0, orderItem.Quantity);
		assertEquals(0, orderItem.TotalOrder, 0);

		orderItem.AddItem();
		assertEquals(1, orderItem.Quantity);
		assertEquals(1.00F, orderItem.TotalOrder, 0);

		orderItem.AddItem();
		assertEquals(2, orderItem.Quantity);
		assertEquals(2.00F, orderItem.TotalOrder, 0);
	}

	/*
	 * Remove specified number of items
	 */
	@Test
	public void testRemoveItems() {
		OrderItem orderItem = new OrderItem("test1", 1.00F, 0);
		orderItem.AddItems(4);
		assertEquals("test1", orderItem.ProductName);
		assertEquals(1.00F, orderItem.LatestPrice, 0);

		assertEquals(4, orderItem.Quantity);
		assertEquals(4.00F, orderItem.TotalOrder, 0);

		orderItem.RemoveItems(2);
		assertEquals(2, orderItem.Quantity);
		assertEquals(2.00F, orderItem.TotalOrder, 0);

		orderItem.RemoveItems(2);
		assertEquals(0, orderItem.Quantity);
		assertEquals(0, orderItem.TotalOrder, 0);

		orderItem.RemoveItems(2);
		assertEquals(0, orderItem.Quantity);
		assertEquals(0, orderItem.TotalOrder, 0);
	}

	/*
	 * Remove single item
	 */
	@Test
	public void testRemoveItem() {
		OrderItem orderItem = new OrderItem("test1", 1.00F, 0);
		orderItem.AddItems(2);
		assertEquals("test1", orderItem.ProductName);
		assertEquals(1.00F, orderItem.LatestPrice, 0);

		assertEquals(2, orderItem.Quantity);
		assertEquals(2.00F, orderItem.TotalOrder, 0);

		orderItem.RemoveItem();
		assertEquals(1, orderItem.Quantity);
		assertEquals(1.00F, orderItem.TotalOrder, 0);

		orderItem.RemoveItem();
		assertEquals(0, orderItem.Quantity);
		assertEquals(0, orderItem.TotalOrder, 0);

		orderItem.RemoveItem();
		assertEquals(0, orderItem.Quantity);
		assertEquals(0, orderItem.TotalOrder, 0);
	}

	/*
	 * Pretty Print list of items
	 */
	@Test
	public void testToString() {
		ShoppingBasket basket = new ShoppingBasket();
		basket.AddProduct("Test", 1.00F);
		AbstractListModel<OrderItem> list = basket.getList();
		assertEquals(1, list.getSize());
		OrderItem item = list.getElementAt(0);
		assertEquals("Test                          1         £1.00      £1.00", item.toString());
	}

	/*
	 * Constructor creates an object with a name and price
	 */
	@Test
	public void testOrderItemStringFloat() {
		OrderItem orderItem = new OrderItem("test1", 1.00F);
		// Check correct values are added, with a quantity of 1
		assertEquals("test1", orderItem.ProductName);
		assertEquals(1.00F, orderItem.LatestPrice, 0);
		assertEquals(1, orderItem.Quantity);
	}

	/*
	 * Constructor creates an object with a name, price and quantity
	 */
	@Test
	public void testOrderItemStringFloatInt() {
		OrderItem orderItem = new OrderItem("test1", 1.00F, -1);
		// Check correct values are added, with a quantity of 0
		assertEquals("test1", orderItem.ProductName);
		assertEquals(1.00F, orderItem.LatestPrice, 0);
		assertEquals(0, orderItem.Quantity);

		orderItem = new OrderItem("test2", 1.00F, 0);
		// Check correct values are added, with a quantity of 0
		assertEquals("test2", orderItem.ProductName);
		assertEquals(1.00F, orderItem.LatestPrice, 0);
		assertEquals(0, orderItem.Quantity);

		orderItem = new OrderItem("test3", 1.00F, 5);
		// Check correct values are added, with a quantity of 5
		assertEquals("test3", orderItem.ProductName);
		assertEquals(1.00F, orderItem.LatestPrice, 0);
		assertEquals(5, orderItem.Quantity);

	}
}
