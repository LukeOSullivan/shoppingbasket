package io.shoppingBasket;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

public class ShoppingBasket {
	
	private static final String SAVE_HEADING_DATE = "Date/Time: ";
	private static final String SAVE_DATE_FORMAT = "dd/MM/YYYY HH:mm:ss";
		
	int numProducts;
	float basketTotal;
	int numItems;
	private DefaultListModel<OrderItem> model;
	
	/**
	 * ShoppingBasket Constructor
	 */
	public ShoppingBasket(){
		
		model = new DefaultListModel<OrderItem>();
		numProducts = 0;
		basketTotal = 0;
		numItems = 0;
	}
	/**
	 * Adding a new product to basket with a name, value, and quantity
	 * Update totals
	 * 
	 * @param name product name
	 * @param value product price
	 * @param quantity number of items required
	 */
	public void AddProduct(String name, float value, int quantity){ 
		if(IsProductInBasket(name)){
			for(int i = 0; i < model.size();i++){
				if(model.getElementAt(i).ProductName.equals(name)){
					model.getElementAt(i).AddItems(value, quantity);
					break;
				}
			}
		}
		else{
			model.addElement(new OrderItem(name, value, quantity));
		}
		numItems = 0;
		basketTotal = 0;
		for(int i = 0; i < model.size();i++){
			basketTotal += model.getElementAt(i).TotalOrder;
			numItems += model.getElementAt(i).Quantity;
		}
		numProducts = model.size();

	}
	
	/**
	 * Pass to AddProduct with a name and value
	 * @param name product name
	 * @param value product price
	 */
	public void AddProduct(String name, float value){
		AddProduct(name, value, 1);
	}
	
	/**
	 * Remove a specified number of products by name and quantity
	 * Update totals
	 * 
	 * @param name product name
	 * @param quantity number of items
	 * @throws IllegalArgumentException
	 */
	public void RemoveProduct(String name, int quantity) throws IllegalArgumentException{
		if(IsProductInBasket(name)){
			for(int i = 0; i < model.size();i++){
				if(model.getElementAt(i).ProductName.equals(name)){
					model.getElementAt(i).RemoveItems(quantity);
					if(model.getElementAt(i).Quantity <= 0){
						model.removeElementAt(i);
					}
				}
				
			}
			numItems = 0;
			basketTotal = 0;
			for(int i = 0; i < model.size();i++){
				basketTotal += model.getElementAt(i).TotalOrder;
				numItems += model.getElementAt(i).Quantity;
			}
			numProducts = model.size();
		}
		else{
			throw new IllegalArgumentException("Product " + name + " not in basket");
		}
	}
	
	/**
	 * Pass to RemoveProduct with a name and its current quantity
	 * 
	 * @param name product name
	 */
	public void RemoveProduct(String name){
		for(int i = 0; i < model.size();i++){
			if(name.startsWith(model.getElementAt(i).ProductName)){
				RemoveProduct(name, model.getElementAt(i).Quantity);
			}
		}
	}
	
	/**
	 * Remove all products from basket and reset totals
	 */
	public void ClearBasket(){
		model.clear();
		numItems = 0;
		basketTotal = 0;
		numProducts = 0;
	}
	
	/**
	 * Calculate the current price of a product
	 * 
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 */
	public float CurrentPrice(String name) throws IllegalArgumentException{
		float currentPrice = 0;
		if(IsProductInBasket(name)){
			for(int i = 0; i < model.size(); i++){
				if(model.getElementAt(i).ProductName.equals(name)){
					currentPrice = model.getElementAt(i).LatestPrice;
					break;
				}
			}
		}
		else{
			throw new IllegalArgumentException("Product " + name + " not in basket");
		}
		return currentPrice;
		
	}
	
	/**
	 * Check specified product exists in the basket
	 * 
	 * @param name product name
	 * @return true if existing
	 */
	public boolean IsProductInBasket(String name){
		for(int i = 0; i < model.size(); i++){
			if(model.getElementAt(i).ProductName.equals(name)){
				return true;
			}
		}	
		return false;
	}
	
	/**
	 * Save basket to user nominated file in set format
	 * 
	 * @param fileName file specified by user
	 * @return true if save successful
	 */
	public boolean SaveBasket(String fileName){
		PrintWriter pw;
		try {
			pw = new PrintWriter(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		DateFormat dateFormat = new SimpleDateFormat(SAVE_DATE_FORMAT);
		Date date = new Date();
		pw.println("Basket Receipt");
		pw.println(SAVE_HEADING_DATE + dateFormat.format(date) + "\n");
		pw.println(String.format("%s%s%s%s", 
				String.format("%1$-32s", "Description"),
				String.format("%1$-10s", "Qty"),
				String.format("%1$-11s", "Price"),
				String.format("%s", "Total Price")));
		for(int i = 0; i < model.size();i++){
			pw.println("- " + model.getElementAt(i));
		}
		pw.println(String.format("\nTotal Items: %d\nOrder Total: £%.2f", numItems, basketTotal));

		pw.close();
		return true;
	}
	
	/**
	 * create abstract list model to display
	 * @return default list model
	 */
	public AbstractListModel<OrderItem> getList() {
		return model;
	}
	
	/**
	 * Get total number of items in basket
	 * @return number of items as String
	 */
	public String getNumItems(){
		return String.format("%d", numItems);
	}

	/**
	 * Get total price of items in basket
	 * @return total price as String
	 */
	public String getBasketTotal(){
		return String.format("£%.2f", basketTotal);
	}

}
