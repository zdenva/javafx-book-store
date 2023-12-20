package org.but.feec.javafx.api;

import javafx.beans.property.*;

public class CustomerAddress {

    private LongProperty id = new SimpleLongProperty();
    private LongProperty countryId = new SimpleLongProperty();
    private StringProperty country = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty streetName = new SimpleStringProperty();
    private IntegerProperty streetNumber = new SimpleIntegerProperty();

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public long getCountryId() {
        return countryId.get();
    }

    public LongProperty countryIdProperty() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId.set(countryId);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getStreetName() {
        return streetName.get();
    }

    public StringProperty streetNameProperty() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName.set(streetName);
    }

    public int getStreetNumber() {
        return streetNumber.get();
    }

    public IntegerProperty streetNumberProperty() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber.set(streetNumber);
    }
}
