package org.but.feec.javafx.api;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AuthenticationDetails {
    private LongProperty id = new SimpleLongProperty();
    private StringProperty passwordHash = new SimpleStringProperty();
    private StringProperty passwordSalt = new SimpleStringProperty();

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getPasswordHash() {
        return passwordHash.get();
    }

    public StringProperty passwordHashProperty() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash.set(passwordHash);
    }

    public String getPasswordSalt() {
        return passwordSalt.get();
    }

    public StringProperty passwordSaltProperty() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt.set(passwordSalt);
    }
}
