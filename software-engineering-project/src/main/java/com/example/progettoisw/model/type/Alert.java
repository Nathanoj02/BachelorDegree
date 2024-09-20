package com.example.progettoisw.model.type;

import com.example.progettoisw.model.Model;
import com.example.progettoisw.utils.DateUtils;
import javafx.beans.property.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Alert extends GeneralType {

    private static final String STRING_ID = "alert_id";
    private final ReadOnlyIntegerWrapper alert_id = new ReadOnlyIntegerWrapper(this, STRING_ID);
    private final ReadOnlyIntegerWrapper medic_id = new ReadOnlyIntegerWrapper(this, "medic_id");
    private final ReadOnlyIntegerWrapper patient_id = new ReadOnlyIntegerWrapper(this, "patient_id");
    private final StringProperty description = new SimpleStringProperty(this, "description");
    private final BooleanProperty medic_read = new SimpleBooleanProperty(this, "medic_read");
    private final BooleanProperty patient_read = new SimpleBooleanProperty(this, "patient_read");
    private final ObjectProperty<Type> alert_type = new SimpleObjectProperty<>(this, "alert_type");
    private final ObjectProperty<java.sql.Date> date = new SimpleObjectProperty<>(this, "date");

    private final Property[] properties = new Property[]{
            medic_id,
            patient_id,
            description,
            medic_read,
            patient_read,
            alert_type,
            date
    };
    private final HashMap<Property, String> propertyType = new HashMap<>(Map.of(
            medic_id, Integer.class.getName(),
            patient_id, Integer.class.getName(),
            description, String.class.getName(),
            medic_read, Boolean.class.getName(),
            patient_read, Boolean.class.getName(),
            alert_type, Type.class.getName(),
            date, java.sql.Date.class.getName()
    ));

    public Alert(int medic_id, int patient_id, String description, boolean medic_read, boolean patient_read,
                 Type alert_type, java.sql.Date date) {
        this.alert_id.set(-1);
        this.medic_id.set(medic_id);
        this.patient_id.set(patient_id);
        this.description.set(description);
        this.medic_read.set(medic_read);
        this.patient_read.set(patient_read);
        this.alert_type.set(alert_type);
        this.date.set(date);
    }

    public Alert(int alert_id, int medic_id, int patient_id, String description, boolean medic_read, boolean patient_read,
                 Type alert_type, java.sql.Date date) {
        this.alert_id.set(alert_id);
        this.medic_id.set(medic_id);
        this.patient_id.set(patient_id);
        this.description.set(description);
        this.medic_read.set(medic_read);
        this.patient_read.set(patient_read);
        this.alert_type.set(alert_type);
        this.date.set(date);
    }

    public static Alert getFromResultSet(ResultSet rs) throws SQLException {
        return new Alert(
                rs.getInt("alert_id"),
                rs.getInt("medic_id"),
                rs.getInt("patient_id"),
                rs.getString("description"),
                rs.getBoolean("medic_read"),
                rs.getBoolean("patient_read"),
                Type.values()[rs.getInt("alert_type")],
                rs.getDate("date")
        );
    }

    public ReadOnlyIntegerWrapper alertIdProperty() {
        return alert_id;
    }

    public int getMedicId() {
        return medic_id.get();
    }

    public ReadOnlyIntegerWrapper medicIdProperty() {
        return medic_id;
    }

    public int getPatientId() {
        return patient_id.get();
    }

    public ReadOnlyIntegerWrapper patientIdProperty() {
        return patient_id;
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

    public boolean isMedicRead() {
        return medic_read.get();
    }

    public void setMedicRead(boolean medic_read) {
        this.medic_read.set(medic_read);
    }

    public BooleanProperty medicReadProperty() {
        return medic_read;
    }

    public boolean isPatientRead() {
        return patient_read.get();
    }

    public void setPatientRead(boolean patient_read) {
        this.patient_read.set(patient_read);
    }

    public BooleanProperty patientReadProperty() {
        return patient_read;
    }

    public Type getAlertType() {
        return alert_type.get();
    }

    public ObjectProperty<Type> alertTypeProperty() {
        return alert_type;
    }

    public void setAlertType(Type alert_type) {
        this.alert_type.set(alert_type);
    }

    public Date getDate() {
        return date.get();
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    @Override
    public GeneralType copyWithDiffId(int id) {
        return new Alert(id, medic_id.get(), patient_id.get(), description.get(), medic_read.get(), patient_read.get(),
                alert_type.get(), date.get());
    }

    @Override
    public int getId() {
        return alert_id.get();
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

    public String toStringPatient() {
        return "[" + DateUtils.getToString(getDate()) + "] " +
                (getAlertType() == Alert.Type.Inactivity ? "Non hai " : "Hai ") + getDescription();
    }
    public String toStringMedic() {
        return "[" + DateUtils.getToString(getDate()) + "] " + "Il paziente " +
                Model.getInstance().patientTable().getFromId(getPatientId()).getFullName() +
                (getAlertType() == Alert.Type.Inactivity ? " non ha " : " ha ") + getDescription();
    }

    public enum Type {
        Inactivity,
        WrongDrug,
        TooMuchDrug,
        TooLittleDrug,
        BloodPressNormHigh,
        BloodPressBorderline,
        BloodPressMild,
        BloodPressModerate,
        BloodPressSevere,
        BloodPressSisBolderline,
        BloodPressSis
    }
}
