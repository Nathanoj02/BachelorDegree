package com.example.progettoisw.model.table;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TablesConstructor {

    private static final String EXTENSION = ".txt";

    public static String getTableConstructor(TablesType type) {
        try {
            Path path = Paths.get(TablesConstructor.class.getResource(type.toString().toLowerCase() + EXTENSION).toURI());

            return String.join("", Files.readAllLines(path));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(String.format("Error during read constructor file of %1$s tables", type.toString()), e);
        }
    }
}
