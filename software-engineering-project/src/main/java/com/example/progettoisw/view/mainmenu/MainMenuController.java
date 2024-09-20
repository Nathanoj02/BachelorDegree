package com.example.progettoisw.view.mainmenu;

import com.example.progettoisw.SceneLoader;
import com.example.progettoisw.view.auth.AuthController;
import com.example.progettoisw.view.auth.UserType;

import java.io.IOException;

public class MainMenuController {

    private final MainMenuScene mainMenuScene;

    public MainMenuController() {
        this.mainMenuScene = new MainMenuScene();

        // Handler
        mainMenuScene.setOnOperatorClicked(mouseEvent -> {
            try {
                new AuthController(UserType.Operator).show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        mainMenuScene.setOnMedicClicked(mouseEvent -> {
            try {
                new AuthController(UserType.Medic).show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        mainMenuScene.setOnPatientClicked(mouseEvent -> {
            try {
                new AuthController(UserType.Patient).show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void show() {
        SceneLoader.getInstance().loadScene(mainMenuScene);
    }
}
