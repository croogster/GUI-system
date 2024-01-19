import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class InventorySystem extends JFrame {
    private JTextField nameField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField totalField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton clearAllButton;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private Inventory inventory;

    public InventorySystem() {
        super("Inventory Management System");

        // components
        nameField = new JTextField(20);
        quantityField = new JTextField(20);
        priceField = new JTextField(20);
        totalField = new JTextField(10);
        totalField.setEditable(false);
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");
        clearAllButton = new JButton("Clear All");

        nameField.setMaximumSize(nameField.getPreferredSize());
        quantityField.setMaximumSize(quantityField.getPreferredSize());
        priceField.setMaximumSize(priceField.getPreferredSize());
        totalField.setMaximumSize(totalField.getPreferredSize());

        // table
        tableModel = new DefaultTableModel(new Object[]{"Name", "Quantity", "Price", "Total"}, 0);
        inventoryTable = new JTable(tableModel);
        // inventory
        inventory = new Inventory();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        inputPanel.add(updateButton);
        inputPanel.add(clearAllButton);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(new JLabel("Total Price"));
        inputPanel.add(totalField);

        // Create a main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Add the input panel to the left side
        mainPanel.add(inputPanel, BorderLayout.WEST);

        // Create a scroll pane for the table and add it to the right side
        mainPanel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);

        add(mainPanel);

        // Action listeners to the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());
                double total = quantity * price; // Calculate the total for the product
                Product product = new Product(name, quantity, price);
                inventory.addProduct(product);
                tableModel.addRow(new Object[]{name, quantity, price, total});

                // Calculate/update total price 
                calculateTotal();

                // Save inventory to file after adding a new product
                saveInventoryToFile("database.csv");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = inventoryTable.getSelectedRow();
                if (row != -1) {
                    inventory.deleteProduct(inventory.getProducts().get(row));
                    tableModel.removeRow(row);

                    // Calculate/update the total price
                    calculateTotal();

                    // Save inventory to file after deleting a product
                    saveInventoryToFile("database.csv");
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = inventoryTable.getSelectedRow();
                if (row != -1) {
                    String name = nameField.getText();
                    int quantity = Integer.parseInt(quantityField.getText());
                    double price = Double.parseDouble(priceField.getText());
                    double total = quantity * price; // Calculate the total for the product
        
                    // Update the product in the inventory
                    Product product = inventory.getProducts().get(row);
                    product.setName(name);
                    product.setQuantity(quantity);
                    product.setPrice(price);
        
                    // Update the table
                    tableModel.setValueAt(name, row, 0); // Update the name in the table
                    tableModel.setValueAt(quantity, row, 1);
                    tableModel.setValueAt(price, row, 2);
                    tableModel.setValueAt(total, row, 3);
        
                    // Calculate/update the total price
                    calculateTotal();
        
                    // Save inventory to file after updating a product
                    saveInventoryToFile("database.csv");
                }
            }
        });

        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventory.getProducts().clear();
                tableModel.setRowCount(0);
                clearFormFields();

                // Calculate/update the total price
                calculateTotal();

                // Save inventory to file after clearing all products
                saveInventoryToFile("database.csv");
            }
        });

        // Frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void addProduct() {
        if (validateInput()) {
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());

            Product product = new Product(name, quantity, price);
            inventory.addProduct(product);
            tableModel.addRow(new Object[]{name, quantity, price, quantity * price});

            // Calculate/update total price
            calculateTotal();

            // Save inventory to file after adding a new product
            saveInventoryToFile("database.csv");

            // Clear input fields
            clearFormFields();
        }
    }

    // Method to update a product in the inventory and update the GUI table
    private void updateProduct() {
        int row = inventoryTable.getSelectedRow();
        if (row != -1 && validateInput()) {
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());

            Product product = inventory.getProducts().get(row);
            product.setName(name);
            product.setQuantity(quantity);
            product.setPrice(price);

            // Update the table
            tableModel.setValueAt(name, row, 0);
            tableModel.setValueAt(quantity, row, 1);
            tableModel.setValueAt(price, row, 2);
            tableModel.setValueAt(quantity * price, row, 3);

            // Calculate/update total price
            calculateTotal();

            // Save inventory to file after updating a product
            saveInventoryToFile("database.csv");

            // Clear input fields
            clearFormFields();
        }
    }
    private boolean validateInput() {
        return validateTextField(nameField, "Product Name") &&
               validateNumberField(quantityField, "Quantity") &&
               validateNumberField(priceField, "Price");
    }

    // Helper method to validate text fields
    private boolean validateTextField(JTextField textField, String fieldName) {
        String input = textField.getText().trim();
        if (input.isEmpty()) {
            showError(fieldName + " cannot be empty");
            return false;
        }
        return true;
    }

    // Helper method to validate number fields
    private boolean validateNumberField(JTextField textField, String fieldName) {
        try {
            double value = Double.parseDouble(textField.getText());
            if (value < 0) {
                showError(fieldName + " must be a positive number");
                return false;
            }
        } catch (NumberFormatException e) {
            showError(fieldName + " must be a valid number");
            return false;
        }
        return true;
    }

    // Helper method to show error messages
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    // calculate/update the total price
    private void calculateTotal() {
        double overallTotal = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            overallTotal += (double) tableModel.getValueAt(i, 3);
        }
        totalField.setText(String.valueOf(overallTotal));
    }

    // clear fields
    private void clearFormFields() {
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    private void saveInventoryToFile(String fileName) {
        inventory.saveToFile(fileName);
        JOptionPane.showMessageDialog(this, "Inventory saved to file: " + fileName);
    }

    private void loadInventoryFromFile(String fileName) {
        inventory.loadFromFile(fileName);
        refreshTable();
        JOptionPane.showMessageDialog(this, "Inventory loaded from file: " + fileName);
    }

    private void refreshTable() {
        // Clear existing rows from the table
        tableModel.setRowCount(0);

        // Populate the table with data from the inventory
        for (Product product : inventory.getProducts()) {
            tableModel.addRow(new Object[]{product.getName(), product.getQuantity(), product.getPrice(),
                    product.getQuantity() * product.getPrice()});
        }

        // Calculate/update the total price
        calculateTotal();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InventorySystem inventorySystem = new InventorySystem();
            String fileName = "database.csv";
    
            // Load data from the CSV file and display it in the table
            inventorySystem.loadInventoryFromFile(fileName);
    
            // Additional actions on the inventorySystem instance
            inventorySystem.setTitle("Inventory Management System");
    
            // Example: Display a message dialog
            JOptionPane.showMessageDialog(inventorySystem, "Inventory System Initialized!");
    
            // Add validation for the Add button
            inventorySystem.addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (validateInput(inventorySystem.nameField, "Product Name") &&
                        validateInput(inventorySystem.quantityField, "Quantity") &&
                        validateInput(inventorySystem.priceField, "Price")) {
                        // Input is valid, proceed with adding the product
                        inventorySystem.addProduct();
                    }
                }
            });
    
            // Add validation for the Update button
            inventorySystem.updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (validateInput(inventorySystem.nameField, "Product Name") &&
                        validateInput(inventorySystem.quantityField, "Quantity") &&
                        validateInput(inventorySystem.priceField, "Price")) {
                        // Input is valid, proceed with updating the product
                        inventorySystem.updateProduct();
                    }
                }
            });
        });
    }
    
    // Helper method to validate input in text fields
    private static boolean validateInput(JTextField textField, String fieldName) {
        String input = textField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(null, fieldName + " cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}    