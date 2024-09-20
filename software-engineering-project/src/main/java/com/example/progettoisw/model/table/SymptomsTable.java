package com.example.progettoisw.model.table;

import com.example.progettoisw.model.type.Patient;
import com.example.progettoisw.model.type.Symptom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SymptomsTable extends SQLTable<Symptom> {

    public SymptomsTable() {
        super(TablesType.Symptoms);
    }

    @Override
    protected Symptom getFromResultSet(ResultSet rs) throws SQLException {
        return Symptom.getFromResultSet(rs);
    }

    public List<Symptom> getSymptomsOfPatient(Patient patient) {
        return observableList.stream().filter(symptom -> symptom.getPatientId() == patient.getId()).toList();
    }
}
