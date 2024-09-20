package com.example.progettoisw.view.medic;

import com.example.progettoisw.Application;
import com.example.progettoisw.SceneLoader;
import com.example.progettoisw.model.Model;
import com.example.progettoisw.model.type.*;
import com.example.progettoisw.model.type.Alert;
import com.example.progettoisw.utils.AlertUtils;
import com.example.progettoisw.utils.ObjectUtils;
import com.example.progettoisw.utils.properties.StringListProperty;
import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MedicController {

    private final Medic target;
    private Patient selectedPatient;

    // Master Properties
    private final StringProperty medicName = new SimpleStringProperty();

    // Alert
    private final VBox alertBox;
    private StackPane stackPane;
    private List<Alert> alertList = new ArrayList<>();
    private ListView<String> alertListView = new ListView<>();

    // Menu Scene
    private final Scene menuScene;
    private ChoiceBox<String> selectorPatient;
    private Button specifyTherapyBtn;
    private Button dataPatientBtn;
    private Button updateInfoBtn;

    // Therapy Scene
    private static final String NEW_THERAPY = "Nuova Terapia";
    private final Scene therapyScene;
    private Pane containerForm;
    private Label patientLabel;
    private ChoiceBox<String> selectorTherapy;
    private TextField drugField;
    private TextField dailyQtyField;
    private TextField qtyField;
    private TextField indicationField;
    private Button deleteBtn;
    private Button confirmBtn;

    // Info Patient Scene
    private static final String NEW_TEXT_LISTVIEW = "*new*";
    private final Scene infoPatientScene;
    private Label IP_patientLabel;
    private ListView<String> IP_riskFactorsView;
    private ListView<String> IP_previousDiseasesView;
    private ListView<String> IP_comorbidityView;

    // Data Patient Scene
    private final Scene dataPatientScene;
    private Label DP_patientLabel;
    private ListView<String> DP_riskFactorsView;
    private ListView<String> DP_previousDiseasesView;
    private ListView<String> DP_comorbidityView;
    private TableView<DailySurveys> DP_bloodPressureTable;
    private TableView<TakingDrug> DP_therapyTable;
    private TableView<Symptom> DP_symptomTable;

    // Summary Scene
    private SummaryType summaryType;
    private Date from , to;
    private XYChart.Series<String, Integer> sbpData, dbpData;
    private final Scene summaryScene;
    private Label SM_patientLabel;
    private LineChart<String, Integer> SM_summaryChart;
    private Label SM_periodLabel;
    private Button SM_prevButton;
    private Button SM_nextButton;

    public MedicController(Medic target) throws IOException {
        if (target == null)
            throw new RuntimeException("Medic must be not null");

        this.target = target;
        this.menuScene = new FXMLLoader(Application.class.getResource("view/medic/menu.fxml")).load();
        this.therapyScene = new FXMLLoader(Application.class.getResource("view/medic/therapy.fxml")).load();
        this.infoPatientScene = new FXMLLoader(Application.class.getResource("view/medic/info.fxml")).load();
        this.dataPatientScene = new FXMLLoader(Application.class.getResource("view/medic/data.fxml")).load();
        this.summaryScene = new FXMLLoader(Application.class.getResource("view/medic/summary.fxml")).load();

        this.alertBox = new FXMLLoader(Application.class.getResource("view/alert.fxml")).load();

        this.medicName.bind(Bindings.concat("Dr. ").concat(target.nameProperty().concat(" ").concat(target.surnameProperty())));

        // Lookup Scenes
        lookupMenuScene();
        lookupTherapyScene();
        lookupInfoPatientScene();
        lookupDataPatientScene();
        lookupSummaryScene();

        checkAlerts();
    }

    private void setupMainHeader(Scene scene) {
        ((Label) scene.lookup("#medicLabel")).textProperty().bind(medicName);
        ((Button) scene.lookup("#logoutBtn")).setOnAction(actionEvent -> SceneLoader.getInstance().goToMainMenu());
    }

    private void setupSubHeader(Scene scene) {
        ((Label) scene.lookup("#medicLabel")).textProperty().bind(medicName);
        ((Button) scene.lookup("#homeBtn")).setOnAction(actionEvent -> SceneLoader.getInstance().loadScene(this.menuScene));
    }

    private void lookupMenuScene() {
        /* Head */
        setupMainHeader(this.menuScene);

        /* Body */
        stackPane = (StackPane) menuScene.lookup("#stackPane");

        this.selectorPatient = (ChoiceBox<String>) this.menuScene.lookup("#selectorPatient");
        this.selectorPatient.getItems().addAll(Model.getInstance().patientTable().getPatientOfMedic(target.getId()).
                stream().map(Patient::getFullNameCapitalized).toList());
        this.selectorPatient.setOnAction(actionEvent -> {
            changeSelectedPatient(getPatientFromSelector());

            this.specifyTherapyBtn.setDisable(false);
            this.dataPatientBtn.setDisable(false);
            this.updateInfoBtn.setDisable(false);
        });

        this.specifyTherapyBtn = (Button) this.menuScene.lookup("#specifyTherapyBtn");
        this.specifyTherapyBtn.setOnAction(actionEvent -> SceneLoader.getInstance().loadScene(this.therapyScene));

        this.dataPatientBtn = (Button) this.menuScene.lookup("#dataPatientBtn");
        this.dataPatientBtn.setOnAction(actionEvent -> SceneLoader.getInstance().loadScene(this.dataPatientScene));

        this.updateInfoBtn = (Button) this.menuScene.lookup("#updateInfoBtn");
        this.updateInfoBtn.setOnAction(actionEvent -> SceneLoader.getInstance().loadScene(this.infoPatientScene));
    }

    private void lookupTherapyScene() {
        /* Head */
        setupSubHeader(this.therapyScene);

        /* Body */
        this.containerForm = (Pane) this.therapyScene.lookup("#containerForm");
        this.containerForm.layoutXProperty().bind((this.therapyScene.widthProperty().divide(2)).subtract(this.containerForm.widthProperty().divide(2)));
        this.patientLabel = (Label) this.therapyScene.lookup("#patientLabel");
        this.patientLabel.layoutXProperty().bind((this.containerForm.widthProperty().divide(2)).subtract(this.patientLabel.widthProperty().divide(2)));
        this.selectorTherapy = (ChoiceBox<String>) this.therapyScene.lookup("#selectorTherapy");
        this.selectorTherapy.setOnAction(actionEvent -> {
            if (this.selectorTherapy.getValue() != NEW_THERAPY) {
                Therapy targetTherapy = getTherapyFromSelector();

                if (targetTherapy != null) {
                    this.drugField.setText(targetTherapy.getDrug());
                    this.dailyQtyField.setText(String.valueOf(targetTherapy.getDailyIntake()));
                    this.qtyField.setText(String.valueOf(targetTherapy.getQtyIntakeDrug()));
                    this.indicationField.setText(targetTherapy.getIndications());

                    this.deleteBtn.setDisable(false);
                    this.confirmBtn.setText("Modifica Terapia");
                }
            }else {
                this.drugField.setText("");
                this.dailyQtyField.setText("");
                this.qtyField.setText("");
                this.indicationField.setText("");

                this.deleteBtn.setDisable(true);
                this.confirmBtn.setText("Aggiungi Terapia");
            }
        });
        this.drugField = (TextField) this.therapyScene.lookup("#drugField");
        this.dailyQtyField = (TextField) this.therapyScene.lookup("#dailyQtyField");
        ObjectUtils.setOnlyInteger(dailyQtyField);

        this.qtyField = (TextField) this.therapyScene.lookup("#qtyField");
        ObjectUtils.setOnlyFloat(qtyField);

        this.indicationField = (TextField) this.therapyScene.lookup("#indicationField");
        this.deleteBtn = (Button) this.therapyScene.lookup("#deleteBtn");
        this.deleteBtn.setOnAction(actionEvent -> {
            Therapy targetTherapy = getTherapyFromSelector();

            if (targetTherapy != null) {
                Model.getInstance().therapyTable().remove(targetTherapy);
                updateTherapySelector(NEW_THERAPY);
            }
        });

        this.confirmBtn = (Button) this.therapyScene.lookup("#confirmBtn");
        this.confirmBtn.setOnAction(actionEvent -> {
            boolean err = false;
            if (this.drugField.getText().strip().equals("")) {
                err = true;
                setWarningField(this.drugField);
            }

            if (this.dailyQtyField.getText().strip().equals("") || Integer.valueOf(this.dailyQtyField.getText()) == 0) {
                err = true;
                setWarningField(this.dailyQtyField);
            }

            if (this.qtyField.getText().strip().equals("") || Float.valueOf(this.qtyField.getText()) == 0) {
                err = true;
                setWarningField(this.qtyField);
            }

            if (err) return;

            if (this.selectorTherapy.getValue().equals(NEW_THERAPY)) {
                // Add new Therapy
                Model.getInstance().therapyTable().insert(new Therapy(
                        this.selectedPatient.getId(),
                        this.target.getId(),
                        this.drugField.getText(),
                        Integer.parseInt(this.dailyQtyField.getText()),
                        Double.parseDouble(this.qtyField.getText()),
                        this.indicationField.getText()
                ));
            } else {
                // Edit exist Therapy
                Therapy therapyTarget = getTherapyFromSelector();

                therapyTarget.setDrug(this.drugField.getText());
                therapyTarget.setDailyIntake(Integer.parseInt(this.dailyQtyField.getText()));
                therapyTarget.setQtyIntakeDrug(Double.parseDouble(this.qtyField.getText()));
                therapyTarget.setIndications(this.indicationField.getText());
            }

            updateTherapySelector(this.drugField.getText());
        });
    }

    private void lookupInfoPatientScene() {
        /* Head */
        setupSubHeader(this.infoPatientScene);

        /* Body */
        this.IP_patientLabel = (Label) this.infoPatientScene.lookup("#patientLabel");
        this.IP_riskFactorsView = (ListView<String>) this.infoPatientScene.lookup("#riskFactorsView");
        setupListViewIP(IP_riskFactorsView);
        this.IP_previousDiseasesView = (ListView<String>) this.infoPatientScene.lookup("#previousDiseasesView");
        setupListViewIP(IP_previousDiseasesView);
        this.IP_comorbidityView = (ListView<String>) this.infoPatientScene.lookup("#comorbidityView");
        setupListViewIP(IP_comorbidityView);

        ((Button) this.infoPatientScene.lookup("#confirmButton")).setOnAction(actionEvent -> {
            saveListViewIP(this.IP_riskFactorsView, this.selectedPatient.listRiskFactorsProperty());
            saveListViewIP(this.IP_previousDiseasesView, this.selectedPatient.listPreviousDiseasesProperty());
            saveListViewIP(this.IP_comorbidityView, this.selectedPatient.listComorbidityProperty());

            System.out.println("Informazioni aggiornate con successo");
        });
    }

    private void lookupDataPatientScene() {
        /* Head */
        setupSubHeader(this.dataPatientScene);

        /* Body */
        this.DP_patientLabel = (Label) this.dataPatientScene.lookup("#patientLabel");
        this.DP_riskFactorsView = (ListView<String>) this.dataPatientScene.lookup("#riskFactorsView");
        this.DP_previousDiseasesView = (ListView<String>) this.dataPatientScene.lookup("#previousDiseasesView");
        this.DP_comorbidityView = (ListView<String>) this.dataPatientScene.lookup("#comorbidityView");
        ((Button) this.dataPatientScene.lookup("#summaryWeeklyBtn")).setOnAction(actionEvent -> {
            this.summaryType = SummaryType.Weekly;
            setCurrentWeekPeriod();
            updateDataSummaryChart();

            SceneLoader.getInstance().loadScene(this.summaryScene);
        });
        ((Button) this.dataPatientScene.lookup("#summaryMonthlyBtn")).setOnAction(actionEvent -> {
            this.summaryType = SummaryType.Monthly;
            setCurrentMonthPeriod();
            updateDataSummaryChart();

            SceneLoader.getInstance().loadScene(this.summaryScene);
        });
        this.DP_bloodPressureTable = (TableView<DailySurveys>) this.dataPatientScene.lookup("#bloodPressureTable");
        setupBloodPressureTable();
        this.DP_therapyTable = (TableView<TakingDrug>) this.dataPatientScene.lookup("#therapyTable");
        setupTherapyTable();
        this.DP_symptomTable = (TableView<Symptom>) this.dataPatientScene.lookup("#symptomTable");
        setupSymptomTable();
    }

    private void lookupSummaryScene() {
        /* Head */
        setupSubHeader(this.summaryScene);
        ((Button) this.summaryScene.lookup("#homeBtn")).setOnAction(actionEvent -> SceneLoader.getInstance().loadScene(this.dataPatientScene));

        /* Body */
        this.SM_patientLabel = (Label) this.summaryScene.lookup("#patientLabel");
        this.SM_summaryChart = (LineChart<String, Integer>) this.summaryScene.lookup("#summaryChart");
        setupSummaryChart();
        this.SM_periodLabel = (Label) this.summaryScene.lookup("#periodLabel");
        this.SM_prevButton = (Button) this.summaryScene.lookup("#prevBtn");
        this.SM_prevButton.setOnAction(actionEvent -> {
            prevPeriod();
            updateDataSummaryChart();
        });
        this.SM_nextButton = (Button) this.summaryScene.lookup("#nextBtn");
        this.SM_nextButton.setOnAction(actionEvent -> {
            nextPeriod();
            updateDataSummaryChart();
        });
    }

    /* Therapy Scene Functions */
    private Patient getPatientFromSelector() {
        int selectedRow = this.selectorPatient.getItems().indexOf(this.selectorPatient.getValue());
        if (selectedRow < 0)
            return null;

        return Model.getInstance().patientTable().getPatientOfMedic(target.getId()).get(selectedRow);
    }

    private Therapy getTherapyFromSelector() {
        return Model.getInstance().therapyTable().getTherapyOfPatientByName(this.selectedPatient, this.selectorTherapy.getValue());
    }

    private void updateTherapySelector(String selector) {
        this.selectorTherapy.getItems().clear();
        this.selectorTherapy.getItems().add(NEW_THERAPY);
        this.selectorTherapy.getItems().addAll(Model.getInstance().therapyTable().getTherapyOfPatients(this.selectedPatient).stream().map(therapy -> therapy.getDrug()).toList());
        this.selectorTherapy.setValue(selector);
    }

    private void setWarningField(TextField field) {
        Timeline shake = new Timeline(
                new KeyFrame(Duration.millis(0), new KeyValue(field.translateXProperty(), 0, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(50), new KeyValue(field.translateXProperty(), -3, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(100), new KeyValue(field.translateXProperty(), 0, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(150), new KeyValue(field.translateXProperty(), 3, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(200), new KeyValue(field.translateXProperty(), 0, Interpolator.EASE_BOTH))
        );
        shake.setCycleCount(3);
        shake.setRate(1.5);
        shake.setOnFinished(actionEvent -> field.setStyle("-fx-text-box-border: rgba(255, 0, 0, 0)"));

        field.setStyle("-fx-text-box-border: rgba(255, 0, 0, 0.4)");
        shake.play();
    }

    /* Info Patient Scene Functions */
    private void setupListViewIP(ListView<String> listView) {
        /* During Setup */

        // Set list editable
        listView.setEditable(true);
        // Setup cell factory (TextFieldListCell -> so you can edit row)
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                TextFieldListCell<String> cell = new TextFieldListCell<>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        setStyle(getIndex() == getListView().getItems().size() - 1 ?
                                "-fx-text-fill: rgba(0, 0, 0, .4);" : "-fx-text-fill: black;");
                    }
                };
                cell.setConverter(new StringConverter<String>() {
                    @Override
                    public String toString(String s) {
                        return s;
                    }

                    @Override
                    public String fromString(String s) {
                        return s;
                    }
                });

                return cell;
            }
        });

        listView.getItems().clear();
        listView.getItems().addListener((ListChangeListener<? super String>) change -> {
            while(change.next()) {
                if (change.wasReplaced()) {
                    if (change.getFrom() != (listView.getItems().size() - 1) && listView.getItems().get(change.getFrom()).equals(""))
                        // Delete line
                        listView.getItems().remove(change.getFrom());
                    else if (change.getFrom() == (listView.getItems().size() - 1) && listView.getItems().get(change.getFrom()).equals(""))
                        // Reset last line
                        listView.getItems().set(change.getFrom(), NEW_TEXT_LISTVIEW);

                    if (change.getFrom() == (listView.getItems().size() - 1) && !listView.getItems().get(change.getFrom()).equals(NEW_TEXT_LISTVIEW))
                        // Add last line
                        listView.getItems().add(NEW_TEXT_LISTVIEW);
                }
            }
        });
    }

    private void setupListViewIP(ListView<String> listView, StringListProperty<String> listProperty) {
        /* When change Patient */

        listView.getItems().clear();
        listView.getItems().addAll(listProperty.stream().filter(s -> !s.equals("")).toList());
        listView.getItems().add(NEW_TEXT_LISTVIEW);
    }

    private void saveListViewIP(ListView<String> listView, StringListProperty<String> listProperty) {
        listProperty.clear();
        listView.getItems().stream().filter(str -> !str.equals(NEW_TEXT_LISTVIEW)).forEach(str -> listProperty.add(str));
    }

    /* Data Patient Functions */
    private void setupListViewDP(ListView<String> listView, StringListProperty<String> listProperty) {
        /* When change Patient */

        listView.getItems().clear();
        listView.getItems().addAll(listProperty.stream().filter(s -> !s.equals("")).toList());
    }

    private void setupBloodPressureTable() {
        TableColumn<DailySurveys, String> sbpColumn = new TableColumn<>("SBP");
        sbpColumn.setCellValueFactory(dailySurveysStringCellDataFeatures -> dailySurveysStringCellDataFeatures.getValue().sbpProperty().asString());
        sbpColumn.setResizable(false);
        sbpColumn.setReorderable(false);
        TableColumn<DailySurveys, String> dbpColumn = new TableColumn<>("DBP");
        dbpColumn.setCellValueFactory(dailySurveysStringCellDataFeatures -> dailySurveysStringCellDataFeatures.getValue().dbpProperty().asString());
        dbpColumn.setResizable(false);
        dbpColumn.setReorderable(false);
        TableColumn<DailySurveys, String> dataColumn = new TableColumn<>("Data");
        dataColumn.setCellValueFactory(dailySurveysStringCellDataFeatures -> dailySurveysStringCellDataFeatures.getValue().dateProperty().asString());
        dataColumn.setResizable(false);
        dataColumn.setReorderable(false);
        List<TableColumn<DailySurveys, String>> columns = List.of(sbpColumn, dbpColumn, dataColumn);

        columns.forEach(tableColumn -> {
            tableColumn.setMinWidth(columns.indexOf(tableColumn) == 2 ? 220: 80);
            tableColumn.setPrefWidth(this.DP_bloodPressureTable.getWidth() / columns.size());
        });

        this.DP_bloodPressureTable.getColumns().addAll(columns);
    }

    private void setDataBloodPressureTable() {
        this.DP_bloodPressureTable.getItems().clear();
        this.DP_bloodPressureTable.getItems().addAll(Model.getInstance().dailySurveysTable().getDailySurveysFromPatient(selectedPatient));
    }

    private void setupTherapyTable() {
        TableColumn<TakingDrug, String> sbpColumn = new TableColumn<>("Farmaco");
        sbpColumn.setCellValueFactory(dailySurveysStringCellDataFeatures -> dailySurveysStringCellDataFeatures.getValue().drugProperty());
        sbpColumn.setResizable(false);
        sbpColumn.setReorderable(false);
        TableColumn<TakingDrug, String> dbpColumn = new TableColumn<>("Quantità (mg)");
        dbpColumn.setCellValueFactory(dailySurveysStringCellDataFeatures -> dailySurveysStringCellDataFeatures.getValue().qtyProperty().asString());
        dbpColumn.setResizable(false);
        dbpColumn.setReorderable(false);
        TableColumn<TakingDrug, String> dataColumn = new TableColumn<>("Data");
        dataColumn.setCellValueFactory(dailySurveysStringCellDataFeatures -> dailySurveysStringCellDataFeatures.getValue().dateProperty().asString());
        dataColumn.setResizable(false);
        dataColumn.setReorderable(false);
        List<TableColumn<TakingDrug, String>> columns = List.of(sbpColumn, dbpColumn, dataColumn);

        columns.forEach(tableColumn -> {
            tableColumn.setMinWidth(columns.indexOf(tableColumn) == 2 ? 180: 100);
            tableColumn.setPrefWidth(this.DP_therapyTable.getWidth() / columns.size());
        });

        this.DP_therapyTable.getColumns().addAll(columns);
    }

    private void setDataSymptomTable() {
        this.DP_symptomTable.getItems().clear();
        this.DP_symptomTable.getItems().addAll(Model.getInstance().symptomsTable().getSymptomsOfPatient(selectedPatient));
    }

    private void setupSymptomTable() {
        TableColumn<Symptom, String> sbpColumn = new TableColumn<>("Descrizione");
        sbpColumn.setCellValueFactory(dailySurveysStringCellDataFeatures -> dailySurveysStringCellDataFeatures.getValue().descriptionProperty());
        sbpColumn.setReorderable(false);
        TableColumn<Symptom, String> dbpColumn = new TableColumn<>("Periodo");
        SimpleDateFormat formatPeriod = new SimpleDateFormat("yyyy/MM/dd [HH:mm]");
        dbpColumn.setCellValueFactory(dailySurveysStringCellDataFeatures -> Bindings.createStringBinding(() -> {
            Symptom symptom = dailySurveysStringCellDataFeatures.getValue();

            return formatPeriod.format(symptom.getFrom()) + " - " + formatPeriod.format(symptom.getTo());
        }));
        dbpColumn.setResizable(false);
        dbpColumn.setReorderable(false);
        List<TableColumn<Symptom, String>> columns = List.of(sbpColumn, dbpColumn/*, dataColumn*/);

        columns.forEach(tableColumn -> {
            tableColumn.setMinWidth(columns.indexOf(tableColumn) == 1 ? 250: 130);
            tableColumn.setPrefWidth(this.DP_symptomTable.getWidth() / columns.size());
        });

        this.DP_symptomTable.getColumns().addAll(columns);
    }

    private void setDataTherapyTable() {
        this.DP_therapyTable.getItems().clear();
        this.DP_therapyTable.getItems().addAll(Model.getInstance().takingDrugTable().getTakingDrugFromPatient(selectedPatient));
    }

    /* Summary Functions */
    private void setupSummaryChart() {
        this.SM_summaryChart.setAnimated(false);

        this.sbpData = new XYChart.Series<>();
        this.sbpData.setName("SBP");

        this.dbpData = new XYChart.Series<>();
        this.dbpData.setName("DBP");

        this.SM_summaryChart.getData().clear();
        this.SM_summaryChart.getData().addAll(this.sbpData, this.dbpData);
    }

    private void setCurrentWeekPeriod() {
        this.SM_nextButton.setDisable(true);
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        this.from = new Date(calendar.getTime().getTime());

        calendar.add(Calendar.DATE, 6);
        this.to = new Date(calendar.getTime().getTime());
    }

    private void setCurrentMonthPeriod() {
        this.SM_nextButton.setDisable(true);
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        this.from = new Date(calendar.getTime().getTime());

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        this.to = new Date(calendar.getTime().getTime());
    }

    private void nextPeriod() {
        SimpleDateFormat checkWeekFormat = new SimpleDateFormat("ww-yyyy");
        SimpleDateFormat checkMonthFormat = new SimpleDateFormat("MM-yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);

        switch (summaryType) {
            case Weekly -> {
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                from = new Date(calendar.getTime().getTime());

                calendar.add(Calendar.DATE, 6);
                to = new Date(calendar.getTime().getTime());

                if (checkWeekFormat.format(from).equals(checkWeekFormat.format(new Date(System.currentTimeMillis()))))
                    this.SM_nextButton.setDisable(true);
            }
            case Monthly -> {
                calendar.add(Calendar.MONTH, 1);
                from = new Date(calendar.getTime().getTime());

                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                to = new Date(calendar.getTime().getTime());

                if (checkMonthFormat.format(from).equals(checkMonthFormat.format(new Date(System.currentTimeMillis()))))
                    this.SM_nextButton.setDisable(true);
            }
        }
    }
    private void prevPeriod() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);

        switch (summaryType) {
            case Weekly -> {
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                from = new Date(calendar.getTime().getTime());

                calendar.add(Calendar.DATE, 6);
                to = new Date(calendar.getTime().getTime());
            }
            case Monthly -> {
                calendar.add(Calendar.MONTH, -1);
                from = new Date(calendar.getTime().getTime());

                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                to = new Date(calendar.getTime().getTime());
            }
        }

        if (this.SM_nextButton.isDisable())
            this.SM_nextButton.setDisable(false);
    }

    private void updateDataSummaryChart() {
        List<DailySurveys> dailySurveysInPeriod = Model.getInstance().dailySurveysTable().getDailySurveysFromPatientAndPeriod(selectedPatient, from, to);
        SimpleDateFormat dateFormat;
        switch (summaryType) {
            case Weekly -> dateFormat = new SimpleDateFormat("E HH:mm");
            default -> dateFormat = new SimpleDateFormat("E d HH:mm");
        }

        switch (summaryType) {
            case Weekly -> this.SM_periodLabel.setText(new SimpleDateFormat("W'^ settimana di' MMMMM yyyy").format(from));
            case Monthly -> this.SM_periodLabel.setText(new SimpleDateFormat("'mese di' MMMMM yyyy").format(from));
        }

        CategoryAxis categoryAxis = (CategoryAxis) this.SM_summaryChart.getXAxis();
        categoryAxis.getCategories().clear();
        categoryAxis.getCategories().addAll(dailySurveysInPeriod.stream().map(dailySurveys -> dateFormat.format(dailySurveys.getDate())).distinct().toList());

        this.sbpData.getData().clear();
        this.sbpData.getData().addAll(dailySurveysInPeriod.stream().map(dailySurveys -> new XYChart.Data<>(dateFormat.format(dailySurveys.getDate()), dailySurveys.getSBP())).toList());
        if (this.sbpData.getData().size() == 0) {
            this.sbpData.getData().add(new XYChart.Data<>("Nessun Dato", 0));
        }

        this.dbpData.getData().clear();
        this.dbpData.getData().addAll(dailySurveysInPeriod.stream().map(dailySurveys -> new XYChart.Data<>(dateFormat.format(dailySurveys.getDate()), dailySurveys.getDBP())).toList());
        if (this.dbpData.getData().size() == 0) {
            this.dbpData.getData().add(new XYChart.Data<>("Nessun Dato", 0));
        }
    }

    /* Alerts */
    private void checkAlerts() {
        Model model = Model.getInstance();
        for(Patient patient : model.patientTable().getPatientOfMedic(target.getId()))
            AlertUtils.checkPatientAlerts(patient);

        alertList = model.alertTable().getMedicAlertNotRead(target);

        if(alertList.isEmpty())
            return;

        stackPane.getChildren().add(alertBox);

        // alertBox Properties
        alertBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        alertBox.prefWidthProperty().bind(menuScene.widthProperty());
        StackPane.setMargin(alertBox, new Insets(50, 0, 0, 0));


        lookupAlertBox();
    }

    private void lookupAlertBox() {
        alertListView = (ListView<String>) alertBox.lookup("#alertListView");

        for(Alert alert : alertList)
            alertListView.getItems().add(alert.toStringMedic());

        ((Button) alertBox.lookup("#readBtn")).setOnAction(actionEvent -> {
            for(Alert alert : alertList)
                alert.setMedicRead(true);

            stackPane.getChildren().remove(alertBox);
        });
    }

    /* Global Functions */
    private void changeSelectedPatient(Patient patient) {
        this.selectedPatient = patient;

        /* Therapy */
        this.patientLabel.setText("Paziente " + this.selectedPatient.getFullNameCapitalized());
        updateTherapySelector(NEW_THERAPY);

        /* Info Patient */
        this.IP_patientLabel.setText(this.selectedPatient.getFullNameCapitalized());
        setupListViewIP(this.IP_riskFactorsView, this.selectedPatient.listRiskFactorsProperty());
        setupListViewIP(this.IP_previousDiseasesView, this.selectedPatient.listPreviousDiseasesProperty());
        setupListViewIP(this.IP_comorbidityView, this.selectedPatient.listComorbidityProperty());

        /* Data Patient */
        this.DP_patientLabel.setText(this.selectedPatient.getFullNameCapitalized());
        setupListViewDP(this.DP_riskFactorsView, this.selectedPatient.listRiskFactorsProperty());
        setupListViewDP(this.DP_previousDiseasesView, this.selectedPatient.listPreviousDiseasesProperty());
        setupListViewDP(this.DP_comorbidityView, this.selectedPatient.listComorbidityProperty());
        setDataBloodPressureTable();
        setDataTherapyTable();
        setDataSymptomTable();

        /* Summary */
        this.SM_patientLabel.setText(this.selectedPatient.getFullNameCapitalized());
    }

    public void show() {
        SceneLoader.getInstance().loadScene(this.menuScene);
    }

    private enum SummaryType {
        Weekly,
        Monthly
    }
}
