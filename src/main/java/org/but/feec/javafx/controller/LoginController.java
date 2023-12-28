package org.but.feec.javafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.but.feec.javafx.App;
import org.but.feec.javafx.api.AuthenticationDetails;
import org.but.feec.javafx.api.CustomerDetails;
import org.but.feec.javafx.data.CustomerRepository;
import org.but.feec.javafx.exceptions.ExceptionHandler;
import org.but.feec.javafx.services.AuthenticationService;
import org.but.feec.javafx.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @FXML
    public Label emailLabel;
    @FXML
    public Label passwordLabel;
    @FXML
    public TextField emailTextField;
    @FXML
    public TextField passwordTextField;
    @FXML
    public Button loginButton;
    @FXML
    public Button registerButton;
    AuthenticationService authenticationService = new AuthenticationService();
    CustomerService customerService;
    CustomerRepository customerRepository;

    CustomerDetails customerDetails;

    public void initialize(){
        customerRepository = new CustomerRepository();
        customerService = new CustomerService(customerRepository);
    }
    public void login(){
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        System.out.println("Entered email: " + email);
        AuthenticationDetails authenticationDetails = initializeGetSaltHashByEmail(email);
        if( !authenticationDetails.equals(null)){
            if (authenticationService.verifyPassword(password, authenticationDetails)){
                System.out.println("Password is valid.");
                customerDetails = initializeCustomerDetails(authenticationDetails.getId());
                showMenu();
            }
            else{
                System.out.println("Password is not valid.");
            }
        }
        else {
            System.out.println("Email isn't in the database.");
        }

    }

    public void showMenu(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/Menu.fxml"));
            Parent root = fxmlLoader.load();
            MenuController controller = fxmlLoader.getController();
            controller.setCustomer(customerDetails);

            Scene scene = new Scene(root, 1000, 600);
            Stage stage = new Stage();
            stage.setResizable(true);
            stage.setTitle("Book store - menu");
            stage.setScene(scene);

            Stage stageOld = (Stage) loginButton.getScene().getWindow();
            stageOld.close();

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    private AuthenticationDetails initializeGetSaltHashByEmail(String email){
        return customerService.getSaltHashByEmail(email);
    }
    private CustomerDetails initializeCustomerDetails(Long id){
        return customerService.getCustomerDetails(id);
    }
    public void register(){
    }


}




