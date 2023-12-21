package org.but.feec.javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.but.feec.javafx.App;
import org.but.feec.javafx.api.BookCatalog;
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

    CustomerRepository customerRepository;
    CustomerService customerService;
    private Long phoneId;
    private Long customerId;

    public void setCustomerId(Long customerId){
        this.customerId = customerId;
        initialize();
    }

    private void initialize(){
        customerRepository = new CustomerRepository();
        customerService = new CustomerService(customerRepository);
        streetNameCol.setCellValueFactory(new PropertyValueFactory<CustomerAddress, String>("streetName"));
        streetNumberCol.setCellValueFactory(new PropertyValueFactory<CustomerAddress, Integer>("streetNumber"));
        cityCol.setCellValueFactory(new PropertyValueFactory<CustomerAddress, String>("city"));
        countryCol.setCellValueFactory(new PropertyValueFactory<CustomerAddress, String>("country"));
        ObservableList<CustomerAddress> observableList = initializeCustomerAddress(customerId);
        addressTable.setItems(observableList);

        CustomerDetails customerDetails = initializeCustomerDetails(customerId);
        emailInput.setText(customerDetails.getEmail());
        firstNameInput.setText(customerDetails.getFirstName());
        lastNameInput.setText(customerDetails.getLastName());
        phoneId = customerDetails.getPhoneId();
        if(phoneId != 0) {
            phoneNumberInput.setText(customerDetails.getPhoneNumber());
        }
    }

    @FXML
    public void updateCustomer(){
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setId(customerId);
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

    private CustomerDetails initializeCustomerDetails(Long id){
        return customerService.getCustomerDetails(id);
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
    }

    public void showCustomerAddressEdit(String state){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/CustomerAddressEdit.fxml"));

            Parent root = fxmlLoader.load();
            CustomerAddressEditController controller = fxmlLoader.getController();
            if(state == "Edit"){
                CustomerAddress customerAddress = addressTable.getSelectionModel().getSelectedItem();
                if (customerAddress == null){
                    throw new IOException();
                }
                controller.setAddress(customerAddress);
            }
            controller.setState(state);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Book store - book view");
            stage.setScene(scene);

            stage.show();
        }catch (IOException ex){
            ExceptionHandler.handleException(ex);
        }
    }
}
