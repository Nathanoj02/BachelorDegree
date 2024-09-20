package com.example.progettoisw.model.type;

import com.example.progettoisw.model.Model;
import com.example.progettoisw.utils.StringUtils;
import com.example.progettoisw.utils.properties.StringListProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patient extends GeneralType {

    private static final String STRING_ID = "patient_id";
    private final ReadOnlyIntegerWrapper patient_id = new ReadOnlyIntegerWrapper(this, STRING_ID);
    private final ReadOnlyIntegerWrapper medic_id_ref = new ReadOnlyIntegerWrapper(this, "medic_id");
    private final StringProperty email = new SimpleStringProperty(this, "email");
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final StringProperty surname = new SimpleStringProperty(this, "surname");
    private final StringProperty password = new SimpleStringProperty(this, "password");
    private final StringListProperty<String> risk_factors = new StringListProperty<>(String.class, this, "risk_factors");
    private final StringListProperty<String> previous_diseases = new StringListProperty(String.class, this, "previous_diseases");
    private final StringListProperty<String> comorbidity = new StringListProperty(String.class, this, "comorbidity");
    private final Property[] properties = new Property[]{
            medic_id_ref,
            email,
            name,
            surname,
            password,
            risk_factors,
            previous_diseases,
            comorbidity
    };
    private final HashMap<Property, String> propertyType = new HashMap<>(Map.of(
            medic_id_ref, Integer.class.getName(),
            email, String.class.getName(),
            name, String.class.getName(),
            surname, String.class.getName(),
            password, String.class.getName(),
            risk_factors, String.class.getName(),
            previous_diseases, String.class.getName(),
            comorbidity, String.class.getName()
    ));

    public Patient(int medic_id, String email, String name, String surname, String password, List<String> risk_factors, List<String> previous_diseases, List<String> comorbidity) {
        this.patient_id.set(-1);
        this.medic_id_ref.set(medic_id);
        this.email.set(email.toLowerCase());
        this.name.set(name);
        this.surname.set(surname);
        this.password.set(password);
        this.risk_factors.addAll(risk_factors);
        this.previous_diseases.addAll(previous_diseases);
        this.comorbidity.addAll(comorbidity);
    }

    public Patient(int patient_id, int medic_id, String email, String name, String surname, String password, String risk_factors, String previous_diseases, String comorbidity) {
        this.patient_id.set(patient_id);
        this.medic_id_ref.set(medic_id);
        this.email.set(email.toLowerCase());
        this.name.set(name);
        this.surname.set(surname);
        this.password.set(password);
        this.risk_factors.set(risk_factors);
        this.previous_diseases.set(previous_diseases);
        this.comorbidity.set(comorbidity);
    }

    public static Patient getFromResultSet(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getInt("patient_id"),
                rs.getInt("medic_id"),
                rs.getString("email"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("password"),
                rs.getString("risk_factors"),
                rs.getString("previous_diseases"),
                rs.getString("comorbidity")
        );
    }

    public ReadOnlyIntegerWrapper patientIdProperty() {
        return patient_id;
    }

    public int getMedicId() {
        return medic_id_ref.get();
    }

    public ReadOnlyIntegerWrapper medicIdRefProperty() {
        return medic_id_ref;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email.toLowerCase());
    }

    public String getFullName() {
        return getName() + " " + getSurname();
    }

    public String getFullNameCapitalized() {
        return StringUtils.capitalize(getName()) + " " + StringUtils.capitalize(getSurname());
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

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringListProperty<String> listRiskFactorsProperty() {
        return risk_factors;
    }

    public StringListProperty<String> listPreviousDiseasesProperty() {
        return previous_diseases;
    }

    public StringListProperty<String> listComorbidityProperty() {
        return comorbidity;
    }

    public DailySurveys createDailySurveys(int sbp, int dbp, Date date) {
        return Model.getInstance().dailySurveysTable().insert(new DailySurveys(getId(), sbp, dbp, date));
    }

    @Override
    public GeneralType copyWithDiffId(int id) {
        return new Patient(id, medicIdRefProperty().get(), emailProperty().get(), name.get(), surname.get(), password.get(), risk_factors.stringProperty().get(), risk_factors.stringProperty().get(), comorbidity.stringProperty().get());
    }

    @Override
    public int getId() {
        return patient_id.get();
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
