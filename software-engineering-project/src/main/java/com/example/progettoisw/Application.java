package com.example.progettoisw;

import com.example.progettoisw.model.Model;
import com.example.progettoisw.model.table.*;
import com.example.progettoisw.model.type.TakingDrug;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Iterator;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        // Remove pixel effect on text
        System.setProperty("prism.lcdtext", "false");

        // Setup Model
        setupModel();

        // Setup SceneLoader
        SceneLoader.buildInstance(stage).goToMainMenu();

        stage.setTitle("Application");
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.show();
    }

    private void setupModel() {
        Model model = Model.getInstance();

        HashMap<TablesType, SQLTable> tables = new HashMap<>();
        tables.put(TablesType.Patient, new PatientTable());
        tables.put(TablesType.Medic, new MedicTable());
        tables.put(TablesType.Therapy, new TherapyTable());
        tables.put(TablesType.Taking_Drug, new TakingDrugTable());
        tables.put(TablesType.Daily_Surveys, new DailySurveysTable());
        tables.put(TablesType.Alert, new AlertTable());
        tables.put(TablesType.Symptoms, new SymptomsTable());

        model.prepareModel(tables);
    }
}