package com.example.progettoisw.model.table;

import com.example.progettoisw.model.type.Medic;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicTable extends SQLTable<Medic> {

    public MedicTable() {
        super(TablesType.Medic);
    }

    @Override
    protected Medic getFromResultSet(ResultSet rs) throws SQLException {
        return Medic.getFromResultSet(rs);
    }

    public Medic findAuthorized(String email, String password) {
        return observableList.stream().filter(medic -> medic.getEmail().equals(email) && medic.getPassword().equals(password)).findAny().orElse(null);
    }

    public boolean medicExists(String email) {
        return observableList.stream().anyMatch(medic -> medic.getEmail().equals(email));
    }
}
