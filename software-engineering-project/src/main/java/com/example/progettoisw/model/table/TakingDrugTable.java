package com.example.progettoisw.model.table;

import com.example.progettoisw.model.type.Patient;
import com.example.progettoisw.model.type.TakingDrug;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TakingDrugTable extends SQLTable<TakingDrug> {
    
    public TakingDrugTable() {
        super(TablesType.Taking_Drug);
    }

    @Override
    protected TakingDrug getFromResultSet(ResultSet rs) throws SQLException {
        return TakingDrug.getFromResultSet(rs);
    }

    public List<TakingDrug> getTakingDrugFromPatient(Patient patient) {
        return observableList.stream().filter(tk -> tk.getPatientId() == patient.getId()).toList();
    }
}
