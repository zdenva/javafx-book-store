package org.but.feec.javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.but.feec.javafx.App;
import org.but.feec.javafx.api.BookCatalog;
import org.but.feec.javafx.data.BookRepository;
import org.but.feec.javafx.exceptions.ExceptionHandler;
import org.but.feec.javafx.services.BookService;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class BookCatalogController {

    @FXML
    private Button bookViewButton;
    @FXML
    private Button bookBuyButton;
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

    private BookService bookService;
    private BookRepository bookRepository;

    @FXML
    private void initialize(){
        bookRepository = new BookRepository();
        bookService = new BookService(bookRepository);

        idCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, Long>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, String>("title"));
        genreCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, String>("genre"));
        pubDateCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, Date>("publicationDate"));
        publisherNameCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, String>("publisherName"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, String>("isbn"));

        ObservableList<BookCatalog> observableBookList = initializeBooksData();
        booksTable.setItems(observableBookList);

        setupHandles();
    }

    private void setupHandles(){

    }
    private ObservableList<BookCatalog> initializeBooksData(){
        List<BookCatalog> books = bookService.getBookCatalog();
        return FXCollections.observableArrayList(books);
    }

    public void buyBook() {
    }
    public void viewBook() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/bookView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600,600);
            Stage stage = new Stage();
            stage.setTitle("Book store - book view");
            stage.setScene(scene);

            stage.show();
        }catch (IOException ex){
            ExceptionHandler.handleException(ex);
        }
    }

}
