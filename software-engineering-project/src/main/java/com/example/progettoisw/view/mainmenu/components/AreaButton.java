package com.example.progettoisw.view.mainmenu.components;

import com.example.progettoisw.utils.properties.RangeProperty;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class AreaButton extends Pane {

    public final RangeProperty RANGE_BLUR_RADIUS = new RangeProperty(8, 4);
    private final RangeProperty RANGE_SATURATION = new RangeProperty(-0.6, -0.3);
    private final RangeProperty RANGE_HOVER_POSITION = new RangeProperty(0, -150);
    private final RangeProperty RANGE_TEXT_OPACITY = new RangeProperty(0.2, 1);
    private final double ratioImage;
    private final DoubleProperty animationProperty; // range[0 , 1]
    private ImageView background;
    private Label title;

    public AreaButton(Image image, String title) {
        super();
        setCursor(Cursor.HAND);

        // Crea l'animation property
        animationProperty = new SimpleDoubleProperty(0);

        // Calculate Ratio of the image
        ratioImage = image.getWidth() / image.getHeight();

        // Build Text
        buildText(title);
        getChildren().add(this.title);

        // Build background
        buildBackground(image);
        getChildren().add(background);

        // Bind animation
        bindAnimation();

        // Clip Overflow
        clipProperty().bind(Bindings.createObjectBinding(() -> new Rectangle(getWidth(), getHeight()), widthProperty(), heightProperty()));
    }

    private void buildBackground(Image image) {
        background = new ImageView(image);

        // Position
        background.layoutXProperty().bind(widthProperty().divide(2).subtract(background.fitWidthProperty().divide(2)));
        background.layoutYProperty().bind((heightProperty().divide(2).subtract(background.fitHeightProperty().divide(2))).add(RANGE_HOVER_POSITION.rangeProperty()));

        // Size
        background.fitWidthProperty().bind(Bindings.max(widthProperty(), heightProperty().multiply(ratioImage)).add(RANGE_BLUR_RADIUS.getEnd()));
        background.fitHeightProperty().bind(background.fitWidthProperty().divide(ratioImage));

        // Effect
        ColorAdjust colorEffectBackground = new ColorAdjust();
        colorEffectBackground.setContrast(-0.2);
        GaussianBlur gaussianEffectBackground = new GaussianBlur();
        colorEffectBackground.setInput(gaussianEffectBackground);
        background.setEffect(colorEffectBackground);

        // Animation
        gaussianEffectBackground.radiusProperty().bind(RANGE_BLUR_RADIUS.rangeProperty());
        colorEffectBackground.saturationProperty().bind(RANGE_SATURATION.rangeProperty());
    }

    private void buildText(String content) {
        /*title = new Label(content);

        // Position
        title.layoutXProperty().bind(widthProperty().divide(2).subtract(title.widthProperty().divide(2)));
        title.layoutYProperty().bind(heightProperty().divide(2));

        // Style
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(Font.font("Roboto", FontWeight.BOLD,36));

        // Animation
        title.scaleXProperty().bind(RANGE_TEXT_SCALE.rangeProperty());
        title.scaleYProperty().bind(RANGE_TEXT_SCALE.rangeProperty());*/

        title = new Label(content);

        // Position
        title.layoutXProperty().bind(widthProperty().divide(2).subtract(title.widthProperty().divide(2)));
        title.layoutYProperty().bind(heightProperty().subtract(title.heightProperty()));

        // Style
        title.opacityProperty().bind(RANGE_TEXT_OPACITY.rangeProperty());
        title.setPadding(new Insets(0, 0, 20, 0));
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(Font.font("Roboto", FontWeight.BOLD, 36));
    }

    private void bindAnimation() {
        RANGE_BLUR_RADIUS.bind(animationProperty);
        RANGE_SATURATION.bind(animationProperty);
        RANGE_HOVER_POSITION.bind(animationProperty);
        RANGE_TEXT_OPACITY.bind(animationProperty);
    }

    public DoubleProperty animationProperty() {
        return animationProperty;
    }
}
