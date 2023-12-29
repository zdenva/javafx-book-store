package org.but.feec.javafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.but.feec.javafx.App;
import org.but.feec.javafx.api.CustomerDetails;
import org.but.feec.javafx.exceptions.ExceptionHandler;

import java.io.IOException;

public class MenuController {

    @FXML
    public Button editProfileButton;
    @FXML
    public Button bookCatalogButton;
    @FXML
    public Label emailLabel;
    private CustomerDetails customer;
    public void setCustomer(CustomerDetails customer){
        this.customer = customer;
        initialize();
    }

    private void initialize(){
        emailLabel.setText(customer.getEmail());
    }
    public void showEditProfile(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/CustomerEdit.fxml"));
            Parent root = fxmlLoader.load();
            CustomerEditController controller = fxmlLoader.getController();
            controller.setCustomer(customer);

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Book store");
            stage.setScene(scene);
            Stage stageOld = (Stage) editProfileButton.getScene().getWindow();
            stageOld.close();

            stage.show();
        }catch (IOException ex){
            ExceptionHandler.handleException(ex);
        }
    }

    public void showBookCatalog(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/BookCatalog.fxml"));
            Parent root = fxmlLoader.load();
            BookCatalogController controller = fxmlLoader.getController();
            controller.setCustomer(customer);

            Scene scene = new Scene(root, 600,600);
            Stage stage = new Stage();
            stage.setTitle("Book store");
            stage.setScene(scene);

            Stage stageOld = (Stage) bookCatalogButton.getScene().getWindow();
            stageOld.close();

            stage.show();
        }catch (IOException ex){
            ExceptionHandler.handleException(ex);
        }
    };
    public void logout(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("App.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600,600);
            Stage stage = new Stage();
            stage.setTitle("Book store");
            stage.setScene(scene);

            Stage stageOld = (Stage) editProfileButton.getScene().getWindow();
            stageOld.close();

            stage.show();
        }catch(IOException ex){
            ExceptionHandler.handleException(ex);
        }
    }
}
