package com.example.progettoisw.model.table;

import com.example.progettoisw.model.Model;
import com.example.progettoisw.model.type.GeneralType;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class SQLTable<T extends GeneralType> {

    protected final Model model = Model.getInstance();
    protected final TablesType type;
    protected final String tableName;
    protected final String tableConstructor;
    protected ObservableList<T> observableList;

    public SQLTable(TablesType type) {
        this.type = type;
        this.tableName = type.toString().toLowerCase();
        this.tableConstructor = TablesConstructor.getTableConstructor(type);

        this.observableList = FXCollections.observableArrayList(GeneralType::getProperties);
    }

    /*
        GETTER
     */
    public TablesType getType() {
        return type;
    }

    public String getTableName() {
        return tableName;
    }

    public T getFromId(int id) {
        return observableList.stream().filter(obj -> obj.getId() == id).findAny().orElse(null);
    }

    public List<T> toList() {
        return observableList.stream().toList();
    }

    public ObservableList<T> getObservableList() {
        return observableList;
    }

    public int getIdByIndex(int index) {
        return observableList.get(index).getId();
    }

    /*
        TABLE ACTIONS
     */
    public boolean exist() throws SQLException {
        return this.model.hasTable(this.tableName);
    }

    public void createTable() throws SQLException {
        this.model.runStatement(String.format("CREATE TABLE %1$s %2$s", tableName, tableConstructor));
    }

    public void dropTable() throws SQLException {
        this.model.runStatement(String.format("DROP TABLE IF EXISTS %1$s;", tableName));
    }

    public void resetTable() throws SQLException {
        dropTable();
        createTable();
    }

    public void load() throws SQLException {
        ResultSet rs = this.model.query(String.format("SELECT * FROM %1$s", tableName));

        // Populate ObservableList
        while (rs.next())
            observableList.add(getFromResultSet(rs));

        // Add Listener
        observableList.addListener((ListChangeListener<T>) change -> {
            while (change.next()) {
                if (change.wasAdded())
                    observableEventAdd((List<T>) change.getAddedSubList());

                if (change.wasRemoved())
                    observableEventRemove((List<T>) change.getRemoved());

                if (change.wasUpdated())
                    observableEventUpdated(observableList.get(change.getFrom()));
            }
        });
    }

    /*
        ENTRY ACTIONS
     */
    private int addEntry(String constructor, String values) throws SQLException {
        return this.model.insert(tableName, constructor, values);
    }

    public T insert(T object) {
        if (object == null)
            throw new RuntimeException("Error can't insert null object");

        try {
            int entryId = addEntry(object.getTupleName(), object.getTupleValues());
            T createdEntry = (T) object.copyWithDiffId(entryId);
            observableList.add(createdEntry);

            return createdEntry;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(T object) {
        observableList.remove(object);
    }

    /*
        OBSERVABLE EVENT
     */
    private void observableEventAdd(List<T> addedList) {
        /* Not Managed */
        System.out.printf("Add event in %1$s table%n", tableName);
    }

    private void observableEventRemove(List<T> removedList) {
        System.out.printf("Remove event in %1$s table%n", tableName);

        for (T object : removedList) {
            try {
                this.model.runStatement(String.format("DELETE FROM %1$s WHERE %2$s = %3$d", tableName, object.getStringId(), object.getId()));
            } catch (SQLException e) {
                throw new RuntimeException("Failed to delete from database", e);
            }
        }
    }

    private void observableEventUpdated(T target) {
        System.out.printf("Update event in %1$s table%n", tableName);

        try {
            this.model.update(tableName, target.getAssociationNameValues(), String.format("%1$s=%2$d", target.getStringId(), target.getId()));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update the database", e);
        }
    }

    /*
        ABSTRACT FUNCTION
     */
    protected abstract T getFromResultSet(ResultSet rs) throws SQLException;
}
