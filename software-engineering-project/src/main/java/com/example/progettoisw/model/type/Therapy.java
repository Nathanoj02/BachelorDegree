package com.example.progettoisw.model.type;

import javafx.beans.property.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Therapy extends GeneralType {

    private static final String STRING_ID = "therapy_id";
    private final ReadOnlyIntegerWrapper therapy_id = new ReadOnlyIntegerWrapper(this, STRING_ID);
    private final ReadOnlyIntegerWrapper patient_id_ref = new ReadOnlyIntegerWrapper(this, "patient_id");
    private final ReadOnlyIntegerWrapper medic_id_ref = new ReadOnlyIntegerWrapper(this, "medic_id");
    private final StringProperty drug = new SimpleStringProperty(this, "drug");
    private final IntegerProperty daily_intake = new SimpleIntegerProperty(this, "daily_intake");
    private final DoubleProperty qty_intake_drug = new SimpleDoubleProperty(this, "qty_intake_drug");
    private final StringProperty indications = new SimpleStringProperty(this, "indications");
    private final Property[] properties = new Property[]{
            patient_id_ref,
            medic_id_ref,
            drug,
            daily_intake,
            qty_intake_drug,
            indications,
    };
    private final HashMap<Property, String> propertyType = new HashMap<>(Map.of(
            patient_id_ref, Integer.class.getName(),
            medic_id_ref, Integer.class.getName(),
            drug, String.class.getName(),
            daily_intake, Integer.class.getName(),
            qty_intake_drug, Double.class.getName(),
            indications, String.class.getName()
    ));

    public Therapy(int patient_id_ref, int medic_id_ref, String drug, int daily_intake, double qty_intake_drug, String indications) {
        this.therapy_id.set(-1);
        this.patient_id_ref.set(patient_id_ref);
        this.medic_id_ref.set(medic_id_ref);
        this.drug.set(drug);
        this.daily_intake.set(daily_intake);
        this.qty_intake_drug.set(qty_intake_drug);
        this.indications.set(indications);
    }

    public Therapy(int therapy_id, int patient_id_ref, int medic_id_ref, String drug, int daily_intake, double qty_intake_drug, String indications) {
        this.therapy_id.set(therapy_id);
        this.patient_id_ref.set(patient_id_ref);
        this.medic_id_ref.set(medic_id_ref);
        this.drug.set(drug);
        this.daily_intake.set(daily_intake);
        this.qty_intake_drug.set(qty_intake_drug);
        this.indications.set(indications);
    }

    public static Therapy getFromResultSet(ResultSet rs) throws SQLException {
        return new Therapy(
                rs.getInt("therapy_id"),
                rs.getInt("patient_id"),
                rs.getInt("medic_id"),
                rs.getString("drug"),
                rs.getInt("daily_intake"),
                rs.getDouble("qty_intake_drug"),
                rs.getString("indications")
        );
    }

    public ReadOnlyIntegerWrapper therapyIdProperty() {
        return therapy_id;
    }

    public int getPatientId() {
        return patient_id_ref.get();
    }

    public ReadOnlyIntegerWrapper patientIdRefProperty() {
        return patient_id_ref;
    }

    public int getMedicId() {
        return medic_id_ref.get();
    }

    public ReadOnlyIntegerWrapper medicIdRefProperty() {
        return medic_id_ref;
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

    public int getDailyIntake() {
        return daily_intake.get();
    }

    public void setDailyIntake(int daily_intake) {
        this.daily_intake.set(daily_intake);
    }

    public IntegerProperty dailyIntakeProperty() {
        return daily_intake;
    }

    public double getQtyIntakeDrug() {
        return qty_intake_drug.get();
    }

    public void setQtyIntakeDrug(double qty_intake_drug) {
        this.qty_intake_drug.set(qty_intake_drug);
    }

    public DoubleProperty qtyIntakeDrugProperty() {
        return qty_intake_drug;
    }

    public String getIndications() {
        return indications.get();
    }

    public void setIndications(String indications) {
        this.indications.set(indications);
    }

    public StringProperty indicationsProperty() {
        return indications;
    }

    @Override
    public GeneralType copyWithDiffId(int id) {
        return new Therapy(id, patient_id_ref.get(), medic_id_ref.get(), drug.get(), daily_intake.get(), qty_intake_drug.get(), indications.get());
    }

    @Override
    public int getId() {
        return therapy_id.get();
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
