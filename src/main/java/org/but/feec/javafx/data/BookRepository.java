package org.but.feec.javafx.data;

import org.but.feec.javafx.api.BookCatalog;
import org.but.feec.javafx.config.DataSourceConfig;
import org.but.feec.javafx.exceptions.DataAcessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    public List<BookCatalog> getBookCatalog(){
        try{
            Connection connection = DataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT book_id, title, genre, publication_date, publisher_name, isbn FROM book_store.book b JOIN book_store.publisher p ON b.publisher_id = p.publisher_id;");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BookCatalog> bookCatalog = new ArrayList<>();
            while (resultSet.next()) {
                bookCatalog.add(maptoBookCatalog(resultSet));
            }
            return bookCatalog;
            }catch (SQLException ec){
            throw new DataAcessException("Book catalog could not be loaded.", ec);
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
