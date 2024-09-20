package com.example.progettoisw.model.table;

import com.example.progettoisw.model.type.Alert;
import com.example.progettoisw.model.type.Medic;
import com.example.progettoisw.model.type.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class AlertTable extends SQLTable<Alert> {

    public AlertTable() {
        super(TablesType.Alert);
    }

    @Override
    protected Alert getFromResultSet(ResultSet rs) throws SQLException {
        return Alert.getFromResultSet(rs);
    }

    public List<Alert> getMedicAlertNotRead(Medic medic) {
        return observableList.stream()
                .filter(alert -> alert.getMedicId() == medic.getId())
                .filter(alert -> !alert.isMedicRead())
                .sorted(new Comparator<Alert>() {
                    @Override
                    public int compare(Alert o1, Alert o2) {
                        return o1.getPatientId() - o2.getPatientId();
                    }
                })
                .toList();
    }

    public List<Alert> getPatientAlertNotRead(Patient patient) {
        return getAllPatientAlert(patient).stream()
                .filter(alert -> !alert.isPatientRead()).toList();
    }

    public List<Alert> getAllPatientAlert(Patient patient) {
        return observableList.stream()
                .filter(alert -> alert.getPatientId() == patient.getId()).toList();
    }
}
