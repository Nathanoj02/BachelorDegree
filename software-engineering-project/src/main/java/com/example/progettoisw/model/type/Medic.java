package com.example.progettoisw.model.type;

import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Medic extends GeneralType {

    private static final String STRING_ID = "medic_id";
    private final ReadOnlyIntegerWrapper medic_id = new ReadOnlyIntegerWrapper(this, STRING_ID);
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final StringProperty surname = new SimpleStringProperty(this, "surname");
    private final StringProperty email = new SimpleStringProperty(this, "email");
    private final StringProperty password = new SimpleStringProperty(this, "password");
    private final Property[] properties = new Property[]{
            name,
            surname,
            email,
            password,
    };
    private final HashMap<Property, String> propertyType = new HashMap<>(Map.of(
            name, String.class.getName(),
            surname, String.class.getName(),
            email, String.class.getName(),
            password, String.class.getName()
    ));

    public Medic(String name, String surname, String email, String password) {
        this.medic_id.set(-1);
        this.name.set(name);
        this.surname.set(surname);
        this.email.set(email.toLowerCase());
        this.password.set(password);
    }

    public Medic(int medic_id, String name, String surname, String email, String password) {
        this.medic_id.set(medic_id);
        this.name.set(name);
        this.surname.set(surname);
        this.email.set(email.toLowerCase());
        this.password.set(password);
    }

    public static Medic getFromResultSet(ResultSet rs) throws SQLException {
        return new Medic(
                rs.getInt("medic_id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("email"),
                rs.getString("password")
        );
    }

    public ReadOnlyIntegerWrapper medicIdProperty() {
        return medic_id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email.toLowerCase());
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    @Override
    public GeneralType copyWithDiffId(int id) {
        return new Medic(id, name.get(), surname.get(), email.get(), password.get());
    }

    @Override
    public int getId() {
        return medic_id.get();
    }

    @Override
    public String getStringId() {
        return STRING_ID;
    }

    @Override
    public Property[] getProperties() {
        return properties;
    }

    @Override
    public HashMap<Property, String> getPropertiesType() {
        return propertyType;
    }
}
