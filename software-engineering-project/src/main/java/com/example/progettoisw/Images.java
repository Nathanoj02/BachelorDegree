package com.example.progettoisw;

import javafx.scene.image.Image;

public class Images {
    public static Image getImage(String filename) {
        return new Image(Images.class.getResource("images/" + filename).toString());
    }
}
