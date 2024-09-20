package com.example.progettoisw.utils;

import com.example.progettoisw.model.Model;
import com.example.progettoisw.model.type.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlertUtils {
    private static final long DAY_IN_MS = 1000 * 60 * 60 * 24;

    public static void checkPatientAlerts(Patient patient) {
        checkIfPatientSubmittedRecently(patient);

        List<Therapy> patientTherapies = Model.getInstance().therapyTable().getTherapyOfPatients(patient);

        // Check sulle terapie di ieri
        for(Therapy therapy : patientTherapies)
            AlertUtils.checkPatientDosesForDay(patient, therapy, new Date(System.currentTimeMillis() - DAY_IN_MS));

        garbageCollector();
    }

    public static void checkIfPatientSubmittedRecently(Patient patient) {
        Model model = Model.getInstance();
        List<DailySurveys> dailySurveysList = model.dailySurveysTable().getDailySurveysFromPatientAndPeriod(patient,
                new Date(System.currentTimeMillis() - DAY_IN_MS), new Date(System.currentTimeMillis()));

        if(!dailySurveysList.isEmpty())
            return;

        if(model.alertTable().getAllPatientAlert(patient).stream().anyMatch(alert -> alert.getAlertType() == Alert.Type.Inactivity)) {
            model.alertTable().getAllPatientAlert(patient).stream().filter(alert -> alert.getAlertType() == Alert.Type.Inactivity)
                    .forEach(alert -> {
                        alert.setMedicRead(false);
                        alert.setPatientRead(false);
                    });
            return;
        }

        // Crea alert
        model.alertTable().insert(new Alert(patient.getMedicId(), patient.getId(),
                "inserito dati per più di 3 giorni consecutivi",
                false, false, Alert.Type.Inactivity, new Date(System.currentTimeMillis())));
    }

    public static void checkIfPatientFollowsPrescription(Patient patient, TakingDrug takingDrug, Date date) {
        Model model = Model.getInstance();

        Therapy therapy = model.therapyTable().getTherapyOfPatientByName(patient, takingDrug.getDrug());

        if(therapy.getQtyIntakeDrug() != takingDrug.getQty())
            // Crea alert
            model.alertTable().insert(new Alert(patient.getMedicId(), patient.getId(),
                    "assunto " + takingDrug.getQty() + " mg di " + takingDrug.getDrug() +
                            ", invece di " + therapy.getQtyIntakeDrug() + " mg",
                    false, false, Alert.Type.WrongDrug, date));

        float sumOfDoses = (float) model.takingDrugTable().getTakingDrugFromPatient(patient).stream().
                filter(drug -> DateUtils.isSameDay(drug.getDate(), date)).
                mapToDouble(TakingDrug::getQty).sum();

        String description = "assunto " + sumOfDoses + " mg di " + takingDrug.getDrug() +
                " come dose totale giornaliera, invece di " +
                therapy.getDailyIntake() * therapy.getQtyIntakeDrug() + " mg";

        // Elimino gli alert dello stesso giorno (la quantità può solo aumentare)
        model.alertTable().getAllPatientAlert(patient).stream()
                .filter(alert -> alert.getAlertType() == Alert.Type.TooMuchDrug)
                .filter(alert -> DateUtils.isSameDay(alert.getDate(), date))
                .forEach(alert -> model.alertTable().remove(alert));

        if(sumOfDoses > therapy.getDailyIntake() * therapy.getQtyIntakeDrug())
            model.alertTable().insert(new Alert(patient.getMedicId(), patient.getId(),
                    description, false, false, Alert.Type.TooMuchDrug, date));
    }

    public static void checkPatientDosesForDay(Patient patient, Therapy therapy, Date date) {
        Model model = Model.getInstance();

        // Verifico se è già presente
        if(model.alertTable().getAllPatientAlert(patient).stream()
                .filter(alert -> alert.getAlertType() == Alert.Type.TooLittleDrug)
                .anyMatch(alert -> DateUtils.isSameDay(alert.getDate(), date)))
            return;

        float sumOfDoses = (float) model.takingDrugTable().getTakingDrugFromPatient(patient).stream().
                filter(drug -> DateUtils.isSameDay(drug.getDate(), date)).
                mapToDouble(TakingDrug::getQty).sum();

        if(sumOfDoses < therapy.getDailyIntake() * therapy.getQtyIntakeDrug())
            model.alertTable().insert(new Alert(patient.getMedicId(), patient.getId(),
                    "assunto " + sumOfDoses + " mg di " + therapy.getDrug() +
                            " come dose totale giornaliera, invece di " +
                            therapy.getDailyIntake() * therapy.getQtyIntakeDrug() + " mg",
                    false, false, Alert.Type.TooLittleDrug, date));
    }

    public static void patientActive(Patient patient) {
        Model model = Model.getInstance();

        model.alertTable().getAllPatientAlert(patient).stream()
                .filter(alert -> alert.getAlertType() == Alert.Type.Inactivity)
                .forEach(alert -> model.alertTable().remove(alert));
    }

    public static void checkPatientBloodPressure(Patient patient, int sbp, int dbp, Date date) {
        Model model = Model.getInstance();

        String messageHeader = "una pressione sanguigna di " + sbp + "/" + dbp;

        if(dbp < 90) {
            if(sbp >= 140 && sbp < 150) {
                insertBloodPressAlert(patient, messageHeader, Alert.Type.BloodPressSisBolderline, date);
                return;
            }
            if(sbp >= 150) {
                insertBloodPressAlert(patient, messageHeader, Alert.Type.BloodPressSis, date);
                return;
            }
        }

        /* N.B.: Quando la pressione sistolica e diastolica di un paziente rientrano in
        categorie differenti la classificazione va fatta in base alla categoria maggiore */

        Alert.Type sbpType = bloodPressRanges(sbp, 130, 140, 150, 160, 180);
        Alert.Type dbpType = bloodPressRanges(dbp, 85, 90, 95, 100, 110);

        Alert.Type alertType = sbpType.compareTo(dbpType) >= 0 ? sbpType : dbpType;

        if(alertType != Alert.Type.WrongDrug)
            insertBloodPressAlert(patient, messageHeader, alertType, date);
    }

    private static Alert.Type bloodPressRanges(int val, int normal, int normHigh, int bordeline, int mild, int moderate) {
        if(val < normal)
            return Alert.Type.WrongDrug;    // default per dire che è minore
        if(val < normHigh)
            return Alert.Type.BloodPressNormHigh;
        if(val < bordeline)
            return Alert.Type.BloodPressBorderline;
        if(val < mild)
            return Alert.Type.BloodPressMild;
        if(val < moderate)
            return Alert.Type.BloodPressModerate;
        return Alert.Type.BloodPressSevere;
    }

    private static void insertBloodPressAlert(Patient patient, String messageHeader, Alert.Type alertType, Date date) {
        String message = messageHeader;

        if(alertType == Alert.Type.BloodPressSisBolderline)
            message += " (Ipertensione sistolica isolata borderline)";
        else if(alertType == Alert.Type.BloodPressSis)
            message += " (Ipertensione sistolica isolata)";
        else if(alertType == Alert.Type.BloodPressNormHigh)
            message += " (Pressione sanguigna normale - alta)";
        else if(alertType == Alert.Type.BloodPressBorderline)
            message += " (Ipertensione di Grado 1 borderline)";
        else if(alertType == Alert.Type.BloodPressMild)
            message += " (Ipertensione di Grado 1 lieve)";
        else if(alertType == Alert.Type.BloodPressModerate)
            message += " (Ipertensione di Grado 2 moderata)";
        else if(alertType == Alert.Type.BloodPressSevere)
            message += " (Ipertensione di Grado 3 grave)";


        Model.getInstance().alertTable().insert(new Alert(patient.getMedicId(), patient.getId(),
                message, false, false, Alert.Type.BloodPressSisBolderline, date));
    }

    private static void garbageCollector() {
        Model model = Model.getInstance();

        model.alertTable().toList().stream().
                filter(alert -> alert.isMedicRead() && alert.isPatientRead()).
                forEach(alert -> model.alertTable().remove(alert));
    }
}
