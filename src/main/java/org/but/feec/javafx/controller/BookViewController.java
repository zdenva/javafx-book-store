package org.but.feec.javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.but.feec.javafx.api.BookViewAuthors;
import org.but.feec.javafx.api.BookViewDetails;
import org.but.feec.javafx.data.BookRepository;
import org.but.feec.javafx.services.BookService;

import java.util.List;

public class BookViewController {

    @FXML
    private Label viewLabel;
    @FXML
    private TableView<BookViewDetails> bookDetailTable;
    @FXML
    private TableColumn<BookViewDetails, String> detailNameCol;
    @FXML
    private TableColumn<BookViewDetails, String> detailValueCol;
    @FXML
    private TableView<BookViewAuthors> bookAuthorTable;
    @FXML
    private TableColumn<BookViewAuthors, Long> authorIdCol;
    @FXML
    private TableColumn<BookViewAuthors, String> authorFirstNameCol;
    @FXML
    private TableColumn<BookViewAuthors, String> authorLastNameCol;
    @FXML
    private BookService bookService;
    private BookRepository bookRepository;

    private Long bookId;
    public void setBookId(Long id) {
        this.bookId = id;
        initialize();
    }

    private void initialize(){
    bookRepository = new BookRepository();
    bookService = new BookService(bookRepository);

    detailNameCol.setCellValueFactory(new PropertyValueFactory<BookViewDetails, String>("detailName"));
    detailValueCol.setCellValueFactory(new PropertyValueFactory<BookViewDetails, String>("detailValue"));
    authorIdCol.setCellValueFactory(new PropertyValueFactory<BookViewAuthors, Long>("authorId"));
    authorFirstNameCol.setCellValueFactory(new PropertyValueFactory<BookViewAuthors, String>("authorFirstName"));
    authorLastNameCol.setCellValueFactory(new PropertyValueFactory<BookViewAuthors, String>("authorLastName"));

    ObservableList<BookViewDetails> observableBookList1 = initializeBookViewDetailsData(bookId);
    bookDetailTable.setItems(observableBookList1);

    ObservableList<BookViewAuthors> observableBookList2 = initializeBookViewAuthorsData(bookId);
    bookAuthorTable.setItems(observableBookList2);

    }

    private ObservableList<BookViewDetails> initializeBookViewDetailsData(long id){
        List<BookViewDetails> book = bookService.getBookViewDetails(id);
        return FXCollections.observableArrayList(book);
    }

    private ObservableList<BookViewAuthors> initializeBookViewAuthorsData(long id){
        List<BookViewAuthors> book = bookService.getBookViewAuthors(id);
        return FXCollections.observableArrayList(book);
    }
}
