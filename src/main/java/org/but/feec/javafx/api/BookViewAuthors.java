package org.but.feec.javafx.api;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookViewAuthors {
    private StringProperty authorFirstName = new SimpleStringProperty();
    private StringProperty authorLastName = new SimpleStringProperty();

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
