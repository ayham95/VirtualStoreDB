package sample;

/**
 * Created by ayham on 5/29/16.
 */
public class Product {

    private int productID;
    private String category;
    private String model;
    private String color;
    private int price;
    private int quantity;

    public Product(int productID, String category, String model, String color, int price, int quantity) {
        this.productID = productID;
        this.category = category;
        this.model = model;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
