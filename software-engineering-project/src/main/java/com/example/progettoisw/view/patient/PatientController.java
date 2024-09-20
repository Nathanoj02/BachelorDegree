package com.example.progettoisw.view.patient;

import com.example.progettoisw.Application;
import com.example.progettoisw.SceneLoader;
import com.example.progettoisw.model.Model;
import com.example.progettoisw.model.type.Alert;
import com.example.progettoisw.model.type.DailySurveys;
import com.example.progettoisw.model.type.Patient;
import com.example.progettoisw.model.type.TakingDrug;
import com.example.progettoisw.model.type.Therapy;
import com.example.progettoisw.utils.AlertUtils;
import com.example.progettoisw.utils.DateUtils;
import com.example.progettoisw.utils.ObjectUtils;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class PatientController {
    // Generali
    private final Patient target;
    private final Scene patientScene;
    private final VBox alertBox;

    private StackPane stackPane;

    private Label title;

    // Pressione
    private TextField sbpVal, dbpVal;
    private DatePicker bloodPressDate;
    private TextField bloodPressHour, bloodPressMin;

    // Terapie
    private List<ChoiceBox<String>> drugTaken = new ArrayList<>();
    private List<TextField> drugQuantity = new ArrayList<>();
    private List<DatePicker> drugDate = new ArrayList<>();
    private List<TextField> drugHour = new ArrayList<>(), drugMin = new ArrayList<>();

    private VBox drugBox = new VBox();

    // Sintomi
    private List<TextField> symDesc = new ArrayList<>();
    private List<DatePicker> symStartDate = new ArrayList<>(), symEndDate = new ArrayList<>();
    private List<TextField> symStartHour = new ArrayList<>(), symStartMin = new ArrayList<>(),
            symEndHour = new ArrayList<>(), symEndMin = new ArrayList<>();

    private VBox symBox = new VBox();

    // Altro
    private Label logText;
    private final int WAIT_SECONDS = 2;
    private final PauseTransition logDelay = new PauseTransition(Duration.seconds(WAIT_SECONDS));

    Model model;

    // Valori interi
    private int IsbpVal, IdbpVal, IbloodPressHour, IbloodPressMin;
    private List<Float> IdrugQuantity = new ArrayList<>();
    private List<Integer> IdrugHour = new ArrayList<>(), IdrugMin = new ArrayList<>();
    private List<Integer> IsymStartHour = new ArrayList<>(), IsymStartMin = new ArrayList<>(),
            IsymEndHour = new ArrayList<>(), IsymEndMin = new ArrayList<>();

    // Alert scene
    private List<Alert> alertList = new ArrayList<>();
    private ListView<String> alertListView = new ListView<>();


    public PatientController(Patient target) throws IOException {
        if (target == null)
            throw new RuntimeException("Patient must be not null");
        this.target = target;

        this.patientScene = new FXMLLoader(Application.class.getResource("view/patient/patient.fxml")).load();
        this.alertBox = new FXMLLoader(Application.class.getResource("view/alert.fxml")).load();

        model = Model.getInstance();

        resetValues();

        // Lookup Scenes
        lookupMasterScene();

        checkAlerts();
    }

    private void lookupMasterScene() {
        stackPane = (StackPane) patientScene.lookup("#stackPane");

        title = (Label) patientScene.lookup("#title");

        ((Label) patientScene.lookup("#patientName")).setText(target.getName() + " " + target.getSurname());

        sbpVal = (TextField) patientScene.lookup("#sbpVal");
        dbpVal = (TextField) patientScene.lookup("#dbpVal");
        bloodPressDate = (DatePicker) patientScene.lookup("#bloodPressDate");
        bloodPressHour = (TextField) patientScene.lookup("#bloodPressHour");
        bloodPressMin = (TextField) patientScene.lookup("#bloodPressMin");

        // Imposto solo valori numerici
        ObjectUtils.setOnlyIntegerWithMax(300, sbpVal, dbpVal);
        ObjectUtils.setOnlyIntegerWithMax(23, bloodPressHour);
        ObjectUtils.setOnlyIntegerWithMax(59, bloodPressMin);

        drugBox = (VBox) ((ScrollPane) patientScene.lookup("#drugScrollPane")).getContent();
        addDrugRow();

        symBox = (VBox) ((ScrollPane) patientScene.lookup("#symptomsScrollPane")).getContent();
        addSymptomsRow();

        logText = (Label) patientScene.lookup("#logText");
        logDelay.setOnFinished(actionEvent -> logText.setText(""));

        ((Button) patientScene.lookup("#logoutBtn")).setOnAction(actionEvent -> SceneLoader.getInstance().goToMainMenu());
        ((Button) patientScene.lookup("#doneBtn")).setOnAction(actionEvent -> savePatientData());

        ChangeListener<Boolean> bloodPressListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean old, Boolean newVal) {
                if(!newVal && !dbpVal.getText().equals("") && !sbpVal.getText().equals("")) {
                    if (!ObjectUtils.oneObjectFull(bloodPressDate, bloodPressHour, bloodPressMin))
                        DateUtils.setDate(bloodPressDate, bloodPressHour, bloodPressMin);
                }
            }
        };

        sbpVal.focusedProperty().addListener(bloodPressListener);
        dbpVal.focusedProperty().addListener(bloodPressListener);

        sbpVal.setOnAction(actionEvent -> sbpVal.getParent().requestFocus());
        dbpVal.setOnAction(actionEvent -> dbpVal.getParent().requestFocus());
    }

    private void checkAlerts() {
        AlertUtils.checkPatientAlerts(target);

        alertList = model.alertTable().getPatientAlertNotRead(target);
        if(alertList.isEmpty())
            return;

        title.setOpacity(0);
        stackPane.getChildren().add(alertBox);

        // alertBox Properties
        alertBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        alertBox.prefWidthProperty().bind(patientScene.widthProperty());
        StackPane.setMargin(alertBox, new Insets(50, 0, 0, 0));


        lookupAlertBox();
    }

    private void savePatientData() {
        // Controlla se i campi necessari sono stati compilati
        if(!checkData()) {
            logText.setTextFill(Color.RED);
            logText.setText("I dati non sono stati inseriti correttamente");
            logDelay.stop();
            logDelay.play();

            return;
        }

        // Salva i dati
        DailySurveys ds = target.createDailySurveys(IsbpVal, IdbpVal,
                DateUtils.getDateFromPicker(bloodPressDate, IbloodPressHour, IbloodPressMin));

        AlertUtils.checkPatientBloodPressure(target, IsbpVal, IdbpVal,
                DateUtils.getDateFromPicker(bloodPressDate, IbloodPressHour, IbloodPressMin));

        for(int i = 0; i < drugTaken.size(); i++) {
            if(drugTaken.get(i).getValue() != null) {
                TakingDrug tk = ds.putTakingDrug(drugTaken.get(i).getValue(),
                        DateUtils.getDateFromPicker(drugDate.get(i), IdrugHour.get(i), IdrugMin.get(i)),
                        IdrugQuantity.get(i));

                AlertUtils.checkIfPatientFollowsPrescription(target, tk,
                        DateUtils.getDateFromPicker(drugDate.get(i), IdrugHour.get(i), IdrugMin.get(i)));
            }
        }
        for(int i = 0; i < symDesc.size(); i++) {
            if(!symDesc.get(i).getText().equals(""))
                ds.putSymptom(DateUtils.getDateFromPicker(symStartDate.get(i), IsymStartHour.get(i), IsymStartMin.get(i)),
                        DateUtils.getDateFromPicker(symEndDate.get(i), IsymEndHour.get(i), IsymEndMin.get(i)),
                        symDesc.get(i).getText());
        }

        AlertUtils.patientActive(target);

        // Scrive un messaggio di Log
        logText.setTextFill(Color.BLACK);
        logText.setText("Dati inseriti con successo");

        // Cancella il messaggio dopo * secondi
        logDelay.stop();
        logDelay.play();

        // Cancella tutti i campi
        clearAll();

        // Se ha assunto dosi errate di farmaci mostra gli alert
        checkAlerts();
    }

    private boolean checkData() {
        // Pressione (se non c'è -> non posso inserire il resto)
        if(!ObjectUtils.oneObjectFull(sbpVal, dbpVal, bloodPressDate, bloodPressHour, bloodPressMin))
            return false;

        if(!ObjectUtils.allObjectsFull(sbpVal, dbpVal, bloodPressDate, bloodPressHour, bloodPressMin))
            return false;

        // Data di oggi
        Date now = new Date(System.currentTimeMillis());

        // Controllo sui valori
        try {
            IsbpVal = Integer.parseInt(sbpVal.getText());
            IdbpVal = Integer.parseInt(dbpVal.getText());
            IbloodPressHour = Integer.parseInt(bloodPressHour.getText());
            IbloodPressMin = Integer.parseInt(bloodPressMin.getText());
        }
        catch(NumberFormatException e) {
            return false;
        }

        if(DateUtils.getDateFromPicker(bloodPressDate, IbloodPressHour, IbloodPressMin).after(now))
            return false;

        if(IdbpVal <= 0 || IdbpVal > IsbpVal || !DateUtils.hoursLegal(IbloodPressHour) || !DateUtils.minsLegal(IbloodPressMin))
            return false;

        // Terapie
        for(int i = 0; i < drugTaken.size(); i++) {
            if(ObjectUtils.oneObjectFull(drugTaken.get(i), drugQuantity.get(i), drugDate.get(i), drugHour.get(i), drugMin.get(i))) {

                if(!ObjectUtils.allObjectsFull(drugTaken.get(i), drugQuantity.get(i), drugDate.get(i), drugHour.get(i), drugMin.get(i)))
                    return false;

                // Controllo sui valori
                try {
                    IdrugQuantity.set(i, Float.parseFloat(drugQuantity.get(i).getText()));
                    IdrugHour.set(i, Integer.parseInt(drugHour.get(i).getText()));
                    IdrugMin.set(i, Integer.parseInt(drugMin.get(i).getText()));
                }
                catch (NumberFormatException e) {
                    return false;
                }

                if(DateUtils.getDateFromPicker(drugDate.get(i), IdrugHour.get(i), IdrugMin.get(i)).after(now))
                    return false;

                if(IdrugQuantity.get(i) <= 0 || !DateUtils.hoursLegal(IdrugHour.get(i)) || !DateUtils.minsLegal(IdrugMin.get(i)))
                    return false;
            }
        }

        // Sintomi
        for(int i = 0; i < symDesc.size(); i++) {
            if(ObjectUtils.oneObjectFull(symDesc.get(i), symStartDate.get(i), symStartHour.get(i), symStartMin.get(i),
                    symEndDate.get(i), symEndHour.get(i), symEndMin.get(i))) {

                if(!ObjectUtils.allObjectsFull(symDesc.get(i), symStartDate.get(i), symStartHour.get(i), symStartMin.get(i),
                        symEndDate.get(i), symEndHour.get(i), symEndMin.get(i))) {
                    System.out.println("Sintomi");
                    return false;
                }

                // Controllo sui valori
                try {
                    IsymStartHour.set(i, Integer.parseInt(symStartHour.get(i).getText()));
                    IsymStartMin.set(i, Integer.parseInt(symStartMin.get(i).getText()));
                    IsymEndHour.set(i, Integer.parseInt(symEndHour.get(i).getText()));
                    IsymEndMin.set(i, Integer.parseInt(symEndMin.get(i).getText()));
                }
                catch (NumberFormatException e) {
                    System.out.println("Sintomi numeri");
                    return false;
                }

                if(DateUtils.getDateFromPicker(symStartDate.get(i), IsymStartHour.get(i), IsymStartMin.get(i)).after(now) ||
                        DateUtils.getDateFromPicker(symEndDate.get(i), IsymEndHour.get(i), IsymEndMin.get(i)).after(now)) {
                    System.out.println("Sintomi data");
                    return false;
                }

                if(!DateUtils.hoursLegal(IsymStartHour.get(i), IsymEndHour.get(i)) ||
                        !DateUtils.minsLegal(IsymStartMin.get(i), IsymEndMin.get(i))) {
                    System.out.println("Sintomi ora legale");
                    return false;
                }
            }
        }

        return true;
    }

    private void clearAll() {
        resetValues();

        sbpVal.setText("");
        dbpVal.setText("");
        bloodPressDate.setValue(null);
        bloodPressHour.setText("");
        bloodPressMin.setText("");

        drugBox.getChildren().removeAll(drugBox.getChildren());

        drugTaken.clear();
        drugQuantity.clear();
        drugDate.clear();
        drugHour.clear();
        drugMin.clear();

        addDrugRow();

        symBox.getChildren().removeAll(symBox.getChildren());

        symDesc.clear();
        symStartDate.clear();
        symStartHour.clear();
        symStartMin.clear();
        symEndDate.clear();
        symEndHour.clear();
        symEndMin.clear();

        addSymptomsRow();
    }

    private void resetValues() {
        // Ne basta uno per riga per dare errore
        IbloodPressHour = -1;

        IdrugQuantity.clear();
        IdrugHour.clear();
        IdrugMin.clear();

        IsymStartHour.clear();
        IsymStartMin.clear();
        IsymEndHour.clear();
        IsymEndMin.clear();
    }

    private void addDrugRow() {
        final int MARGIN = 10;

        HBox row = drugRow();
        VBox.setMargin(row, new Insets(MARGIN, 0, 0, MARGIN));

        drugBox.getChildren().add(row);
        drugBox.setPrefHeight(drugBox.getHeight() + row.getHeight() + 2 * MARGIN);
    }

    private HBox drugRow() {
        Button resetRowBtn = new Button("Cancella");
        drugTaken.add(new ChoiceBox<>());
        drugQuantity.add(new TextField());
        Label mu = new Label("mg");
        drugDate.add(new DatePicker());
        drugHour.add(new TextField());
        drugMin.add(new TextField());
        Label separator = new Label(":");

        // Aumento anche la lunghezza dei valori convertiti in intero
        IdrugQuantity.add(-1f);
        IdrugHour.add(-1);
        IdrugMin.add(-1);
        // --

        int i = drugTaken.size() - 1;

        for(Therapy therapy : model.therapyTable().getTherapyOfPatients(target))
            drugTaken.get(i).getItems().add(therapy.getDrug());

        HBox hbox = new HBox(resetRowBtn, drugTaken.get(i), drugQuantity.get(i), mu, drugDate.get(i), drugHour.get(i), separator, drugMin.get(i));
        hbox.setAlignment(Pos.TOP_LEFT);

        drugHour.get(i).setPromptText("ora");
        drugMin.get(i).setPromptText("minuti");

        ObjectUtils.setOnlyIntegerWithMax(23, drugHour.get(i));
        ObjectUtils.setOnlyIntegerWithMax(59, drugMin.get(i));
        ObjectUtils.setOnlyFloat(drugQuantity.get(i));
        drugDate.get(i).setEditable(false);

        drugTaken.get(i).setPrefWidth(250);
        drugQuantity.get(i).setPrefWidth(140);
        drugHour.get(i).setPrefWidth(70);
        drugMin.get(i).setPrefWidth(70);

        HBox.setMargin(resetRowBtn, new Insets(0, 0, 0, 40));
        HBox.setMargin(drugTaken.get(i), new Insets(0, 0, 0, 30));
        HBox.setMargin(drugQuantity.get(i), new Insets(0, 0, 0, 50));
        HBox.setMargin(mu, new Insets(4, 0, 0, 5));
        HBox.setMargin(drugDate.get(i), new Insets(0, 0, 0, 45));
        HBox.setMargin(drugHour.get(i), new Insets(0, 0, 0, 55));
        HBox.setMargin(separator, new Insets(4, 0, 0, 5));
        HBox.setMargin(drugMin.get(i), new Insets(0, 0, 0, 5));

        // Se inserisco una terapia --> inserisce dati di default negli altri campi e crea una nuova riga
        drugTaken.get(i).setOnAction(actionEvent -> {
            if(drugTaken.get(i).getValue() != null) {
                if(drugQuantity.get(i).getText().equals(""))
                    drugQuantity.get(i).setText(model.therapyTable().
                            getTherapyOfPatientByName(target, drugTaken.get(i).getValue()).getQtyIntakeDrug() + "");

                if(!ObjectUtils.oneObjectFull(drugDate.get(i), drugHour.get(i), drugMin.get(i)))
                    DateUtils.setDate(drugDate.get(i), drugHour.get(i), drugMin.get(i));

                if(i == drugTaken.size() - 1)
                    addDrugRow();
            }
        });

        resetRowBtn.setOnAction(actionEvent -> {
            // Resetta i valori
            drugTaken.get(i).setValue(null);
            drugQuantity.get(i).setText("");
            drugDate.get(i).setValue(null);
            drugHour.get(i).setText("");
            drugMin.get(i).setText("");
        });

        return hbox;
    }

    private void addSymptomsRow() {
        final int MARGIN = 10;

        HBox row = symptomsRow();
        VBox.setMargin(row, new Insets(MARGIN, 0, 0, MARGIN));

        symBox.getChildren().add(row);
        symBox.setPrefHeight(symBox.getHeight() + row.getHeight() + 2 * MARGIN);
    }

    private HBox symptomsRow() {
        Button resetRowBtn = new Button("Cancella");
        symDesc.add(new TextField());
        symStartDate.add(new DatePicker());
        symStartHour.add(new TextField());
        symStartMin.add(new TextField());
        symEndDate.add(new DatePicker());
        symEndHour.add(new TextField());
        symEndMin.add(new TextField());
        Label separator1 = new Label(":");
        Label separator2 = new Label(":");

        // Aumento anche la lunghezza dei valori convertiti in intero
        IsymStartHour.add(-1);
        IsymStartMin.add(-1);
        IsymEndHour.add(-1);
        IsymEndMin.add(-1);
        // --

        int i = symDesc.size() - 1;

        HBox hbox = new HBox(resetRowBtn, symDesc.get(i), symStartDate.get(i), symStartHour.get(i), separator1, symStartMin.get(i),
                symEndDate.get(i), symEndHour.get(i), separator2, symEndMin.get(i));
        hbox.setAlignment(Pos.TOP_LEFT);

        symStartHour.get(i).setPromptText("ora");
        symStartMin.get(i).setPromptText("minuti");
        symEndHour.get(i).setPromptText("ora");
        symEndMin.get(i).setPromptText("minuti");

        ObjectUtils.setOnlyIntegerWithMax(23, symStartHour.get(i), symEndHour.get(i));
        ObjectUtils.setOnlyIntegerWithMax(59, symStartMin.get(i), symEndMin.get(i));
        symStartDate.get(i).setEditable(false);
        symEndDate.get(i).setEditable(false);

        symDesc.get(i).setPrefWidth(250);
        symStartDate.get(i).setPrefWidth(130);
        symStartHour.get(i).setPrefWidth(70);
        symStartMin.get(i).setPrefWidth(70);
        symEndDate.get(i).setPrefWidth(130);
        symEndHour.get(i).setPrefWidth(70);
        symEndMin.get(i).setPrefWidth(70);

        HBox.setMargin(resetRowBtn, new Insets(0, 0, 0, 10));
        HBox.setMargin(symDesc.get(i), new Insets(0, 0, 0, 30));
        HBox.setMargin(symStartDate.get(i), new Insets(0, 0, 0, 50));
        HBox.setMargin(symStartHour.get(i), new Insets(0, 0, 0, 30));
        HBox.setMargin(separator1, new Insets(4, 0, 0, 5));
        HBox.setMargin(symStartMin.get(i), new Insets(0, 0, 0, 5));
        HBox.setMargin(symEndDate.get(i), new Insets(0, 0, 0, 50));
        HBox.setMargin(symEndHour.get(i), new Insets(0, 0, 0, 30));
        HBox.setMargin(separator2, new Insets(4, 0, 0, 5));
        HBox.setMargin(symEndMin.get(i), new Insets(0, 0, 0, 5));

        // Se inserisco una terapia --> inserisce dati di default negli altri campi e crea una nuova riga
        symDesc.get(i).focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean old, Boolean onFocus) {
                if(!onFocus && !symDesc.get(i).getText().equals("")) {
                    if(!ObjectUtils.oneObjectFull(symStartDate.get(i), symStartHour.get(i), symStartMin.get(i)))
                        DateUtils.setDate(symStartDate.get(i), symStartHour.get(i), symStartMin.get(i));
                    if(!ObjectUtils.oneObjectFull(symEndDate.get(i), symEndHour.get(i), symEndMin.get(i)))
                        DateUtils.setDate(symEndDate.get(i), symEndHour.get(i), symEndMin.get(i));

                    if(i == symDesc.size() - 1)
                        addSymptomsRow();
                }
            }
        });
        symDesc.get(i).setOnAction(actionEvent -> symDesc.get(i).getParent().requestFocus());

        resetRowBtn.setOnAction(actionEvent -> {
            // Resetta i valori
            symDesc.get(i).setText("");
            symStartDate.get(i).setValue(null);
            symStartHour.get(i).setText("");
            symStartMin.get(i).setText("");
            symEndDate.get(i).setValue(null);
            symEndHour.get(i).setText("");
            symEndMin.get(i).setText("");
        });

        return hbox;
    }

    // -- Alert Scene --
    private void lookupAlertBox() {
        alertListView = (ListView<String>) alertBox.lookup("#alertListView");

        alertListView.getItems().clear();

        for(Alert alert : alertList)
            alertListView.getItems().add(alert.toStringPatient());

        ((Button) alertBox.lookup("#readBtn")).setOnAction(actionEvent -> {
            for(Alert alert : alertList)
                alert.setPatientRead(true);

            stackPane.getChildren().remove(alertBox);
            title.setOpacity(1);
        });
    }

    public void show() {
        SceneLoader.getInstance().loadScene(this.patientScene);
    }
}
