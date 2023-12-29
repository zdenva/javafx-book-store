package org.but.feec.javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.but.feec.javafx.App;
import org.but.feec.javafx.api.CustomerAddress;
import org.but.feec.javafx.api.CustomerDetails;
import org.but.feec.javafx.data.CustomerRepository;
import org.but.feec.javafx.exceptions.ExceptionHandler;
import org.but.feec.javafx.services.CustomerService;

import java.io.IOException;
import java.util.List;

public class CustomerEditController {
    @FXML
    private Button addAddressButton;

    @FXML
    private TableView<CustomerAddress> addressTable;

    @FXML
    private TableColumn<CustomerAddress, String> cityCol;

    @FXML
    private TableColumn<CustomerAddress, String> countryCol;

    @FXML
    private Button deleteAddressButton;

    @FXML
    private Button editAddressButton;

    @FXML
    private TextField emailInput;

    @FXML
    private TextField firstNameInput;

    @FXML
    private TextField lastNameInput;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private TableColumn<CustomerAddress, String> streetNameCol;

    @FXML
    private TableColumn<CustomerAddress, Integer> streetNumberCol;

    @FXML
    private Button updateButton;
    public Label emailLabel;

    CustomerRepository customerRepository;
    CustomerService customerService;
    private Long phoneId;
    private CustomerDetails customer;

    public void setCustomer(CustomerDetails customer){
        this.customer = customer;
        initialize();
    }

    private void initialize(){
        emailLabel.setText(customer.getEmail());
        customerRepository = new CustomerRepository();
        customerService = new CustomerService(customerRepository);
        streetNameCol.setCellValueFactory(new PropertyValueFactory<CustomerAddress, String>("streetName"));
        streetNumberCol.setCellValueFactory(new PropertyValueFactory<CustomerAddress, Integer>("streetNumber"));
        cityCol.setCellValueFactory(new PropertyValueFactory<CustomerAddress, String>("city"));
        countryCol.setCellValueFactory(new PropertyValueFactory<CustomerAddress, String>("country"));
        updateAddressTable();


        emailInput.setText(customer.getEmail());
        firstNameInput.setText(customer.getFirstName());
        lastNameInput.setText(customer.getLastName());
        phoneId = customer.getPhoneId();
        if(phoneId != 0) {
            phoneNumberInput.setText(customer.getPhoneNumber());
        }
    }

    public void updateAddressTable(){
        ObservableList<CustomerAddress> observableList = initializeCustomerAddress(customer.getId());
        addressTable.setItems(observableList);
    }

    @FXML
    public void updateCustomer(){
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setId(customer.getId());
        customerDetails.setPhoneId(phoneId);
        customerDetails.setEmail(emailInput.getText());
        customerDetails.setFirstName(firstNameInput.getText());
        customerDetails.setLastName(lastNameInput.getText());
        customerDetails.setPhoneNumber(phoneNumberInput.getText());

        customerService.editCustomerDetails(customerDetails);
        System.out.println("Success updating.");
    }

    private ObservableList<CustomerAddress> initializeCustomerAddress(Long id){
        List<CustomerAddress> addresses = customerService.getCustomerAddress(id);
        return FXCollections.observableArrayList(addresses);
    }



    @FXML
    public void addAddress() {
        showCustomerAddressEdit("Create");
    }
    @FXML
    public void editAddress() {
        showCustomerAddressEdit("Edit");
    }
    @FXML
    public void deleteAddress() {
        Long addressId = 0L;
        addressId = addressTable.getSelectionModel().getSelectedItem().getId();
        if(addressId == 0L){
            System.out.println("Nothing is selected.");
        }
        else {
            customerService.deleteAddress(addressId);
            updateAddressTable();
        }
    }

    public void showCustomerAddressEdit(String state){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/CustomerAddressEdit.fxml"));

            Parent root = fxmlLoader.load();
            CustomerAddressEditController controller = fxmlLoader.getController();
            if(state == "Edit") {
                CustomerAddress customerAddress = addressTable.getSelectionModel().getSelectedItem();
                if (customerAddress == null) {
                    throw new IOException();
                }
                controller.setAddress(customerAddress);
            }
            controller.setCustomerId(customer.getId());
            controller.setState(state);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Book store");
            stage.setScene(scene);

            stage.show();
        }catch (IOException ex){
            ExceptionHandler.handleException(ex);
        }
    }
    public void logout(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("App.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600,600);
            Stage stage = new Stage();
            stage.setTitle("Book store");
            stage.setScene(scene);

            Stage stageOld = (Stage) updateButton.getScene().getWindow();
            stageOld.close();

            stage.show();
        }catch(IOException ex){
            ExceptionHandler.handleException(ex);
        }
    }
    public void menu(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/Menu.fxml"));
            Parent root = fxmlLoader.load();
            MenuController controller = fxmlLoader.getController();
            controller.setCustomer(customer);

            Scene scene = new Scene(root, 600,600);
            Stage stage = new Stage();
            stage.setTitle("Book store");
            stage.setScene(scene);

            Stage stageOld = (Stage) emailLabel.getScene().getWindow();
            stageOld.close();

            stage.show();
        }catch(IOException ex){
            ExceptionHandler.handleException(ex);
        }
    }
}
