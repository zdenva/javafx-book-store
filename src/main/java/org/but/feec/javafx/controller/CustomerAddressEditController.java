package org.but.feec.javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import org.but.feec.javafx.api.CustomerAddress;
import org.but.feec.javafx.data.CustomerRepository;
import org.but.feec.javafx.services.CustomerService;

public class CustomerAddressEditController {
    @FXML
    private Button actionButton;

    @FXML
    private TextField cityInput;

    @FXML
    private TextField countryInput;

    @FXML
    private TextField streetNameInput;

    @FXML
    private TextField streetNumberInput;
    private String state;
    private Long customerId;
    private CustomerAddress customerAddress = null;
    private CustomerService customerService;
    private CustomerRepository customerRepository;

    public void setState(String state) {
        this.state = state;
        initialize();
    }

    public void setCustomerId(Long customerId){
        this.customerId = customerId;
    }


    public void setAddress(CustomerAddress customerAddress){
        this.customerAddress = customerAddress;
    }

    private void  initialize(){
        customerRepository = new CustomerRepository();
        customerService = new CustomerService(customerRepository);
        if (state == "Create"){
            actionButton.setText("Create Address");
            actionButton.setOnMouseClicked(e -> initializeCreateAddress(customerId));
        } else if (state == "Edit") {
            actionButton.setText("Edit Address");
            updateInputs();
            actionButton.setOnMouseClicked(e -> initializeEditAddress());
        }
    }

    private void updateInputs(){
        if(customerAddress != null){
            cityInput.setText(customerAddress.getCity());
            countryInput.setText(customerAddress.getCountry());
            streetNameInput.setText(customerAddress.getStreetName());
            streetNumberInput.setText(Integer.toString(customerAddress.getStreetNumber()));
        }
    }
    private void getAddressFromInputs(){
        customerAddress.setCity(cityInput.getText());
        customerAddress.setCountry(countryInput.getText());
        customerAddress.setStreetName(streetNameInput.getText());
        customerAddress.setStreetNumber(Integer.parseInt(streetNumberInput.getText()));
    }

    private void initializeCreateAddress(Long customerId){
        System.out.println("Create address.");
        this.customerAddress = new CustomerAddress();
        getAddressFromInputs();
        customerService.createAddress(customerId, customerAddress);
    }
    private void initializeEditAddress(){
        getAddressFromInputs();
        System.out.println("Edit address, address id: " + customerAddress.getId());
        customerService.editAddress(customerAddress);
    }
}
