package org.but.feec.javafx.api;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AuthenticationDetails {
    private StringProperty passwordHash = new SimpleStringProperty();
    private StringProperty passwordSalt = new SimpleStringProperty();

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
