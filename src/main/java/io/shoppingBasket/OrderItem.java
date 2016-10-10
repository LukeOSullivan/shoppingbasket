package io.shoppingBasket;

public class OrderItem {
	
	//fields
	final String ProductName;
	float LatestPrice = 0.00F;

	int Quantity = 0;
	float TotalOrder = 0.00F;
	
	/**
	 * Passes to constructor with a quantity of 1
	 * @param name product name
	 * @param price product price
	 */
	public OrderItem(String name, float price){
		this(name, price, 1);
	}
	
	/**
	 * Constructor for each item of a product
	 * @param name product name
	 * @param price product price
	 * @param quantity number of items
	 */
	public OrderItem(String name, float price, int quantity){
		ProductName = name;
		LatestPrice = price;
		if(quantity > 0){
			AddItems(quantity);
		}
		else{
			quantity = 0;
		}
	}
	
	/**
	 * Add specific number of items with a specific price
	 * @param latestPrice product price
	 * @param itemsToAdd number of items to be added
	 */
	public void AddItems(float latestPrice, int itemsToAdd){
		this.LatestPrice = latestPrice;
		AddItems(itemsToAdd);
	}

	/**
	 * Add specific number of items with current price
	 * @param itemsToAdd number of items to be added
	 */
	public void AddItems(int itemsToAdd){
		Quantity += itemsToAdd;
		TotalOrder = Quantity * LatestPrice;
	}
	
	/**
	 * Pass to AddItems with a quantity of 1
	 */
	public void AddItem(){
		AddItems(1);	
	}
	
	/**
	 * Remove items by specific quantity
	 * @param itemsToRemove number of items to be removed
	 */
	public void RemoveItems(int itemsToRemove){
		if(itemsToRemove <= Quantity){
			Quantity -= itemsToRemove;
			TotalOrder = Quantity * LatestPrice;
		}
		else{
			Quantity = 0;
			TotalOrder = 0;
		}
	}
	
	/**
	 * Pass to RemoveItems with a quantity of 1
	 */
	public void RemoveItem(){
		RemoveItems(1);
	}
	
	/**
	 * Sets format for the list displayed
	 */
	@Override
	public String toString() {
		return String.format("%s%s%s%s", 
				String.format("%-30s", ProductName),
				String.format("%-10d", Quantity),
				String.format("£%-10.2f", LatestPrice),
				String.format("£%.2f", TotalOrder));
	}
}