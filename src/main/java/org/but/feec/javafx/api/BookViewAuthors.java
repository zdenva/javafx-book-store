package org.but.feec.javafx.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookViewAuthors {
    private LongProperty authorId = new SimpleLongProperty();
    private StringProperty authorFirstName = new SimpleStringProperty();
    private StringProperty authorLastName = new SimpleStringProperty();

    public long getAuthorId() {
        return authorId.get();
    }

    public LongProperty authorIdProperty() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId.set(authorId);
    }

    public String getAuthorFirstName() {
        return authorFirstName.get();
    }

    public StringProperty authorFirstNameProperty() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName.set(authorFirstName);
    }

    public String getAuthorLastName() {
        return authorLastName.get();
    }

    public StringProperty authorLastNameProperty() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName.set(authorLastName);
    }
}
