package com.example.progettoisw.model.table;

import com.example.progettoisw.model.type.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PatientTable extends SQLTable<Patient> {

    public PatientTable() {
        super(TablesType.Patient);
    }

    @Override
    protected Patient getFromResultSet(ResultSet rs) throws SQLException {
        return Patient.getFromResultSet(rs);
    }

    public Patient findAuthorized(String email, String password) {
        return observableList.stream().filter(patient -> patient.getEmail().equals(email) && patient.getPassword().equals(password)).findAny().orElse(null);
    }

    public List<Patient> getPatientOfMedic(int medic_id) {
        return observableList.stream().filter(patient -> patient.getMedicId() == medic_id).toList();
    }

    public boolean patientExists(String email) {
        return observableList.stream().anyMatch(patient -> patient.getEmail().equals(email));
    }
}
