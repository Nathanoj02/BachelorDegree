package com.example.progettoisw.model.type;

import javafx.beans.property.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TakingDrug extends GeneralType {

    private static final String STRING_ID = "tk_id";
    private final ReadOnlyIntegerWrapper tk_id = new ReadOnlyIntegerWrapper(this, STRING_ID);
    private final ReadOnlyIntegerWrapper ds_id = new ReadOnlyIntegerWrapper(this, "ds_id");
    private final ReadOnlyIntegerWrapper patient_id = new ReadOnlyIntegerWrapper(this, "patient_id");
    private final StringProperty drug = new SimpleStringProperty(this, "drug");
    private final ObjectProperty<Date> date = new SimpleObjectProperty<>(this, "date");
    private final DoubleProperty qty = new SimpleDoubleProperty(this, "qty");
    private final Property[] properties = new Property[]{
            ds_id,
            patient_id,
            drug,
            date,
            qty
    };
    private final HashMap<Property, String> propertyType = new HashMap<>(Map.of(
            ds_id, Integer.class.getName(),
            patient_id, Integer.class.getName(),
            drug, String.class.getName(),
            date, Date.class.getName(),
            qty, Double.class.getName()
    ));

    public TakingDrug(int ds_id, int patient_id, String drug, Date date, double qty) {
        this(-1, ds_id, patient_id, drug, date, qty);
    }

    public TakingDrug(int tk_id, int ds_id, int patient_id, String drug, Date date, double qty) {
        this.tk_id.set(tk_id);
        this.ds_id.set(ds_id);
        this.patient_id.set(patient_id);
        this.drug.set(drug);
        this.date.set(date);
        this.qty.set(round(qty, 4));
    }

    public static TakingDrug getFromResultSet(ResultSet rs) throws SQLException {
        return new TakingDrug(
                rs.getInt("tk_id"),
                rs.getInt("ds_id"),
                rs.getInt("patient_id"),
                rs.getString("drug"),
                rs.getDate("date"),
                rs.getDouble("qty")
        );
    }

    /* UTILS */
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public ReadOnlyIntegerWrapper takingDrugIdProperty() {
        return tk_id;
    }

    public int getDailySurveysId() {
        return ds_id.get();
    }

    public void setDailySurveysId(int ds_id) {
        this.ds_id.set(ds_id);
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

    public String getDrug() {
        return drug.get();
    }

    public void setDrug(String drug) {
        this.drug.set(drug);
    }

    public StringProperty drugProperty() {
        return drug;
    }

    public Date getDate() {
        return date.get();
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public double getQty() {
        return qty.get();
    }

    public void setQty(double qty) {
        this.qty.set(qty);
    }

    public DoubleProperty qtyProperty() {
        return qty;
    }

    @Override
    public GeneralType copyWithDiffId(int id) {
        return new TakingDrug(id, ds_id.get(), patient_id.get(), drug.get(), date.get(), qty.get());
    }

    @Override
    public int getId() {
        return tk_id.get();
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
