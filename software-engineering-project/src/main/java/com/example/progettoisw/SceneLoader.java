package com.example.progettoisw;

import com.example.progettoisw.view.mainmenu.MainMenuController;
import javafx.animation.PauseTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneLoader {
    private static SceneLoader singleInstance = null;
    private final Stage stage;
    private final MainMenuController mainMenuController;

    public SceneLoader(Stage stage) {
        this.stage = stage;
        this.mainMenuController = new MainMenuController();
    }

    public static SceneLoader buildInstance(Stage stage) {
        return singleInstance = new SceneLoader(stage);
    }

    public static SceneLoader getInstance() {
        if (singleInstance == null)
            throw new RuntimeException("SceneLoader must first be initialized!");

        return singleInstance;
    }

    public void loadScene(Scene scene) {
        stage.setScene(scene);

        // TODO provare a sistemare
        stage.setWidth(stage.getWidth() + 1); // workaround
        PauseTransition delay = new PauseTransition(Duration.millis(15));
        delay.setOnFinished(actionEvent -> stage.setWidth(stage.getWidth() - 1));

        delay.stop();
        delay.play();
    }

    public void goToMainMenu() {
        mainMenuController.show();
    }
}
