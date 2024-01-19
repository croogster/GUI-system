# Inventory Management System

## Overview
The Inventory Management System is a Java application designed for managing a product inventory. It provides a graphical user interface (GUI) for users to interact with the inventory, including functionalities such as adding, deleting, updating, and clearing products. The system also allows for saving and loading the inventory data to and from a CSV file.

## Components

### 1. InventorySystem Class
   - Manages the GUI and user interactions.
   - Includes components such as text fields, buttons, and a table for displaying the product inventory.
   - Provides functionality for adding, deleting, updating, and clearing products.
   - Calculates and displays the total price of the inventory.

### 2. Inventory Class
   - Manages the product inventory data.
   - Provides methods for adding, deleting, and updating products.
   - Supports saving the inventory to a CSV file and loading the inventory from a CSV file.

### 3. Product Class
   - Represents a product with attributes such as name, quantity, and price.
   - Used by the Inventory class to store product information.

## Usage

1. **Adding a Product**
   - Enter the product name, quantity, and price in the respective text fields.
   - Click the "Add" button to add the product to the inventory.

2. **Deleting a Product**
   - Select a row in the table representing the product to be deleted.
   - Click the "Delete" button to remove the selected product from the inventory.

3. **Updating a Product**
   - Select a row in the table representing the product to be updated.
   - Modify the product information in the text fields.
   - Click the "Update" button to apply the changes to the selected product.

4. **Clearing All Products**
   - Click the "Clear All" button to remove all products from the inventory.

5. **Saving and Loading**
   - The system automatically saves the inventory to a file ("database.csv") after each modification.
   - Click the "Load" button to load the inventory from the saved file.

## Real-World Applications
The Inventory Management System can be used in real-world scenarios such as:
- Small businesses managing their product inventory.
- Retail stores keeping track of stock and pricing.
- Educational purposes for learning GUI programming in Java.
- Modification and extension for specific business needs.

## Dependencies
- Java Development Kit (JDK) 8 or higher.

## How to Run
1. Compile the Java files using a Java compiler.
2. Run the compiled InventorySystem class file.
3. The GUI application will open, and you can start interacting with the inventory.

## Author
[Your Name]

## License
This project is licensed under the [License Name] License - see the LICENSE.md file for details.
