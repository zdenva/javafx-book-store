package org.but.feec.javafx.services;

import org.but.feec.javafx.api.BookCatalog;
import org.but.feec.javafx.api.BookViewAuthors;
import org.but.feec.javafx.api.BookViewDescriptions;
import org.but.feec.javafx.api.BookViewLanguages;
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

    public  List<BookViewDescriptions> getBookViewDetails(long id ){
        return bookRepository.getBookViewDetails(id);
    }
    public List<BookViewAuthors> getBookViewAuthors(long id ){
        return bookRepository.getBookViewAuthors(id);
    }
    public List<BookViewLanguages> getBookViewLanguages(long id ){
        return bookRepository.getBookViewLanguages(id);
    }
}
