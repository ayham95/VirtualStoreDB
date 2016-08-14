package sample;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StringUtils;
import com.sun.deploy.util.SystemUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.LocalGregorianCalendar;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;


/**
 * Created by ayham on 5/26/16.
 */
public class DataBaseConnection {

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private int purchasesCount;
    private boolean purchasedAgain;
    private ObservableList<Product> products;
    private ObservableList<Customer> customers;
    private ObservableList<Category> categories;
    private ObservableList<Purchase> purchases;

    private int count()
    {
        int counter = 1;
        try
        {
            resultSet = statement.executeQuery("select * from purchase");
            while (resultSet.next())
            {
                ++counter;
            }
            return counter;
        }catch (Exception e)
        {
            System.out.println("counter Failed!");
        }
        return counter ;
    }

    public ObservableList<Purchase> getPurchases()
    {
        return this.purchases;
    }

    public ObservableList<Product> getProducts() {
        return this.products;
    }

    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }




    public DataBaseConnection()
    {
        products = FXCollections.observableArrayList();
        refreshProducts(); // to add all the products in the dataBase
        purchasesCount = 7;
        purchasedAgain = false;

        try{
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:8889/DataBase", "root", "W31c0m31");
            System.out.println("Connected!");
            statement = (Statement) connection.createStatement();


        }catch (Exception e)
        {
            System.out.println("Failed");
        }
    }

    public void addCustomer(int id, String firstName, String lastName, int phoneNumber, String gender)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into customer values(?,?,?,?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setInt(4, phoneNumber);
            preparedStatement.setString(5, gender);

            preparedStatement.execute();

            //statement.executeUpdate("INSERT INTO customer VALUES (" + id + firstName + lastName + phoneNumber + gender +")"); DIDN'T WORK! the problem was in the String type...

        }catch (Exception e)
        {
            System.out.println("ADD FAILED!");
        }
    }

    public void deleteCustomer(int id)
    {
        try
        {
            statement.executeUpdate("delete from purchase where CID = " + id);
            statement.executeUpdate("delete from Customer where CID = " + id);
        }catch (Exception e)
        {
            System.out.println("Customer Deletion Failed!");
        }
    }

    public void findProducts(String name)
    {
        try{
            products = FXCollections.observableArrayList();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from product as p inner join Category as c on c.catID = p.category where c.catname = ?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int pID = resultSet.getInt("PID");
                String catID = resultSet.getString("catName");
                String model = resultSet.getString("model");
                String color = resultSet.getString("color");
                int price = resultSet.getInt("price");
                int quantity = resultSet.getInt("quantity");
                System.out.println("********");
                products.add(new Product(pID,catID,model,color,price,quantity));

            }
        }catch (Exception e)
        {
            System.out.println("finding failed!");
        }
    }

    public void addCategory(int catID, String name, String discreption)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into Category values(?,?,?)");
            preparedStatement.setInt(1, catID);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, discreption);
            preparedStatement.execute();
            refreshCategories();
        }catch (Exception e)
        {
            System.out.println("Category adding Failed!");
        }

    }

    public void deleteCategory(int catID)
    {
        try
        {
            statement.executeUpdate("delete from Product where category = " + catID);
            statement.executeUpdate("delete from Category where CatID = " + catID);

        }catch (Exception e)
        {
            System.out.println("Category deletion Failed!");
        }

    }

    public void addProduct(int pID, int catID, String model,String color, int price, int quantity)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into Product values(?,?,?,?,?,?)");
            preparedStatement.setInt(1,pID);
            preparedStatement.setInt(2, catID);
            preparedStatement.setString(3, model);
            preparedStatement.setString(4, color);
            preparedStatement.setInt(5,price);
            preparedStatement.setInt(6,quantity);
            preparedStatement.execute();
            refreshProducts();
        }catch (Exception e)
        {
            System.out.println("Product Adding failed!");
        }
    }

    public void deleteProduct(int id)
    {
        try
        {
            statement.executeUpdate("delete from purchase where PID = " + id);
            statement.executeUpdate("delete from Product where PID = " + id);
        }catch (Exception e)
        {
            System.out.println("deleting Failed!");
        }
    }

    public void refreshProducts()
    {
        try
        {
            products = FXCollections.observableArrayList();

            resultSet = statement.executeQuery("select PID, c.catName, model, color, price, quantity from product as p inner join category as c on c.catid = p.category ");
            while (resultSet.next())
            {
                int pID = resultSet.getInt("PID");
                String catID = resultSet.getString("catName");
                String model = resultSet.getString("model");
                String color = resultSet.getString("color");
                int price = resultSet.getInt("price");
                int quantity = resultSet.getInt("quantity");
                System.out.println(pID+catID);
                products.add(new Product(pID,catID,model,color,price,quantity));

            }
        }catch (Exception e)
        {
            System.out.println("showing Failed!");
        }
    }

    public void refreshCustomers()
    {
        try {
            customers = FXCollections.observableArrayList();
            resultSet = statement.executeQuery("select * from customer");
            while (resultSet.next())
            {
                int cID = resultSet.getInt("CID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                int phoneNumber = resultSet.getInt("PhoneNumber");
                String gender = resultSet.getString("Gender");
                customers.add(new Customer(cID,firstName,lastName,phoneNumber,gender));
            }
        }catch (Exception e)
        {
            System.out.println("customers refresh Failed!");
        }
    }

    public void refreshCategories()
    {
        try
        {
            categories = FXCollections.observableArrayList();
            resultSet = statement.executeQuery("select * from category");
            while (resultSet.next())
            {
                int catID = resultSet.getInt("CatID");
                String name = resultSet.getString("CatName");
                String description = resultSet.getString("Discreption");
                categories.add(new Category(catID, name, description));
            }
        }catch (Exception e)
        {
            System.out.println("categories refresh failed!");
        }
    }


    public void sell(int cID, int pID)
    {
        try
        {

            resultSet = statement.executeQuery("SELECT * FROM purchase");
            while (resultSet.next())
            {
                int customerID = resultSet.getInt("CID");
                int productID = resultSet.getInt("PID");

                if (customerID == cID && productID == pID) {

                    purchasedAgain = true;
                    if (getProductQuantity(pID) > 0) {
                        statement.executeUpdate("UPDATE purchase SET quantity = quantity + 1 where CID = " + cID + " and PID = " + pID);
                        statement.executeUpdate("UPDATE Product SET quantity = quantity - 1 where PID = " + pID );

                    }else{
                        //cant sell!!!!!!
                    }
                    System.out.println("true");
                }
            }
            if (!purchasedAgain) {
                if (getProductQuantity(pID) > 0) {
                    PreparedStatement preparedStatement = connection.prepareStatement("insert into Purchase values(?,?,?,?,?)");
                    preparedStatement.setInt(1, count());
                    preparedStatement.setInt(2, cID);
                    preparedStatement.setInt(3, pID);
                    preparedStatement.setInt(4, 1);
                    preparedStatement.setInt(5, 12);
                    preparedStatement.execute();
                }
                if (getProductQuantity(pID) - 1 > 0)
                statement.executeUpdate("UPDATE Product SET quantity = quantity - 1 where PID = " + pID );
                System.out.println("true");

            }
            purchasedAgain = false;


        }catch (Exception e)
        {
            System.out.printf("sale Failed!  " + pID);

        }
    }

    public void search(String firstName, String lastName)
    {
        Date dates = new Date();
        try {
            String sql = "select  FirstName, LastName, CatName, Model, p.quantity from Purchase as p inner join Customer as c on p.CID = c.CID inner join \n" +
                    "Product as pr on p.PID = pr.PID inner join Category as ca on ca.CatID = pr.Category where firstname = ? and lastName = ?";

            purchases = FXCollections.observableArrayList();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                String first = resultSet.getString("FirstName");
                String last = resultSet.getString("LastName");
                String catName = resultSet.getString("CatName");
                String model = resultSet.getString("Model");
                int quantity = resultSet.getInt("quantity");
                String date = resultSet.getString("PDate");
                purchases.add(new Purchase(first, last, catName, model, quantity, dates.getDate()));
               // System.out.println(first + " " + last + " " + catName + " " + model);

            }
        }catch (Exception e)
        {
            System.out.println("Search Failed!");
        }

    }

    public void findProduct(String categoryName)
    {
        try
        {
            String sql = "select PID, Category, Model, Price, quantity from Product as p inner join Category as c \n" +
                    "on p.category = c.catID where catName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,categoryName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int pid = resultSet.getInt("PID");
                int category = resultSet.getInt("Category");
                String model = resultSet.getString("Model");
                int price = resultSet.getInt("Price");
                int quantity = resultSet.getInt("Quantity");
                System.out.println(pid + " " + category + " " + model + " " + price + " " + quantity);
            }
        }catch (Exception e)
        {
            System.out.println("Finding Failed!!");
        }
    }

    public void sortCustomers()
    {
        try
        {
            String sql = "select DISTINCT c.CID, FirstName, LastName,phoneNumber , Gender from Customer as c inner join Purchase as p \n" +
                    "on c.CID = p.CID order by quantity desc ";
            customers = FXCollections.observableArrayList();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next())
            {
                int id = resultSet.getInt("CID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                int phoneNumber = resultSet.getInt("phoneNumber");
                String gender = resultSet.getString("Gender");
                customers.add(new Customer(id,firstName,lastName,phoneNumber,gender));
            }
        }catch (Exception e)
        {
            System.out.println("Sorting Failed!");
        }
    }

    public void refreshPurchases()
    {

        Date date = new Date();

        try {
            String sql = "select  FirstName, LastName, CatName, Model, p.quantity, PDate from Purchase as p inner join Customer as c on p.CID = c.CID inner join \n" +
                    "Product as pr on p.PID = pr.PID inner join Category as ca on ca.CatID = pr.Category";
            purchases = FXCollections.observableArrayList();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next())
            {
                String first = resultSet.getString("FirstName");
                String last = resultSet.getString("LastName");
                String catName = resultSet.getString("CatName");
                String model = resultSet.getString("Model");
                int quantity = resultSet.getInt("Quantity");
                int date1 = resultSet.getInt("PDate");
                purchases.add(new Purchase(first, last, catName, model, quantity ,(date.getDay() - 2)));

            }
        }catch (Exception e)
        {
            System.out.println("Search Failed!");
        }

    }

    public int getProductQuantity(int pID)
    {
        try{
            resultSet = statement.executeQuery("select * from product");
            while (resultSet.next())
            {
                int id = resultSet.getInt("PID");
                int quantity = resultSet.getInt("quantity");
                if (id == pID)
                {
                    return quantity;
                }
            }
        }catch (Exception e)
        {
            System.out.println("product count failed!");
        }
        return -1;
    }



}
