package sample;

/**
 * Created by ayham on 5/30/16.
 */
public class Purchase {
    private String firstName;
    private String lastName;
    private String category;
    private String model;
    private int quantity;
    private int date;


    public Purchase(String firstName, String lastName, String category, String model, int quantity, int date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.category = category;
        this.model = model;
        this.quantity = quantity;
        this.date = date;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
