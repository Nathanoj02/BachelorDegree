package com.example.progettoisw.view.operator;

import com.example.progettoisw.Application;
import com.example.progettoisw.SceneLoader;
import com.example.progettoisw.model.Model;
import com.example.progettoisw.model.type.Medic;
import com.example.progettoisw.model.type.Patient;
import com.example.progettoisw.utils.ObjectUtils;
import javafx.animation.PauseTransition;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class OperatorController {
    private final int WAIT_SECONDS = 2;

    private final Scene operatorScene;

    private TextField medicName, medicSurname, medicPassword, medicEmail;
    private Label medicLog;

    private TextField patientName, patientSurname, patientPassword, patientEmail;
    private ChoiceBox<String> patientMedic;
    private Label patientLog;

    private final PauseTransition medicDelay = new PauseTransition(Duration.seconds(WAIT_SECONDS));
    private final PauseTransition patientDelay = new PauseTransition(Duration.seconds(WAIT_SECONDS));
    private final Pattern emailCheck = Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", Pattern.CASE_INSENSITIVE);

    Model model;

    public OperatorController() throws IOException {
        this.operatorScene = new FXMLLoader(Application.class.getResource("view/operator/operator.fxml")).load();

        this.model = Model.getInstance();

        // Lookup Scenes
        lookupMasterScene();
    }

    private void lookupMasterScene() {
        ((Button) this.operatorScene.lookup("#logoutBtn")).setOnAction(actionEvent -> SceneLoader.getInstance().goToMainMenu());

        // TODO vedere se si può compattare
        medicDelay.setOnFinished(actionEvent -> medicLog.setText(""));
        patientDelay.setOnFinished(actionEvent -> patientLog.setText(""));

        // Medic - side
        medicName = (TextField) operatorScene.lookup("#medicName");
        medicSurname = (TextField) operatorScene.lookup("#medicSurname");
        medicPassword = (TextField) operatorScene.lookup("#medicPassw");
        medicEmail = (TextField) operatorScene.lookup("#medicEmail");

        medicLog = (Label) operatorScene.lookup("#medicLog");

        // Patient - side
        patientName = (TextField) operatorScene.lookup("#patientName");
        patientSurname = (TextField) operatorScene.lookup("#patientSurname");
        patientPassword = (TextField) operatorScene.lookup("#patientPassw");
        patientEmail = (TextField) operatorScene.lookup("#patientEmail");
        patientMedic = (ChoiceBox<String>) operatorScene.lookup("#patientMedicDropdown");

        patientMedic.getItems().addAll(model.medicTable().toList().stream().
                map(medic -> medic.getName() + " - " + medic.getEmail()).toList());
        model.medicTable().getObservableList().addListener((ListChangeListener<? super Medic>) change -> {
            patientMedic.getItems().clear();
            patientMedic.getItems().addAll(model.medicTable().toList().stream().
                    map(medic -> medic.getName() + " - " + medic.getEmail()).toList());
        });

        patientLog = (Label) operatorScene.lookup("#patientLog");

        ((Button) operatorScene.lookup("#btnMedicDone")).setOnAction(actionEvent -> {
            // Controllo se tutti i campi sono stati scritti
            if(!ObjectUtils.allObjectsFull(medicName, medicSurname, medicPassword, medicEmail)) {
                medicLog.setTextFill(Color.RED);
                medicLog.setText("Tutti i campi devono essere compilati");
            }
            else if (!emailCheck.matcher(medicEmail.getText()).find()) {
                medicLog.setTextFill(Color.RED);
                medicLog.setText("Inserisci una email valida");
            }
            else if(model.medicTable().medicExists(medicEmail.getText())) {
                medicLog.setTextFill(Color.RED);
                medicLog.setText("L'account per questa email esiste già");
            }
            else {
                model.medicTable().insert(new Medic(medicName.getText(), medicSurname.getText(),
                        medicEmail.getText(), medicPassword.getText()));

                // Cancellare i Text Field dopo che preme il pulsante
                clearMedicFields();

                // Scrive un log
                medicLog.setTextFill(Color.BLACK);
                medicLog.setText("Account Medico aggiunto con successo");
            }

            medicDelay.stop();  // se ci sono già dei contatori attivi
            medicDelay.play();
        });

        ((Button) operatorScene.lookup("#btnPatientDone")).setOnAction(actionEvent -> {
            // Controllo se tutti i campi sono stati scritti
            if(!ObjectUtils.allObjectsFull(patientName, patientSurname, patientPassword, patientEmail, patientMedic)) {
                patientLog.setTextFill(Color.RED);
                patientLog.setText("Tutti i campi devono essere compilati");
            }
            else if (!emailCheck.matcher(patientEmail.getText()).find()) {
                patientLog.setTextFill(Color.RED);
                patientLog.setText("Inserisci una email valida");
            }
            else if(model.patientTable().patientExists(patientEmail.getText())) {
                patientLog.setTextFill(Color.RED);
                patientLog.setText("L'account per questa email esiste già");
            }
            else {
                model.patientTable().insert(new Patient (
                        model.medicTable().getIdByIndex(patientMedic.getItems().indexOf(patientMedic.getValue())),
                        patientEmail.getText(), patientName.getText(), patientSurname.getText(),
                        patientPassword.getText(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

                clearPatientFields();

                // Scrive un log
                patientLog.setTextFill(Color.BLACK);
                patientLog.setText("Account Paziente aggiunto con successo");
            }

            patientDelay.stop();  // se ci sono già dei contatori attivi
            patientDelay.play();
        });

    }

    private void clearMedicFields() {
        medicName.setText("");
        medicSurname.setText("");
        medicPassword.setText("");
        medicEmail.setText("");
    }

    private void clearPatientFields() {
        patientName.setText("");
        patientSurname.setText("");
        patientPassword.setText("");
        patientEmail.setText("");
        patientMedic.setValue(null);
    }

    public void show() {
        SceneLoader.getInstance().loadScene(this.operatorScene);
    }
}
