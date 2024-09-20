package com.example.progettoisw.model;

import com.example.progettoisw.Application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ModelOperators {

    private static final String PATH = Application.class.getResource("operators.txt").getPath();
    private static ModelOperators singleInstance = null;
    private final HashMap<String, String> listOperators;

    public ModelOperators() {
        this.listOperators = new HashMap<>();

        try {
            readOperators();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException of path: " + PATH, e);
        }
    }

    public static synchronized ModelOperators getInstance() {
        return singleInstance == null ?
                singleInstance = new ModelOperators() : singleInstance;
    }


    private void readOperators() throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(PATH));

        String line;
        while (sc.hasNextLine() && !(line = sc.nextLine()).equals("")) {
            String[] value = line.split(",");
            if (value.length != 2)
                throw new RuntimeException("bad formatting of file " + PATH);

            listOperators.put(value[0], value[1]);
        }
        sc.close();
    }

    public boolean isAuthorized(String email, String password) {
        return password.equals(listOperators.get(email));
    }
}

