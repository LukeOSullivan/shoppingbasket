package io.shoppingBasket;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.SwingConstants;

/**
 * Shopping basket GUI.
 * Can add, remove, and edit products, calculate totals, and save receipt as a file.
 * 
 * @author Luke O'Sullivan
 */

public class ShoppingBasketGUI {

	private JFrame frame;
	private JTextField name;
	private JSpinner quantitySpinner;
	private JTextField price;
	private JButton addButton;
	private JButton removeButton;
	private JButton editButton;
	private JButton clearBasketButton;
	private JButton saveButton;
	private JButton helpButton;
	private JButton exitButton;
	private JList<OrderItem> basket;
	private ShoppingBasket shoppingBasket;
	private JTextField totalPrice;
	private JTextField totalItems;	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShoppingBasketGUI window = new ShoppingBasketGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ShoppingBasketGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 589, 470);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/**
		 * Create new shopping basket object
		 */
		shoppingBasket = new ShoppingBasket();
		basket = new JList<OrderItem>(shoppingBasket.getList());
		basket.setFont(new Font("Monospaced", Font.PLAIN, 12));

		/**
		 *  Product Name input
		 */
		name = new JTextField();
		name.setBounds(16, 35, 183, 26);
		frame.getContentPane().add(name);
		name.setColumns(10);

		/**
		 *  Latest Price input
		 */
		price = new JTextField();
		price.setHorizontalAlignment(SwingConstants.TRAILING);
		price.setBounds(329, 35, 117, 26);
		frame.getContentPane().add(price);
		price.setColumns(10);

		/**
		 *  Quantity spinner
		 */
		quantitySpinner = new JSpinner();
		quantitySpinner.setBounds(211, 35, 106, 26);
		frame.getContentPane().add(quantitySpinner);

		/**
		 *  Addition of item
		 */
		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float latestPrice = 0;
				try {
					String priceAsString = price.getText();
					latestPrice = Float.parseFloat(priceAsString);
					shoppingBasket.AddProduct(name.getText(), latestPrice,
							(Integer)quantitySpinner.getValue());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
				totalPrice.setText(shoppingBasket.getBasketTotal());
				totalItems.setText(shoppingBasket.getNumItems());
				frame.repaint();
			}
		});
		addButton.setBounds(458, 35, 117, 29);
		frame.getContentPane().add(addButton);

		/**
		 *  Removal of items
		 */
		removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * Remove item by specified quantity
				 */
				if(basket.isSelectionEmpty()){
					shoppingBasket.RemoveProduct(name.getText(), (Integer)quantitySpinner.getValue());
				}
				/**
				 * Remove item by selection
				 */
				else{
					while(!basket.isSelectionEmpty()){
					shoppingBasket.RemoveProduct(basket.getSelectedValue().ProductName);
					}
				}
				totalPrice.setText(shoppingBasket.getBasketTotal());
				totalItems.setText(shoppingBasket.getNumItems());
				frame.repaint();
			}
		});
		removeButton.setBounds(458, 93, 117, 29);
		frame.getContentPane().add(removeButton);

		/**
		 *  Editing of item
		 */
		editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!basket.isSelectionEmpty()){
					OrderItem orderItem = basket.getSelectedValue();
					
					JTextField nameField = new JTextField(orderItem.ProductName);
					JTextField quantityField = new JTextField(String.format("%d", orderItem.Quantity));
					JTextField priceField = new JTextField(String.format("%.2f", orderItem.LatestPrice));
					JPanel panel = new JPanel(new GridLayout(0,1));
					
					panel.add(new JLabel("Product Name"));
					panel.add(nameField);
					nameField.setEditable(false);
					panel.add(new JLabel("Quantity"));
					panel.add(quantityField);
					panel.add(new JLabel("Latest Price"));
					panel.add(priceField);
					int result = JOptionPane.showConfirmDialog(null, panel, "Edit", 
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if(result == JOptionPane.OK_OPTION){
						int newQuantity = Integer.parseInt(quantityField.getText());
						float newPrice = Float.parseFloat(priceField.getText());
						orderItem.RemoveItems(orderItem.Quantity);
						shoppingBasket.AddProduct(orderItem.ProductName, newPrice, newQuantity);
					}
				}
				else{
					JOptionPane.showMessageDialog(frame, "No product selected");
				}
				totalPrice.setText(shoppingBasket.getBasketTotal());
				totalItems.setText(shoppingBasket.getNumItems());
				frame.repaint();
				
			}
		});
		editButton.setBounds(458, 134, 117, 29);
		frame.getContentPane().add(editButton);

		/**
		 *  Clear all items
		 */
		clearBasketButton = new JButton("Clear Basket");
		clearBasketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shoppingBasket.ClearBasket();
				totalPrice.setText(shoppingBasket.getBasketTotal());
				totalItems.setText(shoppingBasket.getNumItems());
				frame.repaint();
			}
		});
		clearBasketButton.setBounds(458, 175, 117, 29);
		frame.getContentPane().add(clearBasketButton);

		/**
		 * Save to file
		 */
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog fileSelect = new FileDialog(frame, "Please select a file", FileDialog.SAVE);
				fileSelect.setVisible(true);
				
				String fileName = fileSelect.getFile();
				
				if (shoppingBasket.SaveBasket(fileName)){
					JOptionPane.showMessageDialog(frame, "Save successful");
				}
				else{
					JOptionPane.showMessageDialog(frame, "File not saved", "Invalid filename", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
		saveButton.setBounds(458, 216, 117, 29);
		frame.getContentPane().add(saveButton);

		/**
		 *  Exit program
		 */
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setBounds(466, 413, 117, 29);
		frame.getContentPane().add(exitButton);

		JLabel lblNoItems = new JLabel("No Items");
		lblNoItems.setBounds(16, 418, 61, 16);
		frame.getContentPane().add(lblNoItems);

		JLabel lblTotal = new JLabel("Total");
		lblTotal.setBounds(257, 419, 47, 16);
		frame.getContentPane().add(lblTotal);

		/**
		 *  Total price of items in basket
		 */
		totalPrice = new JTextField();
		totalPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		totalPrice.setText(shoppingBasket.getBasketTotal());
		totalPrice.setBounds(300, 413, 130, 26);
		frame.getContentPane().add(totalPrice);
		totalPrice.setColumns(10);

		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setBounds(16, 18, 97, 16);
		frame.getContentPane().add(lblProductName);

		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(211, 18, 61, 16);
		frame.getContentPane().add(lblQuantity);

		JLabel lblLatestPrice = new JLabel("Latest Price");
		lblLatestPrice.setBounds(329, 18, 101, 16);
		frame.getContentPane().add(lblLatestPrice);

		JLabel lblBasket = new JLabel("Basket");
		lblBasket.setBounds(16, 73, 61, 16);
		frame.getContentPane().add(lblBasket);

		/**
		 *  Total number of items in basket
		 */
		totalItems = new JTextField();
		totalItems.setHorizontalAlignment(SwingConstants.TRAILING);
		totalItems.setText(shoppingBasket.getNumItems());
		totalItems.setBounds(85, 413, 130, 26);
		frame.getContentPane().add(totalItems);
		totalItems.setColumns(10);
		
		basket.setBounds(16, 98, 430, 292);
		frame.getContentPane().add(basket);
		
		/**
		 * Displays window with help text
		 */
		helpButton = new JButton("Help");
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new GridLayout(0,1));
				JOptionPane.showMessageDialog(panel, "Adding Items:\nSingle Item - Enter a name and a value, then click 'Add'.\n"
						+ "Multiple Items - Enter a name, value, and quantity, then click 'Add'.\n\n"
						+ "Removing Items:\nBy Name - Enter a name and quantity to be removed, then click 'Remove'.\n"
						+ "By Selection - Click one or more products, then click 'Remove' to remove all selected from basket.\n\n"
						+ "Editing Items:\nSelect an item to edit, then click 'Edit'. Change the values of Quantity and/or Price, then click 'OK' to confirm.\n\n"
						+ "Saving Receipts:\nClick 'Save', then select a new or existing filename to save to.\n\n"
						+ "Remove All Items: Click 'Clear Basket'.\n\n"
						+ "Click 'Exit' to close the program. The current basket will be discarded upon close.", "Help", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpButton.setBounds(466, 372, 117, 29);
		frame.getContentPane().add(helpButton);

	}
}
