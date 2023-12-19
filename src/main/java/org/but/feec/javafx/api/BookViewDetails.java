package org.but.feec.javafx.api;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookViewDetails {

    private StringProperty detailName = new SimpleStringProperty();
    private StringProperty detailValue = new SimpleStringProperty();



    public String getDetailName() {
        return detailName.get();
    }

    public StringProperty detailNameProperty() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName.set(detailName);
    }

    public String getDetailValue() {
        return detailValue.get();
    }

    public StringProperty detailValueProperty() {
        return detailValue;
    }

    public void setDetailValue(String detailValue) {
        this.detailValue.set(detailValue);
    }




}