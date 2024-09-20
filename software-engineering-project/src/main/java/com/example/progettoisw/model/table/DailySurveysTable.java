package com.example.progettoisw.model.table;

import com.example.progettoisw.model.type.DailySurveys;
import com.example.progettoisw.model.type.Patient;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DailySurveysTable extends SQLTable<DailySurveys> {
    
    public DailySurveysTable() {
        super(TablesType.Daily_Surveys);
    }

    @Override
    protected DailySurveys getFromResultSet(ResultSet rs) throws SQLException {
        return DailySurveys.getFromResultSet(rs);
    }

    public List<DailySurveys> getDailySurveysFromPatient(Patient patient) {
        return observableList.stream().filter(dailySurveys -> dailySurveys.getPatientId() == patient.getId()).toList();
    }

    public List<DailySurveys> getDailySurveysFromPatientAndPeriod(Patient patient, Date from, Date to) {
        return observableList.stream().filter(dailySurveys ->
                dailySurveys.getPatientId() == patient.getId() && dailySurveys.getDate().compareTo(from) >= 0 && dailySurveys.getDate().compareTo(to) <= 0).toList();
    }
}
