package org.but.feec.javafx.services;

import org.but.feec.javafx.api.BookCatalog;
import org.but.feec.javafx.api.BookViewAuthors;
import org.but.feec.javafx.api.BookViewDetails;
import org.but.feec.javafx.data.BookRepository;

import java.util.List;

public class BookService {

    private BookRepository bookRepository;
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookCatalog> getBookCatalog(){
        return bookRepository.getBookCatalog();
    }

    public  List<BookViewDetails> getBookViewDetails(long id ){
        return bookRepository.getBookViewDetails(id);
    }
    public List<BookViewAuthors> getBookViewAuthors(long id ){
        return bookRepository.getBookViewAuthors(id);
    }
}
