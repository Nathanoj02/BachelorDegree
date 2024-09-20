package com.example.progettoisw.view.auth;

import com.example.progettoisw.Application;
import com.example.progettoisw.SceneLoader;
import com.example.progettoisw.model.Model;
import com.example.progettoisw.model.ModelOperators;
import com.example.progettoisw.model.type.Medic;
import com.example.progettoisw.model.type.Patient;
import com.example.progettoisw.view.medic.MedicController;
import com.example.progettoisw.view.operator.OperatorController;
import com.example.progettoisw.view.patient.PatientController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AuthController {

    private static final String ERROR_MESSAGE = "Nome o Password errati. Riprova";
    private final UserType target;
    private final Scene authScene;
    private Label messageLabel;
    private TextField emailField;
    private PasswordField passwordField;
    private Button backBtn;
    private Button loginBtn;

    public AuthController(UserType target) throws IOException {
        this.target = target;
        this.authScene = new FXMLLoader(Application.class.getResource("view/auth.fxml")).load();

        setTitle();
        lookupScene();
        setupHandler();
    }

    private void setTitle() {
        Label title = ((Label) this.authScene.lookup("#title"));

        String type = "...";
        switch (this.target) {
            case Operator -> type = "Responsabile";
            case Medic -> type = "Medico";
            case Patient -> type = "Paziente";
        }

        title.setText(title.getText() + " " + type);
    }

    private void lookupScene() {
        this.messageLabel = (Label) this.authScene.lookup("#messageLabel");
        this.emailField = (TextField) this.authScene.lookup("#emailField");
        this.passwordField = (PasswordField) this.authScene.lookup("#passwordField");
        this.backBtn = (Button) this.authScene.lookup("#backBtn");
        this.loginBtn = (Button) this.authScene.lookup("#loginBtn");
    }

    private void setupHandler() {
        this.backBtn.setOnAction(actionEvent -> SceneLoader.getInstance().goToMainMenu());
        this.loginBtn.setOnAction(loginHandler());
    }

    private EventHandler<ActionEvent> loginHandler() {
        return actionEvent -> {
            String email = emailField.getText().toLowerCase(),
                    password = passwordField.getText();

            switch (this.target) {
                case Operator -> {
                    if (ModelOperators.getInstance().isAuthorized(email, password)) {
                        try {
                            new OperatorController().show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else messageLabel.setText(ERROR_MESSAGE);
                }
                case Medic -> {
                    Medic find = Model.getInstance().medicTable().findAuthorized(email, password);
                    if (find != null) {
                        try {
                            new MedicController(find).show();
                        } catch (IOException e) {
                            throw new RuntimeException("Error during creating MedicController (Failed to find fxml in resources)", e);
                        }
                    }else messageLabel.setText(ERROR_MESSAGE);
                }
                case Patient -> {
                    Patient find = Model.getInstance().patientTable().findAuthorized(email, password);
                    if (find != null) {
                        try {
                            new PatientController(find).show();
                        } catch (IOException e) {
                            throw new RuntimeException("Error during creating PatientController (Failed to find fxml in resources)", e);
                        }
                    }else messageLabel.setText(ERROR_MESSAGE);
                }
            }
        };
    }

    public void show() {
        SceneLoader.getInstance().loadScene(this.authScene);
    }
}
