package com.example.progettoisw.model.table;

import com.example.progettoisw.model.type.Medic;
import com.example.progettoisw.model.type.Patient;
import com.example.progettoisw.model.type.Therapy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TherapyTable extends SQLTable<Therapy> {

    public TherapyTable() {
        super(TablesType.Therapy);
    }

    @Override
    protected Therapy getFromResultSet(ResultSet rs) throws SQLException {
        return Therapy.getFromResultSet(rs);
    }

    public List<Therapy> getTherapyOfPatients(Patient patient) {
        return observableList.stream().filter(therapy -> therapy.getPatientId() == patient.getId()).toList();
    }

    public List<Therapy> getTherapyOfMedic(Medic medic) {
        return observableList.stream().filter(therapy -> therapy.getPatientId() == medic.getId()).toList();
    }

    public Therapy getTherapyOfPatientByName(Patient patient, String name) {
        for(Therapy therapy : getTherapyOfPatients(patient))
            if(therapy.getDrug().equals(name))
                return therapy;

        return null;
    }
}
