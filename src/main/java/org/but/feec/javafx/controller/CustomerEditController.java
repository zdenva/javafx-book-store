package org.but.feec.javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CustomerEditController {
    @FXML
    private Button addAddressButton;

    @FXML
    private TableView<?> addressTable;

    @FXML
    private TableColumn<?, ?> cityCol;

    @FXML
    private TableColumn<?, ?> countryCol;

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
    private TextField phoneNuberInput;

    @FXML
    private TableColumn<?, ?> streetNameCol;

    @FXML
    private TableColumn<?, ?> streetNumberCol;

    @FXML
    private Button updateButton;
}
