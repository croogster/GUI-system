import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Product> products;

    public Inventory() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void deleteProduct(Product product) {
        products.remove(product);
    }

    public void updateProduct(Product product, int quantity) {
        product.setQuantity(quantity);
    }

    public List<Product> getProducts() {
        return products;
    }

     public void saveToFile(String Book1) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(Book1))) {
            for (Product product : products) {
                writer.println(product.getName() + "," + product.getQuantity() + "," + product.getPrice());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String Book1) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Book1))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    double price = Double.parseDouble(parts[2]);
                    Product product = new Product(name, quantity, price);
                    products.add(product);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
