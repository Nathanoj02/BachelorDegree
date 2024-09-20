package com.example.progettoisw.view.mainmenu;

import com.example.progettoisw.Images;
import com.example.progettoisw.view.mainmenu.components.AreaButton;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MainMenuScene extends Scene {

    private static final Duration ANIMATION_DURATION = Duration.millis(150);
    private final Group root;
    private final List<AreaButton> areaButtons;
    private Timeline currentAnimation;

    public MainMenuScene() {
        super(new Group());

        root = (Group) getRoot();

        areaButtons = new ArrayList<>();
        areaButtons.add(new AreaButton(Images.getImage("background_managers.jpg"), "Area\nResponsabili"));
        areaButtons.add(new AreaButton(Images.getImage("background_medics.jpg"), "Area\nMedici"));
        areaButtons.add(new AreaButton(Images.getImage("background_patients.jpg"), "Area\nPazienti"));

        areaButtons.forEach(areaButton -> {
            // Position
            areaButton.layoutXProperty().bind(widthProperty().divide(3).multiply(areaButtons.indexOf(areaButton)));

            // Size
            areaButton.prefWidthProperty().bind(widthProperty().divide(areaButtons.size()));
            areaButton.prefHeightProperty().bind(heightProperty());

            // Events
            areaButton.setOnMouseEntered(onEnterHover());
            areaButton.setOnMouseExited(onExitHover());
        });

        root.getChildren().addAll(areaButtons);
    }

    private EventHandler<MouseEvent> onEnterHover() {
        return mouseEvent -> {
            AreaButton source = (AreaButton) mouseEvent.getSource();

            currentAnimation = createForwardTimeline(source);
            currentAnimation.setRate(1);
            currentAnimation.play();
        };
    }

    private EventHandler<MouseEvent> onExitHover() {
        return mouseEvent -> {
            AreaButton source = (AreaButton) mouseEvent.getSource();

            if (currentAnimation.getStatus() == Animation.Status.RUNNING)
                currentAnimation.pause();

            if (currentAnimation.getStatus() == Animation.Status.PAUSED) {
                // Fa il reverse dell'animazione corrente

                currentAnimation.setRate(-1);
                currentAnimation.play();
            } else {
                // Crea una nuova reverse timeline

                currentAnimation = createBackwardTimeline(source);
                currentAnimation.setRate(1);
                currentAnimation.play();
            }
        };
    }

    private Timeline createForwardTimeline(AreaButton target) {
        KeyFrame[] keyframes = new KeyFrame[2];
        
        keyframes[0] = new KeyFrame(
                ANIMATION_DURATION,
                new KeyValue(
                        target.layoutYProperty(),
                        0,
                        Interpolator.EASE_BOTH
                )
        );
        keyframes[1] = new KeyFrame(
                ANIMATION_DURATION,
                new KeyValue(
                        target.animationProperty(),
                        1,
                        Interpolator.EASE_BOTH
                )
        );

        return new Timeline(240, keyframes);
    }

    private Timeline createBackwardTimeline(AreaButton target) {
        KeyFrame[] keyframes = new KeyFrame[2];

        keyframes[0] = new KeyFrame(
                ANIMATION_DURATION,
                new KeyValue(target.layoutYProperty(), 0, Interpolator.EASE_BOTH)
        );
        keyframes[1] = new KeyFrame(
                ANIMATION_DURATION,
                new KeyValue(target.animationProperty(), 0, Interpolator.EASE_BOTH)
        );

        return new Timeline(240, keyframes);
    }

    public void setOnOperatorClicked(EventHandler<? super MouseEvent> eventHandler) {
        areaButtons.get(0).setOnMouseClicked(eventHandler);
    }

    public void setOnMedicClicked(EventHandler<? super MouseEvent> eventHandler) {
        areaButtons.get(1).setOnMouseClicked(eventHandler);
    }

    public void setOnPatientClicked(EventHandler<? super MouseEvent> eventHandler) {
        areaButtons.get(2).setOnMouseClicked(eventHandler);
    }
}
