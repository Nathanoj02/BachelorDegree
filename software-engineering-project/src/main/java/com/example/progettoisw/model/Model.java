package com.example.progettoisw.model;

import com.example.progettoisw.model.table.*;

import java.sql.*;
import java.util.HashMap;

public class Model {
    private static final String URL = "jdbc:sqlite:table.sqlite";
    private static Model singleInstance = null;
    private HashMap<TablesType, SQLTable> tables;
    private Connection connection;

    public Model() {
        connect();
    }

    public static synchronized Model getInstance() {
        return singleInstance == null ?
                singleInstance = new Model() : singleInstance;
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(URL);
            System.out.println("Connected with the database");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect with the database", e);
        }
    }

    public void prepareModel(HashMap<TablesType, SQLTable> tables) {
        this.tables = tables;

        for (TablesType t : TablesType.values()) {
            SQLTable table = tables.get(t);

            if (table == null)
                throw new RuntimeException(String.format("Missing table [%1$s] in the Model tables", t));

            try {
                if (!table.exist())
                    table.createTable();

                table.load();
            } catch (SQLException e) {
                throw new RuntimeException(String.format("Error during create table (%1$s)", table.getTableName()), e);
            }
        }
    }

    public ResultSet query(String q) throws SQLException {
        return connection.createStatement().executeQuery(q);
    }

    public int runStatement(String str) throws SQLException {
        return connection.createStatement().executeUpdate(str);
    }

    public int insert(String table, String constructor, String values) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(String.format("INSERT INTO %1$s %2$s VALUES %3$s", table, constructor, values));
        ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()");

        return rs.getInt("last_insert_rowid()");
    }

    public void update(String table, String newValues, String target) throws SQLException {
        runStatement(String.format("UPDATE %1$s SET %2$s WHERE %3$s", table, newValues, target));
    }

    public boolean hasTable(String tableName) throws SQLException {
        return query(String.format("SELECT * FROM sqlite_master WHERE tbl_name = '%1$s'", tableName)).next();
    }

    public PatientTable patientTable() {
        return (PatientTable) tables.get(TablesType.Patient);
    }

    public MedicTable medicTable() {
        return (MedicTable) tables.get(TablesType.Medic);
    }

    public TherapyTable therapyTable() {
        return (TherapyTable) tables.get(TablesType.Therapy);
    }

    public DailySurveysTable dailySurveysTable() {
        return (DailySurveysTable) tables.get(TablesType.Daily_Surveys);
    }

    public TakingDrugTable takingDrugTable() {
        return (TakingDrugTable) tables.get(TablesType.Taking_Drug);
    }

    public AlertTable alertTable() {
        return (AlertTable) tables.get(TablesType.Alert);
    }

    public SymptomsTable symptomsTable() {
        return (SymptomsTable) tables.get(TablesType.Symptoms);
    }
}
