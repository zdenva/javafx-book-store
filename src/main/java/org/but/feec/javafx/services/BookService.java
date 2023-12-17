package org.but.feec.javafx.services;

import org.but.feec.javafx.api.BookCatalog;
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
}
