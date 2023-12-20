package org.but.feec.javafx.data;

import org.but.feec.javafx.api.BookCatalog;
import org.but.feec.javafx.api.BookViewAuthors;
import org.but.feec.javafx.api.BookViewDetails;
import org.but.feec.javafx.config.DataSourceConfig;
import org.but.feec.javafx.exceptions.DataAcessException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookRepository {


    public List<BookViewDetails> getBookViewDetails(long id){
        try {
            Connection connection = DataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT book_id, title, genre, isbn, num_pages, publication_date, publisher_name, language_name, language_code FROM book_store.book NATURAL JOIN book_store.publisher NATURAL JOIN book_store.book_language WHERE book_id = ?;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BookViewDetails> bookViewDetails = new ArrayList<>();
            resultSet.next();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            return Stream.of(
                    createDetail("Id", Long.toString(resultSet.getLong("book_id"))),
                    createDetail("Title", resultSet.getString("title")),
                    createDetail("Genre", resultSet.getString("genre")),
                    createDetail("ISBN", resultSet.getString("isbn")),
                    createDetail("Number of pages", Long.toString(resultSet.getInt("num_pages"))),
                    createDetail("Publication date", dateFormat.format(resultSet.getDate("publication_date"))),
                    createDetail("Publisher name", resultSet.getString("publisher_name")),
                    createDetail("Language name", resultSet.getString("language_name")),
                    createDetail("Language code", resultSet.getString("language_code"))
            ).collect(Collectors.toList());
        }catch (SQLException ex){
            throw new DataAcessException("Book catalog could not be loaded.", ex);
        }
    }

    private BookViewDetails createDetail(String name, String value) {
        BookViewDetails detail = new BookViewDetails();
        detail.setDetailName(name);
        detail.setDetailValue(value);
        return detail;
    }
    public List<BookViewAuthors> getBookViewAuthors(long id){
        try {
            Connection connection = DataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT author_id, author_first_name, author_last_name FROM book_store.author NATURAL JOIN book_store.book_author WHERE book_id = ?;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BookViewAuthors> bookViewAuthors = new ArrayList<>();
            while (resultSet.next()){
                bookViewAuthors.add(mapToBookViewAuthors(resultSet));
            }
            return bookViewAuthors;

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
            while (resultSet.next()) {
                bookCatalog.add(mapToBookCatalog(resultSet));
            }
            return bookCatalog;
            }catch (SQLException ex){
            throw new DataAcessException("Book catalog could not be loaded.", ex);
        }
    }

    private BookCatalog mapToBookCatalog(ResultSet rs) throws SQLException{
        BookCatalog bookCatalog = new BookCatalog();
        bookCatalog.setId(rs.getLong("book_id"));
        bookCatalog.setTitle(rs.getString("title"));
        bookCatalog.setGenre(rs.getString("genre"));
        bookCatalog.setPublicationDate(rs.getDate("publication_date"));
        bookCatalog.setPublisherName(rs.getString("publisher_name"));
        bookCatalog.setIsbn(rs.getString("isbn"));

        return bookCatalog;
    }
    private BookViewAuthors mapToBookViewAuthors(ResultSet rs) throws SQLException{
        BookViewAuthors bookViewAuthors = new BookViewAuthors();
        bookViewAuthors.setAuthorId(rs.getLong("author_id"));
        bookViewAuthors.setAuthorFirstName(rs.getString("author_first_name"));
        bookViewAuthors.setAuthorLastName(rs.getString("author_last_name"));

        return bookViewAuthors;
    }
}
