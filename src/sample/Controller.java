package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

    public TextField product_text_id;
    public TextField product_category_text;
    public TextField product_model_text;
    public TextField product_color_text;
    public TextField product_price_text;
    public TextField product_quantity_text;
    public Button product_add_btn;
    public Button product_delete_btn;
    public Button product_refresh_btn;
    public Button product_sell_btn;
    public Button product_search_btn;
    public Button customer_add_btn;
    public Button customer_delete_btn;
    public Button customer_refresh_btn;
    public Button customer_search_btn;
    public Button customer_sort_btn;
    public Button category_add_btn;
    public Button category_delete_btn;
    public Button category_refresh_btn;
    public TableView<Product> product_table;
    public TableColumn product_id;
    public TableColumn product_category_id;
    public TableColumn product_model;
    public TableColumn product_color;
    public TableColumn product_price;
    public TableColumn product_quantity;
    public TableView<Customer> customers_table;
    public TableColumn customer_id;
    public TableColumn customer_first_name;
    public TableColumn customer_last_name;
    public TableColumn customer_phone_number;
    public TableColumn customer_gender;
    public TableColumn customer_quantity;
    public TableView<Category> category_table;
    public TableColumn category_id;
    public TableColumn category_name;
    public TableColumn category_description;
    public TextField customer_id_text;
    public TextField customer_first_text;
    public TextField customer_last_text;
    public TextField customer_phone_text;
    public TextField customer_gender_text;
    public TextField category_id_text;
    public TextField category_name_text;
    public TextField category_description_text;
    public TextField purchase_first_text;
    public TextField purchase_last_text;
    public Button purchase_search_btn;
    public Button purchase_sort_btn;
    public Button purchase_refresh_btn;
    public TableView<Purchase> purchase_table;
    public TableColumn purchase_first_name;
    public TableColumn purchase_last_name;
    public TableColumn purchase_category;
    public TableColumn purchase_model;
    public TableColumn purchase_quantity;
    public TableColumn purchase_date;

    private DataBaseConnection dataBaseConnection = new DataBaseConnection();


    public void addToProductColumns()
    {
        product_id.setCellValueFactory(new PropertyValueFactory<Product, String>("productID"));
        product_category_id.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
        product_model.setCellValueFactory(new PropertyValueFactory<Product, String>("model"));
        product_color.setCellValueFactory(new PropertyValueFactory<Product, String>("color"));
        product_price.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        product_quantity.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));
        //dataBaseConnection.refreshProducts();
        product_table.setItems(dataBaseConnection.getProducts());
    }

    public void addToCustomerColumns()
    {
        customer_id.setCellValueFactory(new PropertyValueFactory<Customer,String>("customerID"));
        customer_first_name.setCellValueFactory(new PropertyValueFactory<Customer,String>("firstName"));
        customer_last_name.setCellValueFactory(new PropertyValueFactory<Customer,String>("lastName"));
        customer_phone_number.setCellValueFactory(new PropertyValueFactory<Customer,String>("phoneNumber"));
        customer_gender.setCellValueFactory(new PropertyValueFactory<Customer,String>("gender"));
        customers_table.setItems(dataBaseConnection.getCustomers());
    }

    public void addToCategoryColumns()
    {
        category_id.setCellValueFactory(new PropertyValueFactory<Category, String>("categoryID"));
        category_name.setCellValueFactory(new PropertyValueFactory<Category,String>("name"));
        category_description.setCellValueFactory(new PropertyValueFactory<Category,String>("description"));
        dataBaseConnection.refreshCategories();
        category_table.setItems(dataBaseConnection.getCategories());
    }

    public void addToPurchasecolumns()
    {
        purchase_first_name.setCellValueFactory(new PropertyValueFactory<Purchase,String>("firstName"));
        purchase_last_name.setCellValueFactory(new PropertyValueFactory<Purchase,String>("lastName"));
        purchase_category.setCellValueFactory(new PropertyValueFactory<Purchase,String>("category"));
        purchase_model.setCellValueFactory(new PropertyValueFactory<Purchase,String>("model"));
        purchase_quantity.setCellValueFactory(new PropertyValueFactory<Purchase,String>("quantity"));
        purchase_date.setCellValueFactory(new PropertyValueFactory<Purchase,String>("date"));
        purchase_table.setItems(dataBaseConnection.getPurchases());
    }



    public void productAddBtn(ActionEvent actionEvent) {

        try
        {
            dataBaseConnection.addProduct(Integer.parseInt(product_text_id.getText()),Integer.parseInt(product_category_text.getText()),product_model_text.getText(),
                    product_color_text.getText()
                    ,Integer.parseInt(product_price_text.getText()),Integer.parseInt(product_quantity_text.getText()));
            product_text_id.clear();
            product_category_text.clear();
            product_model_text.clear();
            product_color_text.clear();
            product_price_text.clear();
            product_quantity_text.clear();

        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adding failed");
            alert.setHeaderText("Failed");
            alert.setContentText("Please fill all the information in the boxes bellow");
            alert.showAndWait();
        }

        addToProductColumns();

    }

    public void productDeleteBtn(ActionEvent actionEvent) {

        dataBaseConnection.deleteProduct(product_table.getSelectionModel().getSelectedItem().getProductID());
        dataBaseConnection.refreshProducts();
        addToProductColumns();

    }

    public void productSellBtn(ActionEvent actionEvent) {

        try{

            dataBaseConnection.sell(Integer.parseInt(product_text_id.getText()),product_table.getSelectionModel().getSelectedItem().getProductID());
            product_text_id.clear();
            dataBaseConnection.refreshProducts();
            addToProductColumns();
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selling failed");
            alert.setHeaderText("Failed");
            alert.setContentText("Please specify an ID for the customer");
            alert.showAndWait();
        }

    }

    public void productRefreshBtn(ActionEvent actionEvent) {
        dataBaseConnection.refreshProducts();
        addToProductColumns();
    }

    public void customerAddBtn(ActionEvent actionEvent) {
        try {
            dataBaseConnection.addCustomer(Integer.parseInt(customer_id_text.getText()),customer_first_text.getText(),customer_last_text.getText(),
                    Integer.parseInt(customer_phone_text.getText()),customer_gender_text.getText());
            customer_id_text.clear();
            customer_first_text.clear();
            customer_last_text.clear();
            customer_phone_text.clear();
            customer_gender_text.clear();
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adding failed");
            alert.setHeaderText("Failed");
            alert.setContentText("Please fill all the information in the boxes bellow");
            alert.showAndWait();
        }

        dataBaseConnection.refreshCustomers();
        addToCustomerColumns();

    }

    public void customerDeleteBtn(ActionEvent actionEvent) {
        dataBaseConnection.deleteCustomer(customers_table.getSelectionModel().getSelectedItem().getCustomerID());
        dataBaseConnection.refreshCustomers();
        addToCustomerColumns();
    }

    public void customerSortBtn(ActionEvent actionEvent) {

    }

    public void customerRefreshBtn(ActionEvent actionEvent) {
        dataBaseConnection.refreshCustomers();
        addToCustomerColumns();

    }

    public void categoryAddBtn(ActionEvent actionEvent) {

        try
        {
            dataBaseConnection.addCategory(Integer.parseInt(category_id_text.getText()),category_name_text.getText(),category_description_text.getText());
            category_id_text.clear();
            category_name_text.clear();
            category_description_text.clear();
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adding failed");
            alert.setHeaderText("Failed");
            alert.setContentText("Please fill all the information in the boxes bellow");
            alert.showAndWait();
        }
        addToCategoryColumns();
    }

    public void categoryDeleteBtn(ActionEvent actionEvent) {
        dataBaseConnection.deleteCategory(category_table.getSelectionModel().getSelectedItem().getCategoryID());
        dataBaseConnection.refreshCategories();
        addToCategoryColumns();

    }

    public void categoryRefreshBtn(ActionEvent actionEvent) {
        addToCategoryColumns();
    }

    public void purchase_refresh_btn(ActionEvent actionEvent) {
        dataBaseConnection.refreshPurchases();
        addToPurchasecolumns();
    }

    public void purchase_sort_btn(ActionEvent actionEvent) {
        dataBaseConnection.sortCustomers();
        addToCustomerColumns();
    }

    public void purchase_search_btn(ActionEvent actionEvent) {
        try
        {

            dataBaseConnection.search(purchase_first_text.getText(),purchase_last_text.getText());
            purchase_first_text.clear();
            purchase_last_text.clear();
            addToPurchasecolumns();
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adding failed");
            alert.setHeaderText("Failed");
            alert.setContentText("Please fill all the information in the boxes bellow");
            alert.showAndWait();
        }

    }

    public void productSearchBtn(ActionEvent actionEvent) {
        dataBaseConnection.findProduct(product_category_text.getText());
        addToProductColumns();
    }
}
