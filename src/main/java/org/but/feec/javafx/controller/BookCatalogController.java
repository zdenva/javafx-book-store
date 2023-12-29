package org.but.feec.javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.but.feec.javafx.App;
import org.but.feec.javafx.api.BookCatalog;
import org.but.feec.javafx.api.CustomerDetails;
import org.but.feec.javafx.data.BookRepository;
import org.but.feec.javafx.exceptions.ExceptionHandler;
import org.but.feec.javafx.services.BookService;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class BookCatalogController {

    @FXML
    public Label emailLabel;
    @FXML
    private TableView<BookCatalog> booksTable;
    @FXML
    private TableColumn<BookCatalog, Long> idCol;
    @FXML
    private TableColumn<BookCatalog, String> titleCol;
    @FXML
    private TableColumn<BookCatalog, String> genreCol;
    @FXML
    private TableColumn<BookCatalog, Date> pubDateCol;
    @FXML
    private TableColumn<BookCatalog, String> publisherNameCol;
    @FXML
    private TableColumn<BookCatalog, String> isbnCol;

    @FXML
    private TextField titleFilterInput;
    private BookService bookService;
    private CustomerDetails customer;

    public void setCustomer(CustomerDetails customer){
        this.customer = customer;
        initialize();

    }

    private void initialize(){
        emailLabel.setText(customer.getEmail());
        BookRepository bookRepository = new BookRepository();
        bookService = new BookService(bookRepository);

        idCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, Long>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, String>("title"));
        genreCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, String>("genre"));
        pubDateCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, Date>("publicationDate"));
        publisherNameCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, String>("publisherName"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, String>("isbn"));

        ObservableList<BookCatalog> observableBookList = initializeBooksData();
        FilteredList<BookCatalog> filteredList = new FilteredList<>(observableBookList, p -> true);
        booksTable.setItems(filteredList);

        titleFilterInput.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(bookCatalog -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            return bookCatalog.getTitle().toLowerCase().contains(lowerCaseFilter);
        }));
        setupHandles();
    }

    private void setupHandles(){

    }
    private ObservableList<BookCatalog> initializeBooksData(){
        List<BookCatalog> books = bookService.getBookCatalog();
        return FXCollections.observableArrayList(books);
    }

    public void viewBook() {
        try {
            BookCatalog bookCatalog = booksTable.getSelectionModel().getSelectedItem();
            Long bookId = bookCatalog.getId();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/BookView.fxml"));

            Parent root = fxmlLoader.load();
            BookViewController controller = fxmlLoader.getController();
            controller.setBookId(bookId);

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

            Stage stageOld = (Stage) emailLabel.getScene().getWindow();
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
