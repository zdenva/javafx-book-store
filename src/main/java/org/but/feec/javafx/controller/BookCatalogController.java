package org.but.feec.javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.but.feec.javafx.api.BookCatalog;
import org.but.feec.javafx.data.BookRepository;
import org.but.feec.javafx.services.BookService;

import java.util.Date;
import java.util.List;

public class BookCatalogController {
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

        idCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, Long>("book_id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, String>("title"));
        genreCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, String>("genre"));
        pubDateCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, Date>("publication_date"));
        publisherNameCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, String>("publisher_name"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<BookCatalog, String>("isbn"));

        ObservableList<BookCatalog> observableBookList = initializeBooksData();
        booksTable.setItems(observableBookList);
    }
    private ObservableList<BookCatalog> initializeBooksData(){
        List<BookCatalog> books = bookService.getBookCatalog();
        return FXCollections.observableArrayList(books);
    }
}
