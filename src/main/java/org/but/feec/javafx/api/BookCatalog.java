package org.but.feec.javafx.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Date;

public class BookCatalog {
    private LongProperty id = new SimpleLongProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty genre = new SimpleStringProperty();
    private ObjectProperty<Date> publicationDate = new SimpleObjectProperty();
    private StringProperty publisherName = new SimpleStringProperty();
    private StringProperty isbn = new SimpleStringProperty();

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getGenre() {
        return genre.get();
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public Date getPublicationDate() {
        return publicationDate.get();
    }

    public ObjectProperty<Date> publicationDateProperty() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate.set(publicationDate);
    }

    public String getPublisherName() {
        return publisherName.get();
    }

    public StringProperty publisherNameProperty() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName.set(publisherName);
    }

    public String getIsbn() {
        return isbn.get();
    }

    public StringProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }
}
