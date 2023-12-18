package org.but.feec.javafx.api;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookViewLanguages {

    private StringProperty languageName = new SimpleStringProperty();
    private StringProperty languageCode = new SimpleStringProperty();

    public String getLanguageName() {
        return languageName.get();
    }

    public StringProperty languageNameProperty() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName.set(languageName);
    }

    public String getLanguageCode() {
        return languageCode.get();
    }

    public StringProperty languageCodeProperty() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode.set(languageCode);
    }
}
