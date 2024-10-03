/*
 * Manas Shringare 
 * CSE 385 Final
 * Inventory Management System
 * December 2023
 */

import javax.swing.*;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryManagement extends JFrame {
	
	// Holds Product Information
    private List<Product> inventory;
    
    // Text fields for user input
    private JTextField nameField;
    private JTextField quantityField;
    private JTextField reorderThresholdField;
    private JTextField categoryField;
    private JTextField priceField;
    private JTextField manufacturerField;
    private JTextField dateAddedField;
    
    // Text area to display inventory information
    private JTextArea inventoryTextArea;
    
    // Label to display status or message
    private JLabel statusLabel;
    
    // Connection for MySQL database
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/inventory?user=root&password=1234";
    private Connection connection;
    
    // Constructor
    public InventoryManagement() {
        // Set up the main frame
        setTitle("Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1000);
       

        // Initialize the inventory list
        inventory = new ArrayList<>();

        // Create components
        JLabel nameLabel = new JLabel("Product Name:");
        JLabel quantityLabel = new JLabel("Quantity:");
        JLabel reorderThresholdLabel = new JLabel("Reorder Threshold:");
        JLabel categoryLabel = new JLabel("Category:");
        JLabel priceLabel = new JLabel("Price:");
        JLabel manufacturerLabel = new JLabel("Manufacturer:");
        JLabel dateAddedLabel = new JLabel("Date Added:");

        // Text fields for user input
        nameField = new JTextField(45);
        quantityField = new JTextField(45);
        reorderThresholdField = new JTextField(45);
        categoryField = new JTextField(45);
        priceField = new JTextField(45);
        manufacturerField = new JTextField(45);
        dateAddedField = new JTextField(45);
        statusLabel = new JLabel();
        
        // Buttons for various actions
        JButton addButton = new JButton("Add Product");
        JButton removeButton = new JButton("Remove Product");
        JButton updateButton = new JButton("Update Stock");
        JButton findButton = new JButton("Find Product");
        JButton findSupplierButton = new JButton("Find Supplier's Info");
        JButton findCustomerButton = new JButton("Find Customer's Info");
        JButton displayLowStockButton = new JButton("Display Top 5 Low Stock Products");
        JButton clearButton = new JButton("Clear");

        // Text area to display inventory information
        inventoryTextArea = new JTextArea(15, 60); // Set rows and columns

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Input Section
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Product Information"));
   
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        
        inputPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(quantityLabel, gbc);
        gbc.gridx = 1;
        
        inputPanel.add(quantityField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(reorderThresholdLabel, gbc);
        gbc.gridx = 1;
        
        inputPanel.add(reorderThresholdField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(categoryLabel, gbc);
        gbc.gridx = 1;
        
        inputPanel.add(categoryField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(priceLabel, gbc);
        gbc.gridx = 1;
        
        inputPanel.add(priceField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(manufacturerLabel, gbc);
        gbc.gridx = 1;
       
        inputPanel.add(manufacturerField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(dateAddedLabel, gbc);
        gbc.gridx = 1;
        
        inputPanel.add(dateAddedField, gbc);
        

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(displayLowStockButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(findButton);
        buttonPanel.add(findSupplierButton);
        buttonPanel.add(findCustomerButton);

        // Adds the button panel to the main frame
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

    
        // Creates a panel to hold the Find buttons
        JPanel findPanel1 = new JPanel(new GridLayout(1, 3, 5, 5));
        findPanel1.setBorder(BorderFactory.createTitledBorder("Find Information"));
        findPanel1.add(findButton);
        findPanel1.add(findSupplierButton);
        findPanel1.add(findCustomerButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; 
        inputPanel.add(findPanel1, gbc);

        
        // Display Section
        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.setBorder(BorderFactory.createTitledBorder("Inventory"));
        displayPanel.add(new JScrollPane(inventoryTextArea), BorderLayout.CENTER);

        // Adds input and display panels to the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(inputPanel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(displayPanel, gbc);
        
        // Adds Find panel below the inventoryTextArea
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(findPanel1, gbc);
        
     
        // Action listeners for buttons
        // Action listener for the "Add Product" button
        addButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		addProduct();
        	}
        });

        // Action listener for the "Remove Product" button
        removeButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		removeProduct();
        	}
        });

        // Action listener for the "Update Stock" button
        updateButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		updateStock();
        	}
        });
        
        // Action listener for the "Find Product" button
        findButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		findProduct();
        	}
        });
     
        // Action listener for the "Find Supplier" button
        findSupplierButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		findSupplier();
        	}
        });
        
        // Action listener for the "Find Customer" button
        findCustomerButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		findCustomer();
        	}
        });
        
        // Action listener for the "Display Top 5 Low Stock Products" button
        displayLowStockButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		displayTopLowStockProducts();
        	}
        });
        
     // Action listener for the "Clear" button
        clearButton.addActionListener(new ActionListener() {
         	@Override
         	public void actionPerformed(ActionEvent e) {
             	clearInventoryTextArea();
         	}
     	});
        
        try {
        	//String url = "jdbc:mysql://localhost:3306/inventory?user=root&password=Emerald2016@";
        	connection = DriverManager.getConnection(JDBC_URL);
        	statusLabel.setText("Connected to database");
        } catch (SQLException ex) {
        	showError("Failed to connect to the database: " + ex.getMessage());
        }
             
    }
  
    // Adds product information to the database 
    private void addProductToDatabase(Product product) {
        try {
            String query = "INSERT INTO Products (product_name, quantity, reorder_threshold, category, price, manufacturer, date_added) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, product.name);
                preparedStatement.setInt(2, product.quantity);
                preparedStatement.setInt(3, product.reorderThreshold);
                preparedStatement.setString(4, product.category);
                preparedStatement.setDouble(5, product.price);
                preparedStatement.setString(6, product.manufacturer);
                preparedStatement.setDate(7, new java.sql.Date(product.dateAdded.getTime()));

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            showError("Failed to add product to the database: " + ex.getMessage());
        }
    }
    
    // Checks for low stock and generate alerts
    private void checkLowStockAlerts(Product product) {
        if (product.quantity < product.reorderThreshold) {
            // Product is below the low stock threshold, generates alert
            try {
                String alertQuery = "INSERT INTO LowStockAlerts (product_name, current_quantity, reorder_threshold, alert_timestamp) " +
                                    "VALUES (?, ?, ?, ?)";
                try (PreparedStatement alertStatement = connection.prepareStatement(alertQuery)) {
                    alertStatement.setString(1, product.name);
                    alertStatement.setInt(2, product.quantity);
                    alertStatement.setInt(3, product.reorderThreshold);
                    alertStatement.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));

                    alertStatement.executeUpdate();
                }
                
                // Displays alert message
                String alertMessage = "Low stock alert for product: " + product.name +
                        "\nCurrent Quantity: " + product.quantity +
                        "\nReorder Threshold: " + product.reorderThreshold +
                        "\nAlert Timestamp: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
                JOptionPane.showMessageDialog(null, alertMessage, "Low Stock Alert", JOptionPane.WARNING_MESSAGE);

            } catch (SQLException ex) {
                showError("Failed to generate low stock alert: " + ex.getMessage());
            }
        }
    }

    // Displays top 5 low stock products
    private void displayTopLowStockProducts() {
        try {
            String query = "SELECT * FROM LowStockAlerts ORDER BY alert_timestamp DESC LIMIT 5";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                StringBuilder topLowStockProducts = new StringBuilder("Top 5 Low Stock Products:\n\n");
                while (resultSet.next()) {
                    String productName = resultSet.getString("product_name");
                    int currentQuantity = resultSet.getInt("current_quantity");
                    int reorderThreshold = resultSet.getInt("reorder_threshold");
                    java.sql.Timestamp alertTimestamp = resultSet.getTimestamp("alert_timestamp");

                    topLowStockProducts.append("Product Name: ").append(productName).append("\n");
                    topLowStockProducts.append("Current Quantity: ").append(currentQuantity).append("\n");
                    topLowStockProducts.append("Reorder Threshold: ").append(reorderThreshold).append("\n");
                    topLowStockProducts.append("Alert Timestamp: ").append(alertTimestamp).append("\n\n");
                }

                // Displays the top 5 low stock products in the inventoryTextArea
                inventoryTextArea.setText(topLowStockProducts.toString());

            }
        } catch (SQLException ex) {
            showError("Error displaying top low stock products: " + ex.getMessage());
        }
    }
    
    // Clears the inventory display area
    private void clearInventoryTextArea() {
        inventoryTextArea.setText("");
    }
    
    // Adds a new product
    private void addProduct() {
        String name = nameField.getText();
        String category = categoryField.getText();
        String manufacturer = manufacturerField.getText();
        String dateString = dateAddedField.getText();
        int quantity = 0;
        double price = 0;
        int reorderThreshold = 0;

        try {
            quantity = Integer.parseInt(quantityField.getText());
            price = Double.parseDouble(priceField.getText());
            reorderThreshold = Integer.parseInt(reorderThresholdField.getText());
        } catch (NumberFormatException ex) {
            showError("Invalid input. Please enter valid numbers.");
            return;
        }

        Date dateAdded;
        try {
            // Date format is MM/DD/YYYY
            String[] dateParts = dateString.split("/");
            int month = Integer.parseInt(dateParts[0]);
            int day = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);

            dateAdded = new Date(year - 1900, month - 1, day);
        } catch (Exception ex) {
            showError("Invalid date format. Please enter date in MM/DD/YYYY format.");
            return;
        }

        Product product = new Product(name, quantity, reorderThreshold, category, price, manufacturer, dateAdded);
        inventory.add(product);

        // Stores the product in the database
        addProductToDatabase(product);
        checkLowStockAlerts(product);

        updateInventoryDisplay();
        clearFields();
    }
    
    // Finds a product by name
    private void findProduct() {
        String productNameToFind = JOptionPane.showInputDialog("Enter the product name to find:");
        if (productNameToFind != null && !productNameToFind.isEmpty()) {
            try {
                String query = "SELECT * FROM Products WHERE product_name = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, productNameToFind);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        // Product found, retrieve information
                        String name = resultSet.getString("product_name");
                        int quantity = resultSet.getInt("quantity");
                        int reorderThreshold = resultSet.getInt("reorder_threshold");
                        String category = resultSet.getString("category");
                        double price = resultSet.getDouble("price");
                        String manufacturer = resultSet.getString("manufacturer");
                        Date dateAdded = resultSet.getDate("date_added");

                        // Display the information
                        JOptionPane.showMessageDialog(null,
                                "Product Name: " + name +
                                        "\nQuantity: " + quantity +
                                        "\nReorder Threshold: " + reorderThreshold +
                                        "\nCategory: " + category +
                                        "\nPrice: " + price +
                                        "\nManufacturer: " + manufacturer +
                                        "\nDate Added: " + new SimpleDateFormat("MM/dd/yyyy").format(dateAdded),
                                "Product Information", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Product not found.");
                    }
                }
            } catch (SQLException ex) {
                showError("Error finding product: " + ex.getMessage());
            }
        } else {
            showError("Please enter a product name.");
        }
    }

    // Removes a product by name
    private void removeProduct() {
        String productNameToRemove = JOptionPane.showInputDialog("Enter the product name to remove:");
        if (productNameToRemove != null && !productNameToRemove.isEmpty()) {
            try {
                // Check if the product exists in the database
                String checkQuery = "SELECT * FROM Products WHERE product_name = ?";
                try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                    checkStatement.setString(1, productNameToRemove);
                    ResultSet resultSet = checkStatement.executeQuery();

                    if (resultSet.next()) {
                        // Product found, proceed with removal
                        String removeQuery = "DELETE FROM Products WHERE product_name = ?";
                        try (PreparedStatement removeStatement = connection.prepareStatement(removeQuery)) {
                            removeStatement.setString(1, productNameToRemove);
                            removeStatement.executeUpdate();
                        }

                        JOptionPane.showMessageDialog(null, "Product removed successfully.");
                        updateInventoryDisplay();
                    } else {
                        JOptionPane.showMessageDialog(null, "Product not found.");
                    }
                }
            } catch (SQLException ex) {
                showError("Error removing product: " + ex.getMessage());
            }
        } else {
            showError("Please enter a product name.");
        }
    }

    // Finds a supplier by name
    private void findSupplier() {
        String supplierNameToFind = JOptionPane.showInputDialog("Enter the supplier name to find:");
        if (supplierNameToFind != null && !supplierNameToFind.isEmpty()) {
            try {
                String query = "SELECT * FROM Suppliers WHERE supplier_name = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, supplierNameToFind);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        // Supplier found, retrieve information
                        StringBuilder supplierInfo = new StringBuilder();
                        ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            String columnValue = resultSet.getString(i);
                            supplierInfo.append(columnName).append(": ").append(columnValue).append("\n");
                        }

                        // Display the information
                        JOptionPane.showMessageDialog(null, supplierInfo.toString(), "Supplier Information", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Supplier not found.");
                    }
                }
            } catch (SQLException ex) {
                showError("Error finding supplier: " + ex.getMessage());
            }
        } else {
            showError("Please enter a supplier name.");
        }
    }
    
    // Finds a customer by name
    private void findCustomer() {
        String customerNameToFind = JOptionPane.showInputDialog("Enter the customer name to find:");
        if (customerNameToFind != null && !customerNameToFind.isEmpty()) {
            try {
                String query = "SELECT * FROM Customers WHERE customer_name = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, customerNameToFind);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        // Customer found, retrieve information
                        StringBuilder customerInfo = new StringBuilder();
                        ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            String columnValue = resultSet.getString(i);
                            customerInfo.append(columnName).append(": ").append(columnValue).append("\n");
                        }

                        // Displays the information
                        JOptionPane.showMessageDialog(null, customerInfo.toString(), "Customer Information", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Customer not found.");
                    }
                }
            } catch (SQLException ex) {
                showError("Error finding customer: " + ex.getMessage());
            }
        } else {
            showError("Please enter a customer name.");
        }
    }

    // Updates the stock of a product and updates the database
    private void updateStock() {
        String productNameToUpdate = JOptionPane.showInputDialog("Enter the product name to update:");
        if (productNameToUpdate != null && !productNameToUpdate.isEmpty()) {
            try {
                String query = "SELECT * FROM Products WHERE product_name = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, productNameToUpdate);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        // Product found, retrieve current information
                        int currentQuantity = resultSet.getInt("quantity");
                        int currentReorderThreshold = resultSet.getInt("reorder_threshold");
                        double currentPrice = resultSet.getDouble("price");

                        // Prompt user for updated information
                        int newQuantity = Integer.parseInt(JOptionPane.showInputDialog("Enter new quantity:", currentQuantity));
                        int newReorderThreshold = Integer.parseInt(JOptionPane.showInputDialog("Enter new reorder threshold:", currentReorderThreshold));
                        double newPrice = Double.parseDouble(JOptionPane.showInputDialog("Enter new price:", currentPrice));

                        // Update the product in the database
                        String updateQuery = "UPDATE Products SET quantity = ?, reorder_threshold = ?, price = ? WHERE product_name = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, newQuantity);
                            updateStatement.setInt(2, newReorderThreshold);
                            updateStatement.setDouble(3, newPrice);
                            updateStatement.setString(4, productNameToUpdate);

                            updateStatement.executeUpdate();

                            // Check for low stock and generate alert
                            checkLowStockAlerts(new Product(productNameToUpdate, newQuantity, newReorderThreshold, "", newPrice, "", new Date()));
                        }

                        JOptionPane.showMessageDialog(null, "Product updated successfully.");
                        updateInventoryDisplay();
                    } else {
                        JOptionPane.showMessageDialog(null, "Product not found.");
                    }
                }
            } catch (SQLException | NumberFormatException ex) {
                showError("Error updating product: " + ex.getMessage());
            }
        } else {
            showError("Please enter a product name.");
        }
    }

    // Shows the updated product information on the screen
    private void updateInventoryDisplay() {
    	inventoryTextArea.setText(""); // Clears existing text

        for (Product product : inventory) {
            inventoryTextArea.append("Product Name: " + product.name + "\n");
            inventoryTextArea.append("Quantity: " + product.quantity + "\n");
            inventoryTextArea.append("Reorder Threshold: " + product.reorderThreshold + "\n");
            inventoryTextArea.append("Category: " + product.category + "\n");
            inventoryTextArea.append("Price: " + product.price + "\n");
            inventoryTextArea.append("Manufacturer: " + product.manufacturer + "\n");
            inventoryTextArea.append("Date Added: " + new SimpleDateFormat("MM/dd/yyyy").format(product.dateAdded) + "\n");
            inventoryTextArea.append("\n");
        }
    }
    
    // Clears the information from the fields 
    private void clearFields() {
        nameField.setText("");
        quantityField.setText("");
        reorderThresholdField.setText("");
        categoryField.setText("");
        priceField.setText("");
        manufacturerField.setText("");
        dateAddedField.setText("");
    }

    private static class Product {
    	// Product attributes
        String name; // Name of the product
        int quantity; // Current quantity of the product
        int reorderThreshold; // The threshold value to place a new order
        String category; // Category of the product
        double price; // Price of the product
        String manufacturer; // Manufacturer of the product
        Date dateAdded; // Date the product was added to the database

        /**
         * Constructor for creating a new Product object with the specified attributes.
         *
         * @param name            The name of the product.
         * @param quantity        The current quantity of the product in stock.
         * @param reorderThreshold The threshold quantity at which the product should be reordered.
         * @param category        The category to which the product belongs.
         * @param price           The price of the product.
         * @param manufacturer    The manufacturer of the product.
         * @param dateAdded       The date when the product was added to the inventory.
         */
        
        public Product(String name, int quantity, int reorderThreshold, String category, double price, String manufacturer, Date dateAdded) {
        	// Initialize the Product object with the provided attributes
        	this.name = name;
            this.quantity = quantity;
            this.reorderThreshold = reorderThreshold;
            this.category = category;
            this.price = price;
            this.manufacturer = manufacturer;
            this.dateAdded = dateAdded;
        }
    }
    
    // Creates the error message
    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    // The main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InventoryManagement().setVisible(true);
            }
        });
    }
}
