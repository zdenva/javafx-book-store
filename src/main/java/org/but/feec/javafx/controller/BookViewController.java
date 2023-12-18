package org.but.feec.javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.but.feec.javafx.api.BookViewAuthors;
import org.but.feec.javafx.api.BookViewDescriptions;
import org.but.feec.javafx.api.BookViewLanguages;
import org.but.feec.javafx.data.BookRepository;
import org.but.feec.javafx.services.BookService;

import java.util.List;

public class BookViewController {

    @FXML
    private Label viewLabel;
    @FXML
    private TableView<BookViewDescriptions> bookDetailTable;
    @FXML
    private TableColumn<BookViewDescriptions, String> detailNameCol;
    @FXML
    private TableColumn<BookViewDescriptions, String> detailValueCol;
    @FXML
    private TableView<BookViewAuthors> bookAuthorTable;
    @FXML
    private TableColumn<BookViewAuthors, String> authorFirstNameCol;
    @FXML
    private TableColumn<BookViewAuthors, String> authorLastNameCol;
    @FXML
    private TableView<BookViewLanguages> bookLanguageTable;
    @FXML
    private TableColumn<BookViewLanguages, String> languageNameCol;
    @FXML
    private TableColumn<BookViewLanguages, String> languageCodeCol;

    private BookService bookService;
    private BookRepository bookRepository;

    @FXML
    private void initialize(){
    bookRepository = new BookRepository();
    bookService = new BookService(bookRepository);

    int id = 2;

    detailNameCol.setCellValueFactory(new PropertyValueFactory<BookViewDescriptions, String>("detailName"));
    detailValueCol.setCellValueFactory(new PropertyValueFactory<BookViewDescriptions, String>("detailValue"));
//    authorFirstNameCol.setCellValueFactory(new PropertyValueFactory<BookViewAuthors, String>("authorFistName"));
//    authorLastNameCol.setCellValueFactory(new PropertyValueFactory<BookViewAuthors, String>("authorLastName"));
//    languageNameCol.setCellValueFactory(new PropertyValueFactory<BookViewLanguages, String>("languageName"));
//    languageCodeCol.setCellValueFactory(new PropertyValueFactory<BookViewLanguages, String>("languageValue"));

    ObservableList<BookViewDescriptions> observableBookList1 = initializeBookViewDetailsData(id);
    bookDetailTable.setItems(observableBookList1);
//
//    ObservableList<BookViewAuthors> observableBookList2 = initializeBookViewAuthorsData(id);
//    bookAuthorTable.setItems(observableBookList2);
//
//    ObservableList<BookViewLanguages> observableBookList3 = initializeBookViewLanguagesData(id);
//    bookLanguageTable.setItems(observableBookList3);

    }

    private ObservableList<BookViewDescriptions> initializeBookViewDetailsData(long id){
        List<BookViewDescriptions> book = bookService.getBookViewDetails(id);
        return FXCollections.observableArrayList(book);
    }

    private ObservableList<BookViewAuthors> initializeBookViewAuthorsData(long id){
        List<BookViewAuthors> book = bookService.getBookViewAuthors(id);
        return FXCollections.observableArrayList(book);
    }

    private ObservableList<BookViewLanguages> initializeBookViewLanguagesData(long id){
        List<BookViewLanguages> book = bookService.getBookViewLanguages(id);
        return FXCollections.observableArrayList(book);
    }
}
