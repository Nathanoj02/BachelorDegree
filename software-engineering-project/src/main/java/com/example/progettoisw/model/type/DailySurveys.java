package com.example.progettoisw.model.type;

import com.example.progettoisw.model.Model;
import com.example.progettoisw.utils.properties.StringListProperty;
import javafx.beans.property.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class DailySurveys extends GeneralType {

    private static final String STRING_ID = "ds_id";
    private final ReadOnlyIntegerWrapper ds_id = new ReadOnlyIntegerWrapper(this, STRING_ID);
    private final ReadOnlyIntegerWrapper patient_id = new ReadOnlyIntegerWrapper(this, "patient_id");
    private final IntegerProperty sbp = new SimpleIntegerProperty(this, "sbp");
    private final IntegerProperty dbp = new SimpleIntegerProperty(this, "dbp");
    private final StringListProperty<Integer> tk_ids = new StringListProperty<>(Integer.class, this, "tk_ids");
    private final StringListProperty<Integer> symptom_ids = new StringListProperty<>(Integer.class, this, "symptom_ids");
    private final ObjectProperty<Date> date = new SimpleObjectProperty<>(this, "date");

    private final Property[] properties = new Property[]{
            patient_id,
            sbp,
            dbp,
            tk_ids,
            symptom_ids,
            date
    };
    private final HashMap<Property, String> propertyType = new HashMap<>(Map.of(
            patient_id, Integer.class.getName(),
            sbp, Integer.class.getName(),
            dbp, Integer.class.getName(),
            tk_ids, String.class.getName(),
            symptom_ids, String.class.getName(),
            date, Date.class.getName()
    ));

    public DailySurveys(int patient_id, int sbp, int dbp, Date date) {
        this(-1, patient_id, sbp, dbp, "[]", "[]", date);
    }

    public DailySurveys(int ds_id, int patient_id, int sbp, int dbp, String tk_ids, String symptoms_ids, Date date) {
        this.ds_id.set(ds_id);
        this.patient_id.set(patient_id);
        this.sbp.set(sbp);
        this.dbp.set(dbp);
        this.tk_ids.set(tk_ids);
        this.symptom_ids.set(symptoms_ids);
        this.date.set(date);
    }

    public static DailySurveys getFromResultSet(ResultSet rs) throws SQLException {
         return new DailySurveys(
                rs.getInt("ds_id"),
                rs.getInt("patient_id"),
                rs.getInt("sbp"),
                rs.getInt("dbp"),
                rs.getString("tk_ids"),
                rs.getString("symptom_ids"),
                rs.getDate("date")
        );
    }

    public ReadOnlyIntegerWrapper dailySurveysIdProperty() {
        return ds_id;
    }

    public int getPatientId() {
        return patient_id.get();
    }

    public ReadOnlyIntegerWrapper patientIdProperty() {
        return patient_id;
    }

    public int getSBP() {
        return sbp.get();
    }

    public void setSBP(int sbp) {
        this.sbp.set(sbp);
    }

    public IntegerProperty sbpProperty() {
        return sbp;
    }

    public int getDBP() {
        return dbp.get();
    }

    public void setDBP(int dbp) {
        this.dbp.set(dbp);
    }

    public IntegerProperty dbpProperty() {
        return dbp;
    }

    public TakingDrug putTakingDrug(String drug, Date date, double qty) {
        TakingDrug tk = Model.getInstance().takingDrugTable().insert(new TakingDrug(getId(), getPatientId(), drug, date, qty));
        listTkIdsProperty().add(tk.getId());

        return tk;
    }

    public StringListProperty<Integer> listTkIdsProperty() {
        return tk_ids;
    }

    public Symptom putSymptom(Date from, Date to, String description) {
        Symptom symptom = Model.getInstance().symptomsTable().insert(new Symptom(getPatientId(), getId(), from, to, description));
        listSymptomIdsProperty().add(symptom.getId());

        return symptom;
    }

    public StringListProperty<Integer> listSymptomIdsProperty() {
        return symptom_ids;
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public Date getDate() {
        return date.get();
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    @Override
    public GeneralType copyWithDiffId(int id) {
        return new DailySurveys(id, patient_id.get(), sbp.get(), dbp.get(), tk_ids.stringProperty().get(), symptom_ids.stringProperty().get(), date.get());
    }

    @Override
    public int getId() {
        return ds_id.get();
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
