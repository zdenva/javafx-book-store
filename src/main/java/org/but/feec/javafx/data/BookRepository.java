package org.but.feec.javafx.data;

import org.but.feec.javafx.api.BookCatalog;
import org.but.feec.javafx.api.BookViewAuthors;
import org.but.feec.javafx.api.BookViewDescriptions;
import org.but.feec.javafx.api.BookViewLanguages;
import org.but.feec.javafx.config.DataSourceConfig;
import org.but.feec.javafx.exceptions.DataAcessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {


    public List<BookViewDescriptions> getBookViewDetails(long id){
        try {
            Connection connection = DataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT book_id, title, genre, isbn, num_pages, publication_date, publisher_name FROM book_store.book NATURAL JOIN book_store.publisher WHERE book_id = ?",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BookViewDescriptions> bookViewDetails = new ArrayList<>();

            BookViewDescriptions bookViewDescriptions = new BookViewDescriptions();
            bookViewDescriptions.setDetailName("id");
            bookViewDescriptions.setDetailValue(Long.toString(resultSet.getLong("book_id")));
            System.out.println(resultSet.getLong("book_id"));
//            bookViewDetails.add(bookViewDescriptions);

            return bookViewDetails;
        }catch (SQLException ex){
            throw new DataAcessException("Book catalog could not be loaded.", ex);
        }
    }
    public List<BookViewAuthors> getBookViewAuthors(long id){
        try {
            Connection connection = DataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT book_id, title, genre, publication_date, publisher_name, isbn FROM book_store.book b JOIN book_store.publisher p ON b.publisher_id = p.publisher_id;");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BookViewAuthors> bookViewAuthors = new ArrayList<>();
            return bookViewAuthors;

        }catch (SQLException ex){
            throw new DataAcessException("Book catalog could not be loaded.", ex);
        }
    }
    public List<BookViewLanguages> getBookViewLanguages(long id){
        try {
            Connection connection = DataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT book_id, title, genre, publication_date, publisher_name, isbn FROM book_store.book b JOIN book_store.publisher p ON b.publisher_id = p.publisher_id;");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BookViewLanguages> bookViewLanguages = new ArrayList<>();
            return bookViewLanguages;
        }catch (SQLException ex){
            throw new DataAcessException("Book catalog could not be loaded.", ex);
        }
    }
    public List<BookCatalog> getBookCatalog(){
        try{
            Connection connection = DataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT book_id, title, genre, publication_date, publisher_name, isbn FROM book_store.book b JOIN book_store.publisher p ON b.publisher_id = p.publisher_id;");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BookCatalog> bookCatalog = new ArrayList<>();
            int i = 1;
            while (resultSet.next()) {
                bookCatalog.add(maptoBookCatalog(resultSet));
            }
            return bookCatalog;
            }catch (SQLException ex){
            throw new DataAcessException("Book catalog could not be loaded.", ex);
        }
    }

    private BookCatalog maptoBookCatalog(ResultSet rs) throws SQLException{
        BookCatalog bookCatalog = new BookCatalog();
        bookCatalog.setId(rs.getLong("book_id"));
        bookCatalog.setTitle(rs.getString("title"));
        bookCatalog.setGenre(rs.getString("genre"));
        bookCatalog.setPublicationDate(rs.getDate("publication_date"));
        bookCatalog.setPublisherName(rs.getString("publisher_name"));
        bookCatalog.setIsbn(rs.getString("isbn"));

        return bookCatalog;
    }
}
