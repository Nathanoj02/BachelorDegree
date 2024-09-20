package com.example.progettoisw.model.type;

import javafx.beans.property.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Symptom extends GeneralType {

    private static final String STRING_ID = "symptom_id";
    private final ReadOnlyIntegerWrapper symptom_id = new ReadOnlyIntegerWrapper(this, STRING_ID);
    private final ReadOnlyIntegerWrapper patient_id = new ReadOnlyIntegerWrapper(this, "patient_id");
    private final ReadOnlyIntegerWrapper ds_id = new ReadOnlyIntegerWrapper(this, "ds_id");
    private final ObjectProperty<Date> from = new SimpleObjectProperty<>(this, "from_date");
    private final ObjectProperty<Date> to = new SimpleObjectProperty<>(this, "to_date");
    private final StringProperty description = new SimpleStringProperty(this, "description");
    private final Property[] properties = new Property[]{
            patient_id,
            ds_id,
            from,
            to,
            description
    };
    private final HashMap<Property, String> propertyType = new HashMap<>(Map.of(
            patient_id, Integer.class.getName(),
            ds_id, Integer.class.getName(),
            from, Date.class.getName(),
            to, Date.class.getName(),
            description, String.class.getName()
    ));

    public Symptom(int patient_id, int ds_id, Date from, Date to, String description) {
        this(-1, patient_id, ds_id, from, to, description);
    }
    public Symptom(int symptom_id, int patient_id, int ds_id, Date from, Date to, String description) {
        this.symptom_id.set(symptom_id);
        this.patient_id.set(patient_id);
        this.ds_id.set(ds_id);
        this.from.set(from);
        this.to.set(to);
        this.description.set(description);
    }

    public static Symptom getFromResultSet(ResultSet rs) throws SQLException {
        return new Symptom(
                rs.getInt("symptom_id"),
                rs.getInt("patient_id"),
                rs.getInt("ds_id"),
                rs.getDate("from_date"),
                rs.getDate("to_date"),
                rs.getString("description")
        );
    }

    public ReadOnlyIntegerWrapper symptomIdProperty() {
        return symptom_id;
    }

    public int getPatientId() {
        return patient_id.get();
    }

    public ReadOnlyIntegerWrapper patientIdProperty() {
        return patient_id;
    }

    public void setPatientId(int patient_id) {
        this.patient_id.set(patient_id);
    }

    public int getDailySurveyId() {
        return ds_id.get();
    }

    public ReadOnlyIntegerWrapper dailySurveyIdProperty() {
        return ds_id;
    }

    public void setDailySurveyId(int ds_id) {
        this.ds_id.set(ds_id);
    }

    public Date getFrom() {
        return from.get();
    }

    public ObjectProperty<Date> fromProperty() {
        return from;
    }

    public void setFrom(Date from) {
        this.from.set(from);
    }

    public Date getTo() {
        return to.get();
    }

    public ObjectProperty<Date> toProperty() {
        return to;
    }

    public void setTo(Date to) {
        this.to.set(to);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public GeneralType copyWithDiffId(int id) {
        return new Symptom(id, patient_id.get(), ds_id.get(), from.get(), to.get(), description.get());
    }

    @Override
    public int getId() {
        return symptom_id.get();
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
